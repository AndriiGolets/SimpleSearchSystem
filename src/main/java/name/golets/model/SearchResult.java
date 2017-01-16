package name.golets.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by andrii on 1/9/17.
 */
public class SearchResult implements Comparable<SearchResult> {

    private String line;
    private String url;
    private String title;
    private String searchQuery;


    public SearchResult() {
    }

    public SearchResult(String line, String url, String title, String searchQuery) {
        this.line = line;
        this.url = url;
        this.title = title;
        this.searchQuery = searchQuery;
    }

    @Override
    public int compareTo(SearchResult sr) {

        //sort by order of searchQuery in title and text
        int n1 = indexOfIgnoreCase(title, searchQuery);
        int n2 = indexOfIgnoreCase(sr.getTitle(), searchQuery);

        int result = (n1 < 0 ? n2 + 1 : n1) - (n2 < 0 ? n1 + 1 : n2);
        if (result != 0) {
            return result;
        }

        n1 = indexOfIgnoreCase(line, searchQuery);
        n2 = indexOfIgnoreCase(sr.getLine(), searchQuery);

        result = (n1 < 0 ? n2 + 1 : n1) - (n2 < 0 ? n1 + 2 : n2);
        if (result != 0) {
            return result;
        }

        return 0;
    }

    private int indexOfIgnoreCase(String s1, String s2) {
        List<Integer> intList = new ArrayList<>();
        intList.add(s1.indexOf(s2));
        intList.add(s1.indexOf(s2.substring(0, 1).toUpperCase() + s2.substring(1)));
        intList.add(s1.indexOf(s2.toUpperCase()));
        intList.add(s1.indexOf(s2.toLowerCase()));
        intList.add(s1.indexOf(s2.toLowerCase().substring(0, 1).toUpperCase() + s2.toLowerCase().substring(1)));
        Collections.sort(intList);
        return intList.get(intList.size() - 1);
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "line='" + line + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", searchQuery='" + searchQuery + '\'' +
                '}';
    }
}
