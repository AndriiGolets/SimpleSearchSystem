package name.golets.controller;

import com.fasterxml.jackson.annotation.JsonView;
import name.golets.model.IndexState;
import name.golets.model.IndexationQuery;
import name.golets.model.IndexationStatus;
import name.golets.model.Views;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by andrii on 1/16/17.
 */

@Controller
public class IndexController extends AppController {

    private static Logger LOG = Logger.getLogger(IndexController.class);

    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String indexPage(Model model) {
        populateModelForIndexation(model);
        return "pages/index";
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.POST)
    public String indexSubmit(@ModelAttribute("indexForm") IndexationQuery indexationQuery, Model model) {

        if (indexationQuery != null && appService.indexationStatusNew()) {
            try {
                appService.indexPage(indexationQuery.getUrl(), indexationQuery.getRecDeep());
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
            model.addAttribute(INDEX_START, true);
            return "pages/index";
        } else {
            return "redirect:/main";
        }
    }

    @RequestMapping(params = "index", value = {"/search"}, method = RequestMethod.POST)
    public String redirectToIndex() {
        return "redirect:/index";
    }

    @ResponseBody
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/index/ajax", method = RequestMethod.GET)
    public IndexState getIndexStateViaAjax() {
        return appService.getIndexState();
    }

    private void populateModelForIndexation(Model model) {

        IndexationQuery iq = new IndexationQuery();
        IndexState indexState = appService.getIndexState();
        if (indexState.getStatus().equals(IndexationStatus.PROCEED)) {
            iq.setRecDeep(indexState.getRecDeep());
            iq.setUrl(indexState.getUrl());
            model.addAttribute(INDEX_FORM, iq);
            model.addAttribute(INDEX_START, true);
        } else {
            iq.setRecDeep(DEFAULT_REC_DEEP);
            iq.setUrl(DEFAULT_URL);
            model.addAttribute(INDEX_FORM, iq);
            model.addAttribute(INDEX_START, false);
        }

    }
}
