package name.golets.service;

import name.golets.model.IndexState;
import name.golets.model.SearchResult;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by andrii on 11/30/16.
 */
public interface AppService {

    void indexPage(String uri, int recursionDeep) throws URISyntaxException, IOException;

    List<SearchResult> search(String query) throws IOException, ParseException;

    IndexState getIndexState();

    List<SearchResult> getCurrentSearch();

    boolean indexationStatusNew();

}
