package pl.kietlinski.indress.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.kietlinski.indress.model.AppUser;
import pl.kietlinski.indress.service.ItemService;

@Controller
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping("/item")
    public ModelAndView getItemById(@RequestParam long id){
        return new ModelAndView("showItem", "item", itemService.getItemById(id));
    }

    @RequestMapping("/buy")
    public String buyItemById(@RequestParam long id, Model model){
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("item", itemService.getItemById(id));
        model.addAttribute("user", appUser);
        return "buy";
    }

    @RequestMapping("/success")
    public String successBuyItem(@RequestParam long id){
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        itemService.successBuyItem(appUser.getUsername(), id);
        return "thankYou";
    }

    @RequestMapping("/reconstructDatabase")
    public String reconstructDatabase(){
        itemService.reconstructDatabase();
        return "redirect:/start";
    }

}
