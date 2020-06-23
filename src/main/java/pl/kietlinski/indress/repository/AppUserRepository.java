package pl.kietlinski.indress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kietlinski.indress.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findAppUserByUsername(String username);

}
