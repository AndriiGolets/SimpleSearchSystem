package name.golets.controller;

import name.golets.model.SearchQuery;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by andrii on 1/16/17.
 */

@Controller
public class MainController extends AppController{

    private static Logger LOG = Logger.getLogger(MainController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String redirectToMain() {
        LOG.debug("redirectToMain()");
        return "redirect:/main";
    }

    @RequestMapping(value = {"/main"}, method = RequestMethod.GET)
    public String mainPage(Model model) {
        model.addAttribute("searchQuery", new SearchQuery("погода"));
        model.addAttribute("status", appService.getIndexState().getStatus());
        return "pages/main";
    }



}
