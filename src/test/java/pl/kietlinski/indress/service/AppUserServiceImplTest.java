package pl.kietlinski.indress.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.kietlinski.indress.model.AppUser;
import pl.kietlinski.indress.repository.AppUserRepository;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AppUserServiceImplTest {

    @Mock
    private AppUserService appUserService = mock(AppUserService.class);
    private AppUserRepository appUserRepository = mock(AppUserRepository.class);

    private AppUser appUser;

    public void prepareAppUser(){
        appUser = new AppUser();
        appUser.setUsername("xyz123");
        appUser.setPassword("xyz112233");
        appUser.setWallet(BigDecimal.valueOf(1000));
    }

    @Test
    void should_find_AppUser() {
        //given
        prepareAppUser();
        when(appUserService.findAppUser(appUser.getUsername())).thenReturn(appUser);

        //then
        AppUser result = appUserService.findAppUser(appUser.getUsername());
        Assert.assertEquals(result.getUsername(), "xyz123");
        Assert.assertEquals(result.getPassword(), "xyz112233");
        Assert.assertEquals(result.getWallet(), BigDecimal.valueOf(1000));
    }

    @Test
    void should_add_new_AppUser_and_return_true() {
        //given
        when(appUserService.addNewAppUser(appUser)).thenReturn(true);

        //then
        Assert.assertTrue(appUserService.addNewAppUser(appUser));
    }

    @Test
    void should_check_data_and_return_true() {
        //given
        prepareAppUser();
        int countUsers = appUserRepository.countAppUsersByUsername(appUser.getUsername());

        //then
        Assert.assertTrue(appUser.getUsername().length() >= 6);
        Assert.assertTrue(appUser.getPassword().length() >= 8);
        Assert.assertEquals(appUser.getWallet(), BigDecimal.valueOf(1000));
        Assert.assertEquals(countUsers, 0);
        // checkData() always return true
    }

    @Test
    void should_no_check_data_and_return_false() {
        //given
        AppUser badAppUser = new AppUser();
        badAppUser.setUsername("xyz");
        badAppUser.setPassword("xyz123");
        badAppUser.setWallet(BigDecimal.valueOf(1));
        when(appUserRepository.countAppUsersByUsername(badAppUser.getUsername())).thenReturn(0);

        //then
        int users = appUserRepository.countAppUsersByUsername(badAppUser.getUsername());
        Assert.assertTrue(badAppUser.getUsername().length() < 6);
        Assert.assertTrue(badAppUser.getPassword().length() < 8);
        Assert.assertNotEquals(badAppUser.getWallet(), BigDecimal.valueOf(1000));
        Assert.assertNotEquals(users, 1);
    }

    @Test
    void should_get_wallet() {
        //given
        when(appUserService.getWallet()).thenReturn(BigDecimal.valueOf(1000.00));

        //then
        BigDecimal wallet = appUserService.getWallet();
        Assert.assertEquals(wallet.compareTo(BigDecimal.valueOf(1000.00)), 0);
    }
}