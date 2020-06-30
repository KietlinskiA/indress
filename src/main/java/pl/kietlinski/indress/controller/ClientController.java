package pl.kietlinski.indress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.kietlinski.indress.model.AppUser;
import pl.kietlinski.indress.model.Item;
import pl.kietlinski.indress.model.LoginHyperlink;
import pl.kietlinski.indress.service.AppUserService;
import pl.kietlinski.indress.service.ClientService;
import pl.kietlinski.indress.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    private AppUserService appUserService;
    private ClientService clientService;
    private ItemService itemService;

    public ClientController(AppUserService appUserService, ClientService clientService, ItemService itemService) {
        this.appUserService = appUserService;
        this.clientService = clientService;
        this.itemService = itemService;
    }

    @RequestMapping
    public String get() {
        return "redirect:/start";
    }

    @RequestMapping("/start")
    public String start(HttpServletRequest httpServletRequest, Model model) {
        LoginHyperlink loginHyperlink = clientService.getLoginHyperlink(httpServletRequest.isUserInRole("USER"));
        List<Item> lastAddedItemList = clientService.getLastAddedItemList();
        Integer number = clientService.getAdNumber();

        model.addAttribute("loginHyperlink", loginHyperlink);
        model.addAttribute("lastAddedItemList", lastAddedItemList);
        model.addAttribute("adsNumber", number);
        return "start";
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        Integer number = clientService.getAdNumber();

        return new ModelAndView("login", "adsNumber", number);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) throws ServletException {
        httpServletRequest.logout();
        return "redirect:/login";
    }

    @RequestMapping("/singup")
    public ModelAndView singup() {
        return new ModelAndView("registration", "newAppUser", new AppUser());
    }

    @RequestMapping("/register")
    public ModelAndView register(AppUser newAppUser) {
        if(appUserService.addNewAppUser(newAppUser)){
            return new ModelAndView("redirect:/login");
        } else {
            return new ModelAndView("registration", "newAppUser", new AppUser());
        }
    }

    @RequestMapping("/search")
    public String search( Model model,
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String mainCategory,
            @RequestParam(required = false) String clothes) {
        itemService.getResultList(Optional.ofNullable(itemName), Optional.ofNullable(mainCategory), Optional.ofNullable(clothes));
        Integer number = clientService.getAdNumber();

        model.addAttribute("adsNumber", number);
        model.addAttribute("resultList", itemService.getItemList());
        return "search";
    }

}
