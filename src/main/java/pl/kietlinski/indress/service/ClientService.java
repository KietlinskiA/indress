package pl.kietlinski.indress.service;

import pl.kietlinski.indress.model.Item;
import pl.kietlinski.indress.model.LoginHyperlink;

import java.util.List;

public interface ClientService {

    LoginHyperlink getLoginHyperlink(boolean isUserInRole);

    List<Item> getLastAddedItemList();

    int getAdNumber();
}
