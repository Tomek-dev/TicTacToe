package com.app.tictactoe.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class OAuth2UserInfo {
    private Map<String, Object> attributes;

    public String getName(){
        return (String) attributes.get("login");
    }

    public String getEmail(){
        return (String) attributes.get("email");
    }
}
