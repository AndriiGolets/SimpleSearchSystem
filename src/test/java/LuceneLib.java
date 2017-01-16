import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrii on 1/7/17.
 */
public class LuceneLib {


    private static final String LINE = "line";
    private static final String URL = "url";
    private static final int HITS_PER_PAGE = 10;

    private StandardAnalyzer analyzer = new StandardAnalyzer();
    private Directory index = new RAMDirectory();

    public void addItem(String line, String url) throws IOException {

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter w = new IndexWriter(index, config);
        Document doc = new Document();
        doc.add(new TextField(LINE, line, Field.Store.YES));
        // use a string field for url because we don't want it tokenized
        doc.add(new StringField(URL, url, Field.Store.YES));
        w.addDocument(doc);
        w.close();
    }

    public Map<String, String> getResultsForQuery(String query) throws ParseException, IOException {

        Query q = new QueryParser(LINE, analyzer).parse(query);
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, HITS_PER_PAGE);
        ScoreDoc[] hits = docs.scoreDocs;
        Map<String, String> resultMap = new HashMap<>();

        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            resultMap.put(d.get(LINE), d.get(URL));
        }
        reader.close();
        return resultMap;
    }
}
