package name.golets.service.impl;


import name.golets.model.SearchResult;
import name.golets.service.LuceneService;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by andrii on 12/27/16.
 */

@Component
public class LuceneServiceImpl implements LuceneService {

    private static final String LINE = "line";
    private static final String TITLE = "title";
    private static final String URL = "url";
    private static final int MAX_DOCS = 1000;
    private static final int MAX_LINE_LENGTH = 180;
    public static final int MAX_URL_LENGTH = 50;
    public static final int MAX_RESULT_ON_PAGE = 10;

    private StandardAnalyzer analyzer = new StandardAnalyzer();
    private Directory index = new RAMDirectory();

    public void addItems(List<String> lineList, String url, String title) throws IOException {

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);


        if (lineList != null && url != null) {
            for (String line : lineList) {
                Document doc = new Document();
                doc.add(new TextField(LINE, line, Field.Store.YES));
                doc.add(new TextField(TITLE, title, Field.Store.YES));
                // use a string field for url because we don't want it tokenized
                doc.add(new StringField(URL, url, Field.Store.YES));
                // System.out.println("Lucene add line:" + line + "\n" + url);
                w.addDocument(doc);
            }
        } else {
            System.out.println("------- Empty Argument");
        }

        w.close();
    }

    public List<SearchResult> getResultsForQuery(String query) throws ParseException, IOException {

        Query q = new QueryParser(LINE, analyzer).parse(query);
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, MAX_DOCS);
        ScoreDoc[] hits = docs.scoreDocs;
        List<SearchResult> resultList = new ArrayList<>();

        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            resultList.add(new SearchResult(d.get(LINE), d.get(URL), d.get(TITLE), query));
        }
        reader.close();

        resultList = searchResultHandle(resultList);

        return resultList;
    }

    private List<SearchResult> searchResultHandle(List<SearchResult> searchResultList) {

        List<SearchResult> sortedList = new ArrayList<>();
        Map<String, Map<String, String>> resultMap = new HashMap<>();
        Map<String, String> lineAndUrlMap;

        for (SearchResult searchResult : searchResultList) {
            if (!resultMap.containsKey(searchResult.getTitle())) {
                lineAndUrlMap = new HashMap<>();
                lineAndUrlMap.put(searchResult.getLine(), searchResult.getUrl());
                resultMap.put(searchResult.getTitle(), lineAndUrlMap);
            } else {
                resultMap.get(searchResult.getTitle()).put(searchResult.getLine(), searchResult.getUrl());
            }
        }

        for (Map.Entry<String, Map<String, String>> stringMapEntry : resultMap.entrySet()) {
            lineAndUrlMap = stringMapEntry.getValue();

            List<String> linesList = new ArrayList<>(lineAndUrlMap.keySet());
            linesList.sort((s1, s2) -> s2.length() - s1.length());

            StringBuilder scopeLines = new StringBuilder();
            for (String line : linesList) {
                scopeLines.append(line).append(". ");
            }
            String scopeLine = scopeLines.toString();
            if (scopeLine.length() > MAX_LINE_LENGTH) {
                scopeLine = scopeLine.substring(0, MAX_LINE_LENGTH);
            }

            String searchQuery = searchResultList.get(0).getSearchQuery();
            String url = lineAndUrlMap.values().iterator().next();
            url = url.length() > MAX_URL_LENGTH ? url.substring(0, MAX_URL_LENGTH) : url;
            url = boldTextIgnoreCase(url, searchQuery);
            String title = stringMapEntry.getKey();
            title = boldTextIgnoreCase(title, searchQuery);
            scopeLine = boldTextIgnoreCase(scopeLine, searchQuery);
            sortedList.add(new SearchResult(scopeLine, url, title, searchQuery));
        }

        Collections.sort(sortedList);
        if(sortedList.size() > MAX_RESULT_ON_PAGE){
            sortedList = sortedList.subList(0,9);
        }

        return sortedList;
    }

    private String boldTextIgnoreCase(String text, String query) {

        String text1 = query.substring(0, 1).toUpperCase() + query.substring(1);
        String text2 = query.toUpperCase();
        String text3 = query.toLowerCase();
        String text4 = query.toLowerCase().substring(0, 1).toUpperCase() + query.toLowerCase().substring(1);
        text = boldText(text, text1);
        text = boldText(text, text2);
        text = boldText(text, text3);
        text = boldText(text, text4);
        return text;
    }

    private String boldText(String text, String query) {
        return text.replaceAll(query, "<b>" + query + "</b>");
    }
}
