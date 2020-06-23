package pl.kietlinski.indress.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kietlinski.indress.model.AppUser;
import pl.kietlinski.indress.repository.AppUserRepository;

import java.util.Optional;

@Service
public class AppUserService {

    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser findAppUser(AppUser appUser){
        Optional<AppUser> optionalAppUser = Optional.of(appUserRepository.findAppUserByUsername(appUser.getUsername()));
        return optionalAppUser.get();
    }

    public void addNewAppUser(AppUser newAppUser){
        newAppUser.setPassword(passwordEncoder.encode(newAppUser.getPassword()));
        appUserRepository.save(newAppUser);
    }

}
