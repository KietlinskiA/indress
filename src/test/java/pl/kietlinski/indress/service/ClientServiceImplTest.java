package pl.kietlinski.indress.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.kietlinski.indress.model.Item;
import pl.kietlinski.indress.model.LoginHyperlink;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ClientServiceImplTest {

    @Mock
    private ClientService clientService = mock(ClientService.class);

    private List<Item> itemList;

    public void prepareItemList(){
        itemList = new ArrayList<>();
        Item item = new Item();
        item.setName("Koszulka CK");
        item.setBrand("Calvin Klein");
        item.setSize("XS");
        item.setCondition("Nowy");
        item.setColor("Pomarańczowy");
        item.setAddedData(LocalDateTime.parse("2020-06-11T15:56:45"));
        item.setDescription("Do sprzedania, polecam!");
        item.setPrice(new BigDecimal("135.00"));
        item.setImg("/img/items/unknown.png");
        item.setMainCategory("Jeansy");
        item.setClothes("Kobiety");
        itemList.add(item);
    }

    @Test
    void should_get_loginHyperlink() {
        //given
        LoginHyperlink loginHyperlink = new LoginHyperlink("/logout", "WYLOGUJ");
        when(clientService.getLoginHyperlink(true)).thenReturn(loginHyperlink);

        //then
        LoginHyperlink result = clientService.getLoginHyperlink(true);
        Assert.assertEquals(result.getHyperlink(), "/logout");
        Assert.assertEquals(result.getLoginButton(), "WYLOGUJ");
    }

    @Test
    void should_get_last_added_itemList() {
        //given
        prepareItemList();
        when(clientService.getLastAddedItemList()).thenReturn(itemList);

        //then
        List<Item> result = clientService.getLastAddedItemList();
        Assert.assertEquals(result.size(), 1);
        Assert.assertFalse(result.isEmpty());
    }

}