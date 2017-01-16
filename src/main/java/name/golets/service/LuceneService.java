package name.golets.service;

import name.golets.model.IndexState;
import name.golets.model.SearchResult;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * Created by andrii on 12/27/16.
 */
public interface LuceneService {

    void addItems(List<String> lineList, String url, String title) throws IOException;

    List<SearchResult> getResultsForQuery(String query) throws ParseException, IOException;

}
