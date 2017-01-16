package name.golets.controller;

import name.golets.model.SearchQuery;
import name.golets.model.SearchResult;
import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by andrii on 1/16/17.
 */

@Controller
public class SearchController extends AppController{

    private static Logger LOG = Logger.getLogger(SearchController.class);

    @RequestMapping(params = "search", value = {"/search"}, method = RequestMethod.POST)
    public String searchResult(@ModelAttribute("searchQuery") SearchQuery searchQuery, ModelMap model) {

        try {
            List<SearchResult> searchResultList = appService.search(searchQuery.getQuery());
            model.addAttribute("searchList", searchResultList);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return "pages/search";
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String searchPage(ModelMap model) {

        List<SearchResult> searchResultList = appService.getCurrentSearch();
        model.addAttribute("searchList", searchResultList);
        return "pages/search";
    }
}
