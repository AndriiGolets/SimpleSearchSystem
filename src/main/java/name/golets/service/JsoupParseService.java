package name.golets.service;

import name.golets.model.ParsedSite;

import java.io.IOException;

/**
 * Created by andrii on 1/9/17.
 */
public interface JsoupParseService {

    ParsedSite parseUrl(String url) throws IOException;

}
