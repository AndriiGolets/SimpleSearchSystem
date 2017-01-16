package name.golets.service.impl;

import name.golets.model.ParsedSite;
import name.golets.service.JsoupParseService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrii on 1/9/17.
 */

@Component
public class JsoupParseServiceImpl implements JsoupParseService {

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/55.0.2883.87 Chrome/55.0.2883.87 Safari/537.36";
    private static final String REFERRER = "http://www.google.com";
    private static final String NOT_EMPTY = ".*\\S.*";
    private static final int MIN_LINE_LENGTH = 3;
    private static final int MAX_LINE_LENGTH = 180;

    @Override
    public ParsedSite parseUrl(String url) throws IOException {

        Connection con = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .referrer(REFERRER);

        Connection.Response response = con.followRedirects(false).execute();

        //Test url for redirect
        if (response.hasHeader("location")) {
            if (!linqEqualsTest(response.header("location"), url)) {
                return new ParsedSite(true);
            }
        }

        Document doc = con.get();

        String title = doc.title();
        //remove nonsensical tag
        doc.select("noscript").remove();
        doc.select("script").remove();
        doc.select("[style=display:none;]").remove();
        doc.select("head").remove();

        //Parse all elements
        Elements elements = doc.body().select("*");

        List<String> linesList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();

        for (Element element : elements) {
            String line = element.ownText();
            String link = element.attr("abs:href");
            //filter blank line element
            if (line.matches(NOT_EMPTY) && !linesList.contains(line) && line.length() > MIN_LINE_LENGTH) {
                //avoid repeat indexation
                if (line.length() > MAX_LINE_LENGTH) {
                    line = line.substring(0, MAX_LINE_LENGTH);
                }
                linesList.add(line);
                if (link.matches(NOT_EMPTY) && !urlList.contains(link) && !linqEqualsTest(link, url)) {
                    urlList.add(link);
                }
                //filter url blank element
            }
        }

        return new ParsedSite(linesList, urlList, title);
    }

    private boolean linqEqualsTest(String link1, String link2) {

        link1 = link1.endsWith("/") ? link1.substring(0, link1.length() - 1) : link1;
        link2 = link2.endsWith("/") ? link2.substring(0, link2.length() - 1) : link2;

        link1 = link1.startsWith("https") ? link1.replaceFirst("https", "http") : link1;
        link2 = link2.endsWith("https") ? link1.replaceFirst("https", "http") : link2;

        return link1.equals(link2);
    }

}
