package pl.kietlinski.indress.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kietlinski.indress.model.Item;
import pl.kietlinski.indress.service.MainService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/items")
public class SerwerController {

    private MainService mainService;

    public SerwerController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping
    public ResponseEntity getItems(){
        List<Item> itemList = mainService.getItemList();
        return new ResponseEntity(itemList, HttpStatus.OK);
    }

    @GetMapping("/byId")
    public ResponseEntity<Item> getItemById(@RequestParam long id){
        Optional<Item> optionalItem = mainService.getById(id).stream().filter(item -> item.getId() == id).findFirst();
        return optionalItem
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestParam Item newItem){
        mainService.saveNewItem(newItem);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
