package pl.isa.fitly.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.isa.fitly.model.UserData;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private UserData userData;

    public CustomUserDetails() {
    }

    public CustomUserDetails(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userData.getRole()));
    }

    @Override
    public String getPassword() {
        return userData.getPassword();
    }

    @Override
    public String getUsername() {
        return userData.getEmail();
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
