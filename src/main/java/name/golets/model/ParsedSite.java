package name.golets.model;

import java.util.List;

/**
 * Created by andrii on 1/9/17.
 */
public class ParsedSite {

    private boolean redirect;

    private List<String> lineList;
    private List<String> urlList;
    private String title;

    public ParsedSite(List<String> lineList, List<String> urlList, String title) {
        this.lineList = lineList;
        this.urlList = urlList;
        this.title = title;
    }

    public ParsedSite(boolean redirect) {
        this.redirect = redirect;
    }

    public ParsedSite() {
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public List<String> getLineList() {
        return lineList;
    }

    public void setLineList(List<String> lineList) {
        this.lineList = lineList;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
