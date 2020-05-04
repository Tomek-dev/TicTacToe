package com.app.tictactoe.security;

import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class UserPrincipal implements UserDetails, OAuth2User {

    private String username;
    private String password;
    private Player player;
    private Set<Role> roles = new HashSet<>();

    public static UserPrincipal create(User user){
        UserPrincipal principal = new UserPrincipal();
        principal.setPassword(user.getPassword());
        principal.setUsername(user.getUsername());
        principal.setRoles(user.getRoles());
        principal.setPlayer(user.getPlayer());
        return principal;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
