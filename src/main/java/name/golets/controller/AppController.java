package name.golets.controller;

import name.golets.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by andrii on 11/30/16.
 */

public class AppController {

    static final String INDEX_START = "indexStart";
    static final String INDEX_FORM = "indexForm";
    static final String DEFAULT_URL = "https://www.ukr.net";
    static final int DEFAULT_REC_DEEP = 2;

    @Autowired
    AppService appService;

    public AppController() {
    }

}
