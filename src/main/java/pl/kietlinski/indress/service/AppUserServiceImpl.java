package pl.kietlinski.indress.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kietlinski.indress.model.AppUser;
import pl.kietlinski.indress.repository.AppUserRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Primary
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser findAppUser(String username) {
        Optional<AppUser> optionalAppUser = Optional.of(appUserRepository.findAppUserByUsername(username));
        return optionalAppUser.orElseGet(AppUser::new);
    }

    @Override
    public boolean addNewAppUser(AppUser newAppUser) {
        if(checkData(newAppUser)){
            newAppUser.setWallet(new BigDecimal(1000));
            newAppUser.setPassword(passwordEncoder.encode(newAppUser.getPassword()));
            appUserRepository.save(newAppUser);
        }
        return checkData(newAppUser);
    }

    public boolean checkData(AppUser appUser){
        int users = appUserRepository.countAppUsersByUsername(appUser.getUsername());
        return (appUser.getUsername().length() >= 6
                && appUser.getPassword().length() >= 8
                && users == 0);
    }

    @Override
    public void saveAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    @Override
    public BigDecimal getWallet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")){
            AppUser authAppUser = (AppUser) authentication.getPrincipal();
            AppUser appUserInRepo = appUserRepository.findAppUserByUsername(authAppUser.getUsername());
            return appUserInRepo.getWallet();
        } else {
            return BigDecimal.valueOf(0.00);
        }
    }
}
