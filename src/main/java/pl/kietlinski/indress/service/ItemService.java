package pl.kietlinski.indress.service;

import pl.kietlinski.indress.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item getItemById(long id);

    void getResultList(Optional<String> itemName, Optional<String> mainCategory, Optional<String> clothes);

    void successBuyItem(String username, long id);

    void reconstructDatabase();

    List<Item> getItemList();
}
