package com.app.tictactoe.other.enums;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements GrantedAuthority {
    User("ROLE_USER");

    private String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
