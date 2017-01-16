import name.golets.model.ParsedSite;
import name.golets.model.SearchResult;
import name.golets.service.JsoupParseService;
import name.golets.service.LuceneService;
import name.golets.service.impl.JsoupParseServiceImpl;
import name.golets.service.impl.LuceneServiceImpl;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by andrii on 1/9/17.
 */

public class Test {

    private static JsoupParseService jps = new JsoupParseServiceImpl();
    private static LuceneService lucene = new LuceneServiceImpl();

    public static void main(String[] args) throws IOException, ParseException {

        String startUrl = "https://www.ukr.net";
        String query = "машина";
        Integer recursionDeep = 2;

        recursionParse(startUrl, recursionDeep);

        List<SearchResult> searchResult = lucene.getResultsForQuery(query);

        int i = 1;
        for (SearchResult result : searchResult) {
            System.out.println(i++ + ") " +result.getTitle()+"\n" + result.getLine() + "\n" + result.getUrl());
        }

    }

    private static void recursionParse(String url, int recDeep) throws IOException {

        if (recDeep < 1) {
            return;
        }

        ParsedSite parsedSite;
        List<String> lineList;
        List<String> urlList;
        try {
            System.out.println(recDeep + ") Parse Url: " + url);
            parsedSite = jps.parseUrl(url);
            if (!parsedSite.isRedirect()) {
                lineList = parsedSite.getLineList();
                urlList = parsedSite.getUrlList();
                System.out.println("Url " + url + " contains " + parsedSite.getLineList().size() + " lines, " + parsedSite.getUrlList().size() + " links");
            } else {
                System.out.println("Url " + url + " is redirect");
                return;
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Warn! url not respond: " + url);
            return;
        }

        if (lineList != null) {
            if (lineList.size() > 0) {
                lucene.addItems(lineList, url, parsedSite.getTitle());
            }
        }

        if (urlList != null) {
            if (urlList.size() > 0) {
                for (String nextUrl : urlList) {
                    recursionParse(nextUrl, recDeep - 1);
                }
            }
        }

    }
}