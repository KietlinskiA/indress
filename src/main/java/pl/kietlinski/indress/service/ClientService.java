package pl.kietlinski.indress.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.kietlinski.indress.model.Item;
import pl.kietlinski.indress.model.LoginHyperlink;
import pl.kietlinski.indress.repository.ItemRepository;

import java.util.List;
import java.util.Random;

@Service
@Data
public class ClientService {

    private ItemRepository itemRepository;
    private int adNumber = 1;

    @Autowired
    public ClientService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public LoginHyperlink getLoginHyperlink(boolean isUserInRole) {
        if (isUserInRole) {
            return new LoginHyperlink("/logout", "WYLOGUJ");
        } else {
            return new LoginHyperlink("/login", "ZALOGUJ");
        }
    }

    public List<Item> getLastAddedItemList() {
        return itemRepository.findLastAddedItems();
    }

    @Scheduled(fixedDelay = 10000)
    public void setAdNumber() {
        adNumber = new Random().nextInt(5)+1;
    }
}
