package pl.kietlinski.indress.service;

import javafx.util.converter.LocalDateTimeStringConverter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kietlinski.indress.model.AppUser;
import pl.kietlinski.indress.model.Item;
import pl.kietlinski.indress.repository.ItemRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class ItemService {

    private AppUserService appUserService;
    private ItemRepository itemRepository;
    private List<Item> itemList;

    @Autowired
    public ItemService(AppUserService appUserService, ItemRepository itemRepository) {
        this.appUserService = appUserService;
        this.itemRepository = itemRepository;
        itemList = itemRepository.findAll();
    }

    public Item getItemById(long id) {
        return itemRepository.findById(id).get();
    }

    public void saveNewItem(Item newItem) {
        itemRepository.save(newItem);
    }

    public void clearItemList() {
        if (itemList.size() != 0) {
            itemList.clear();
        }
    }

    public void getResultList(Optional<String> itemName, Optional<String> mainCategory, Optional<String> clothes) {
        clearItemList();

        if (itemName.isPresent()) { //itemName
            itemList = itemRepository.findByName(itemName.get());
        } else {
            int categoryNumber = 0;

            if (mainCategory.isPresent() && !mainCategory.get().equals("---")) { //mainCategory
                categoryNumber += 1;
            }
            if (clothes.isPresent() && !clothes.get().equals("---")) { //clothes
                categoryNumber += 10;
            }

            switch (categoryNumber) {
                case 0:
                    itemList = itemRepository.findAll();
                    break;
                case 1:
                    itemList = itemRepository.findByMainCategory(mainCategory.get());
                    break;
                case 10:
                    itemList = itemRepository.findByClothes(clothes.get());
                    break;
                case 11:
                    itemList = itemRepository.findByMainCategoryAndClothes(mainCategory.get(), clothes.get());
                    break;
            }
        }
    }

    public void successBuyItem(String username, long id) {
        AppUser appUser = appUserService.findAppUser(username);
        BigDecimal wallet = appUser.getWallet();
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            BigDecimal price = optionalItem.get().getPrice();

            appUser.setWallet(wallet
                    .subtract(price) //price
                    .subtract(new BigDecimal(10)) //shipping cost
            );
            appUserService.saveAppUser(appUser);
            itemRepository.delete(item);
        }
    }

    public void reconstructDatabase() {
        itemRepository.deleteAll();
        itemRepository.save(new Item("Spodnie house", "House", "M", "Nowy", "Biały", LocalDateTime.parse("2020-06-11T15:56:45"), "Do sprzedania, polecam!", new BigDecimal("20.00"), "/img/items/mens/jeans/j_1.jpg", "Jeansy", "Mężczyźni"));
        itemRepository.save(new Item("Spodnie męskie", "Inna", "M", "Idealny", "Szary", LocalDateTime.parse("2020-06-11T15:56:45"), "Do sprzedania, polecam!", new BigDecimal("50.00"), "/img/items/mens/jeans/j_2.jpg", "Jeansy", "Mężczyźni"));
        itemRepository.save(new Item("Buty męskie Adidas", "Inna", "XL", "Nowy", "Pomarańczowy", LocalDateTime.parse("2020-06-11T15:56:45"), "Do sprzedania, polecam!", new BigDecimal("10.00"), "/img/items/mens/jeans/j_3.jpg", "Jeansy", "Mężczyźni"));
        itemRepository.save(new Item("Buty męskie Adidas", "Adidas", "45", "Nowy", "Niebieski", LocalDateTime.parse("2020-06-11T15:56:45"), "Do sprzedania, polecam!", new BigDecimal("130.00"), "/img/items/mens/shoes/s_1.jpg", "Buty", "Mężczyźni"));
        itemRepository.save(new Item("Buty lacoste", "Lacoste", "44", "Idealny", "Szary", LocalDateTime.parse("2020-06-11T15:56:45"), "Do sprzedania, polecam!", new BigDecimal("80.00"), "/img/items/mens/shoes/s_2.jpg", "Buty", "Mężczyźni"));
        itemRepository.save(new Item("Buty ck meskie", "Calvin Klein", "43", "Idealny", "Czarny", LocalDateTime.parse("2020-06-11T15:56:45"), "Do sprzedania, polecam!", new BigDecimal("85.00"), "/img/items/mens/shoes/s_3.jpg", "Buty", "Mężczyźni"));
        itemRepository.save(new Item("Koszulka th super stan", "Tommy Hilfiger", "XL", "Nowy", "Niebieski", LocalDateTime.parse("2020-06-11T15:56:45"), "Do sprzedania, polecam!", new BigDecimal("55.00"), "/img/items/mens/tshirts/t_1.jpg", "T-shirty", "Mężczyźni"));
        itemRepository.save(new Item("Koszulka XXL", "Ramones", "XXL", "Idealny", "Pomarańczowy", LocalDateTime.parse("2020-06-11T15:56:45"), "Do sprzedania, polecam!", new BigDecimal("50.00"), "/img/items/mens/tshirts/t_2.jpg", "T-shirty", "Mężczyźni"));
        itemRepository.save(new Item("Bluza ld", "Lucky Dice", "L", "Nowy", "Niebieski", LocalDateTime.parse("2020-06-11T15:56:45"), "Do sprzedania, polecam!", new BigDecimal("25.00"), "/img/items/mens/tshirts/t_3.jpg", "T-shirty", "Mężczyźni"));
        itemRepository.save(new Item("Jeansy HM", "H&M", "XL", "Nowy", "Pomarańczowy", LocalDateTime.parse("2020-06-11T15:56:45"), "Fajna rzecz", new BigDecimal("115.00"), "/img/items/womens/jeans/j_1.jpg", "Jeansy", "Kobiety"));
        itemRepository.save(new Item("Jeansy damskie Cropp", "Cropp", "L", "Idealny", "Szary", LocalDateTime.parse("2020-06-11T15:56:45"), "Fajna rzecz", new BigDecimal("45.00"), "/img/items/womens/jeans/j_2.jpg", "Jeansy", "Kobiety"));
        itemRepository.save(new Item("Jeansy Levis", "Levis", "XL", "Nowy", "Pomarańczowy", LocalDateTime.parse("2020-06-11T15:56:45"), "Fajna rzecz", new BigDecimal("35.00"), "/img/items/womens/jeans/j_3.jpg", "Jeansy", "Kobiety"));
        itemRepository.save(new Item("Trampki CK", "Calvin Klein", "38", "Nowy", "Pomarańczowy", LocalDateTime.parse("2020-06-11T15:56:45"), "Fajna rzecz", new BigDecimal("75.00"), "/img/items/womens/shoes/s_1.jpg", "Buty", "Kobiety"));
        itemRepository.save(new Item("Buty HM", "Tommy Hilfiger", "33", "Nowy", "Czarny", LocalDateTime.parse("2020-06-11T15:56:45"), "Fajna rzecz", new BigDecimal("45.00"), "/img/items/womens/shoes/s_2.jpg", "Buty", "Kobiety"));
        itemRepository.save(new Item("Szpilki", "CCC", "35", "Nowy", "Biały", LocalDateTime.parse("2020-06-11T15:56:45"), "Fajna rzecz", new BigDecimal("70.00"), "/img/items/womens/shoes/s_3.jpg", "Buty", "Kobiety"));
        itemRepository.save(new Item("T-shirt HM", "H&M", "S", "Nowy", "Biały", LocalDateTime.parse("2020-06-11T15:56:45"), "Fajna rzecz", new BigDecimal("40.00"), "/img/items/womens/tshirts/t_1.jpg", "T-shirty", "Kobiety"));
        itemRepository.save(new Item("Koszulka HM", "Tommy Hilfiger", "XS", "Idealny", "Czerwony", LocalDateTime.parse("2020-06-11T15:56:45"), "Fajna rzecz", new BigDecimal("50.00"), "/img/items/womens/tshirts/t_2.jpg", "T-shirty", "Kobiety"));
        itemRepository.save(new Item("T-s ck", "Calvin Klein", "XS", "Idealny", "Czerwony", LocalDateTime.parse("2020-06-11T15:56:45"), "Fajna rzecz", new BigDecimal("20.00"), "/img/items/womens/tshirts/t_3.jpg", "T-shirty", "Kobiety"));
        itemRepository.save(new Item("T-s ck", "Calvin Klein", "XS", "Nowy", "Pomarańczowy", LocalDateTime.parse("2020-06-11T15:56:45"), "Do sprzedania, polecam!", new BigDecimal("135.00"), "/img/items/unknown.png", "Jeansy", "Kobiety"));
    }
}
