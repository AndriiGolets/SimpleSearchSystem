package name.golets.service.impl;

import name.golets.model.IndexState;
import name.golets.model.IndexationStatus;
import name.golets.model.SearchResult;
import name.golets.service.AppService;
import name.golets.thread.ParseThread;
import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrii on 11/30/16.
 */

@Service("appService")
public class AppServiceImpl implements AppService {

    private static Logger LOG = Logger.getLogger(AppServiceImpl.class);

    private final ParseThread parseThread;

    @Autowired
    public AppServiceImpl(ParseThread parseThread) {
        this.parseThread = parseThread;
    }

    @Override
    public void indexPage(String url, int recDeep) throws IOException {
        if (!parseThread.isAlive()) {
            parseThread.setUrl(url);
            parseThread.setRecDeep(recDeep);
            parseThread.start();
            LOG.info("--------- Thread Started");
        } else {
            LOG.info("--------- Thread already exists");
        }
    }

    @Override
    public List<SearchResult> search(String query) throws IOException, ParseException {
        parseThread.setCurrentSearchQuery(query);
        return parseThread.getLucene().getResultsForQuery(query);
    }

    @Override
    public IndexState getIndexState() {

        if ((parseThread.getState().equals(Thread.State.NEW))) {
            return new IndexState(IndexationStatus.NEW);
        } else if (parseThread.getState().equals(Thread.State.TERMINATED)) {
            return new IndexState(IndexationStatus.DONE);
        } else {
            return new IndexState(parseThread.getUrlN()
                    , parseThread.getLineN()
                    , parseThread.getCurrentUrl()
                    , IndexationStatus.PROCEED
                    , parseThread.getUrl()
                    , parseThread.getRecDeep()
            );
        }
    }

    @Override
    public boolean indexationStatusNew() {

        return (getIndexState().getStatus().equals(IndexationStatus.NEW));
    }

    @Override
    public List<SearchResult> getCurrentSearch() {
        try {
            String currentSearch = parseThread.getCurrentSearchQuery();
            if(currentSearch != null) {
                return search(parseThread.getCurrentSearchQuery());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

