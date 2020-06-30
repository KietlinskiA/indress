package pl.kietlinski.indress.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kietlinski.indress.model.AppUser;
import pl.kietlinski.indress.repository.AppUserRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AppUserService {

    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser findAppUser(String appuserUsername) {
        Optional<AppUser> optionalAppUser = Optional.of(appUserRepository.findAppUserByUsername(appuserUsername));
        return optionalAppUser.get();
    }

    public boolean addNewAppUser(AppUser newAppUser) {
        if(checkData(newAppUser)){
            newAppUser.setWallet(new BigDecimal(1000));
            newAppUser.setPassword(passwordEncoder.encode(newAppUser.getPassword()));
            appUserRepository.save(newAppUser);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkData(AppUser appUser){
        int users = appUserRepository.countAppUsersByUsername(appUser.getUsername());
        if(appUser.getUsername().length() >= 6
                && appUser.getPassword().length() >= 8
                && appUser.getPassword().length() <= 32
                && users <= 0){
            return true;
        } else {
            return false;
        }
    }

    public void saveAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

}
