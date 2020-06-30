package pl.kietlinski.indress.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.kietlinski.indress.model.Item;
import pl.kietlinski.indress.repository.ItemRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ItemServiceImplTest {

    @Mock
    private ItemService itemService = mock(ItemService.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    private Item item;

    public void prepareItem(){
        item = new Item();
        item.setName("Koszulka CK");
        item.setBrand("Calvin Klein");
        item.setSize("XS");
        item.setCondition("Nowy");
        item.setColor("Pomarańczowy");
        item.setAddedData(LocalDateTime.parse("2020-06-11T15:56:45"));
        item.setDescription("Do sprzedania, polecam!");
        item.setPrice(new BigDecimal("99.00"));
        item.setImg("/img/items/unknown.png");
        item.setMainCategory("Jeansy");
        item.setClothes("Kobiety");
    }

    @Test
    void should_get_item_by_id() {
        //given
        prepareItem();
        when(itemService.getItemById(1L)).thenReturn(item);

        //then
        Item result = itemService.getItemById(1L);
        Assert.assertEquals(result.getName(), "Koszulka CK");
        Assert.assertEquals(result.getBrand(), "Calvin Klein");
        Assert.assertEquals(result.getSize(), "XS");
        Assert.assertEquals(result.getColor(), "Pomarańczowy");
        Assert.assertEquals(result.getClothes(), "Kobiety");
    }

}