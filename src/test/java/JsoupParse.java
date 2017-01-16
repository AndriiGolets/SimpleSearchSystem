/**
 * Created by andrii on 1/3/17.
 */

import org.apache.lucene.queryparser.classic.ParseException;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class JsoupParse {

    public static void main(String[] args) throws IOException {

        String url = "https://www.ukr.net";

        Connection con = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/55.0.2883.87 Chrome/55.0.2883.87 Safari/537.36")
                .referrer("http://www.google.com");

        Response response = con.followRedirects(false).execute();
        System.out.println(response.statusCode() + " : " + response.url());

//check if URL is redirect?
        System.out.println("Is URL going to redirect : " + response.hasHeader("location"));
        System.out.println("Target : " + response.header("location"));

        Document doc = con.get();
       /* Elements links = doc.select("a[href]");

        for (Element link : links) {

            System.out.println(link.text());
            System.out.println(link.attr("abs:href"));

        }*/
// (!element.tagName().equals("noscript")) && (!element.hasAttr("style=display:none"))
        doc.select("noscript").remove();
        doc.select("script").remove();
        doc.select("[style=display:none;]").remove();

        Elements elements = doc.body().select("*");


        LuceneLib luceneLib = new LuceneLib();

        int i = 0;
        for (Element element : elements) {
            String line = element.ownText();

            if (line.matches(".*\\S.*") ) {
             //   System.out.println(line);
              //  System.out.println(element.attr("abs:href"));
                luceneLib.addItem(line, url);
            } else {
                //System.out.println(++i + ":  "+"-delete: " + line);
            }

        }
        try {
            Map<String, String> resultMap = luceneLib.getResultsForQuery("реклама");
            int n = 1;
            for (Map.Entry<String, String> result : resultMap.entrySet()) {
                System.out.println(n++ + ") " + result.getKey() + "\n" + result.getValue() + "\n");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
