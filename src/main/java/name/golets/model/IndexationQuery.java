package name.golets.model;

/**
 * Created by andrii on 1/11/17.
 */
public class IndexationQuery {

    private String url;

    private int recDeep;


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

    @Override
    public String toString() {
        return "IndexationQuery{" +
                "url='" + url + '\'' +
                ", recDeep=" + recDeep +
                '}';
    }
}
