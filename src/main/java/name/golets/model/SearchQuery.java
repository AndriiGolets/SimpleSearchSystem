package name.golets.model;

/**
 * Created by andrii on 1/13/17.
 */
public class SearchQuery {

    private String query;

    public SearchQuery() {
    }

    public SearchQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
