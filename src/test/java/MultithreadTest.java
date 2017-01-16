import name.golets.model.SearchResult;

import name.golets.thread.ParseThread;
import name.golets.service.impl.JsoupParseServiceImpl;
import name.golets.service.impl.LuceneServiceImpl;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * Created by andrii on 1/11/17.
 */

public class MultithreadTest {

    public static void main(String[] args) throws InterruptedException, IOException, ParseException {

        List<SearchResult> searchResultList;
        String query = "погода";

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.register(JsoupParseServiceImpl.class);
        context.register(LuceneServiceImpl.class);
        context.register(ParseThread.class);
        context.refresh();

        ParseThread parseThread = context.getBean(ParseThread.class);

        parseThread.setRecDeep(2);
        parseThread.setUrl("https://www.ukr.net");
        parseThread.start();


        Thread.sleep(3000);
        printSearchResult(parseThread.getLucene().getResultsForQuery(query));

        Thread.sleep(3000);
        printSearchResult(parseThread.getLucene().getResultsForQuery(query));

        Thread.sleep(3000);
        printSearchResult(parseThread.getLucene().getResultsForQuery(query));

    }

    private static void printSearchResult( List<SearchResult> resultList ){

        int i = 1;
        for (SearchResult result : resultList) {
            System.out.println(i++ + ") " + result.getLine() + "\n" + result.getUrl());
        }

    }

}
