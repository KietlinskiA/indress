package pl.kietlinski.indress.service;

import pl.kietlinski.indress.model.AppUser;

import java.math.BigDecimal;

public interface AppUserService {

    AppUser findAppUser(String username);

    boolean addNewAppUser(AppUser newAppUser);

    void saveAppUser(AppUser appUser);

    BigDecimal getWallet();


}
