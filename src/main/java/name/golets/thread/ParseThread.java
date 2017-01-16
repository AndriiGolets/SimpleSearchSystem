package name.golets.thread;

import name.golets.model.ParsedSite;
import name.golets.service.JsoupParseService;
import name.golets.service.LuceneService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by andrii on 1/11/17.
 */

@Component
public class ParseThread extends Thread {

    private static Logger LOG = Logger.getLogger(ParseThread.class);

    private String url;
    private int recDeep;

    private int lineN;
    private int urlN;
    private String currentUrl;
    private String currentSearchQuery;

    private final JsoupParseService parseService;
    private final LuceneService lucene;

    @Autowired
    public ParseThread(JsoupParseService parseService, LuceneService lucene) {
        this.parseService = parseService;
        this.lucene = lucene;
    }

    @Override
    public void run() {

        try {
            recursionParse(getUrl(), getRecDeep());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recursionParse(String url, int recDeep) throws IOException {

        if (recDeep < 1) {
            return;
        }

        ParsedSite parsedSite;
        List<String> lineList;
        List<String> urlList;
        try {
            LOG.info(recDeep + ") Parse Url: " + url);
            parsedSite = parseService.parseUrl(url);
            if (!parsedSite.isRedirect()) {
                lineList = parsedSite.getLineList();
                urlList = parsedSite.getUrlList();
                lineN += lineList.size();
                urlN += urlList.size();
                currentUrl = url + " contains " + lineList.size() + " lines, " + urlList.size() + " links";
                LOG.debug(currentUrl);

            } else {
                currentUrl = url + " is redirect";
                LOG.debug(currentUrl);
                return;
            }
        } catch (Exception e) {
            LOG.warn("Warn! url not respond: " + url);
            return;
        }


        if (lineList.size() > 0) {
            lucene.addItems(lineList, url, parsedSite.getTitle());
        }


        if (urlList.size() > 0) {
            for (String nextUrl : urlList) {
                recursionParse(nextUrl, recDeep - 1);
            }
        }

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRecDeep() {
        return recDeep;
    }

    public void setRecDeep(int recDeep) {
        this.recDeep = recDeep;
    }

    public LuceneService getLucene() {
        return lucene;
    }

    public int getLineN() {
        return lineN;
    }

    public void setLineN(int lineN) {
        this.lineN = lineN;
    }

    public int getUrlN() {
        return urlN;
    }

    public void setUrlN(int urlN) {
        this.urlN = urlN;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getCurrentSearchQuery() {
        return currentSearchQuery;
    }

    public void setCurrentSearchQuery(String currentSearchQuery) {
        this.currentSearchQuery = currentSearchQuery;
    }
}
