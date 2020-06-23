package pl.kietlinski.indress.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.kietlinski.indress.model.AppUser;
import pl.kietlinski.indress.service.AppUserService;

@Controller
public class AppUserController {

    private AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/findAppUser")
    public AppUser findAppUser(@RequestBody AppUser appUser){
        return appUserService.findAppUser(appUser);
    }

    @PostMapping
    public boolean addNewAppUser(AppUser newAppUser){
        appUserService.addNewAppUser(newAppUser);
        return true;
    }

}
