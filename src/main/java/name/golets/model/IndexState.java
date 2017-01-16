package name.golets.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by andrii on 1/12/17.
 */
public class IndexState {

    @JsonView(Views.Public.class)
    private int urlN;

    @JsonView(Views.Public.class)
    private int lineN;

    @JsonView(Views.Public.class)
    private String currentUrl;

    @JsonView(Views.Public.class)
    private IndexationStatus status;

    @JsonView(Views.Public.class)
    private String url;

    @JsonView(Views.Public.class)
    private int recDeep;

    public IndexState() {

    }

    public IndexState(IndexationStatus status) {
        this.status = status;
    }


    public IndexState(int urlN, int lineN, String currentUrl, IndexationStatus status, String url, int recDeep) {
        this.urlN = urlN;
        this.lineN = lineN;
        this.currentUrl = currentUrl;
        this.status = status;
        this.url = url;
        this.recDeep = recDeep;
    }

    public int getUrlN() {
        return urlN;
    }

    public void setUrlN(int urlN) {
        this.urlN = urlN;
    }

    public int getLineN() {
        return lineN;
    }

    public void setLineN(int lineN) {
        this.lineN = lineN;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public IndexationStatus getStatus() {
        return status;
    }

    public void setStatus(IndexationStatus status) {
        this.status = status;
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
}
