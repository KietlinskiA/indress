package pl.kietlinski.indress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.kietlinski.indress.model.AppUser;
import pl.kietlinski.indress.model.LoginHyperlink;
import pl.kietlinski.indress.service.AppUserService;
import pl.kietlinski.indress.service.MainService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ClientController {

    private AppUserService appUserService;
    private MainService mainService;

    public ClientController(AppUserService appUserService, MainService mainService) {
        this.appUserService = appUserService;
        this.mainService = mainService;
    }

    @RequestMapping
    public String get(){
        return "redirect:/start";
    }

    @RequestMapping("/start")
    public ModelAndView start(HttpServletRequest httpServletRequest){
        LoginHyperlink loginHyperlink = mainService.getLoginHyperlink(httpServletRequest.isUserInRole("USER"));
        return new ModelAndView("start", "loginHyperlink", loginHyperlink);
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) throws ServletException {
        httpServletRequest.logout();
        return "redirect:/login";
    }

    @RequestMapping("/singup")
    public ModelAndView singup(){
        return new ModelAndView("registration", "newAppUser", new AppUser());
    }

    @RequestMapping("/register")
    public ModelAndView register(AppUser newAppUser){
        appUserService.addNewAppUser(newAppUser);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(required = false) String itemName){
        return new ModelAndView("search", "resultList", mainService.getResultList(itemName));
    }

}
