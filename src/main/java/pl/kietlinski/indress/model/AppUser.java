package pl.kietlinski.indress.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@NoArgsConstructor
@Table(name = "app_users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appUser_id")
    private Long id;

    @Size(min = 6)
    @Column(name = "appUser_username", nullable = false, unique = true)
    private String username;

    @Size(min = 8)
    @Column(name = "appUser_password", nullable = false)
    private String password;

    @Column(name = "appUser_wallet")
    private BigDecimal wallet;

    public AppUser(@NotNull @Size(min = 6) String username, @NotNull @Size(min = 8, max = 32) String password) {
        this.username = username;
        this.password = password;
        this.wallet = new BigDecimal(BigInteger.valueOf(0), 2);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
