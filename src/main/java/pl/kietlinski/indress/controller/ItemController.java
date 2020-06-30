package pl.kietlinski.indress.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kietlinski.indress.model.AppUser;
import pl.kietlinski.indress.service.ItemService;

@Controller
public class ItemController {

    private ItemService itemService;

    private AppUser appUser;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping("/buy")
    public String buyItemById(@RequestParam long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        appUser = (AppUser) authentication.getPrincipal();

        model.addAttribute("item", itemService.getItemById(id));
        model.addAttribute("user", appUser);
        return "buy";
    }

    @RequestMapping("/success")
    public String successBuyItem(@RequestParam long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        appUser = (AppUser) authentication.getPrincipal();

        itemService.successBuyItem(appUser.getUsername(), id);
        return "thankYou";
    }

    @RequestMapping("/reconstructDatabase")
    public String reconstructDatabase(){
        itemService.reconstructDatabase();
        return "redirect:/start";
    }

}
