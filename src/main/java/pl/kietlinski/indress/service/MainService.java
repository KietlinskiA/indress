package pl.kietlinski.indress.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kietlinski.indress.model.Item;
import pl.kietlinski.indress.model.LoginHyperlink;
import pl.kietlinski.indress.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class MainService {

    private ItemRepository itemRepository;
    private List<Item> itemList;

    @Autowired
    public MainService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        itemList = new ArrayList<>();
        itemList = findAll();
    }

    public List<Item> getById(long id){
        if(!itemList.isEmpty()){
            itemList.clear();
        }
        itemList.add(itemRepository.findById(id));
        return itemList;
    }

    public void saveNewItem(Item newItem) {
        itemRepository.save(newItem);
    }

    public LoginHyperlink getLoginHyperlink(boolean isUserInRole){
        if(isUserInRole){
            return new LoginHyperlink("/logout", "WYLOGUJ");
        } else {
            return new LoginHyperlink("/login", "ZALOGUJ");
        }
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public List<Item> getResultList(String itemName) {
        if(itemName.isEmpty()){
            return itemRepository.findAll();
        } else {
            return itemRepository.findByName(itemName);
        }
    }
}
