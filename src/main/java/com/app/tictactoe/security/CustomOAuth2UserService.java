package com.app.tictactoe.security;

import com.app.tictactoe.dao.PlayerDao;
import com.app.tictactoe.dao.UserDao;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.builder.PlayerBuilder;
import com.app.tictactoe.other.builder.UserBuilder;
import com.app.tictactoe.other.enums.Provider;
import com.app.tictactoe.other.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private UserDao userDao;
    private PlayerDao playerDao;

    @Autowired
    public CustomOAuth2UserService(UserDao userDao, PlayerDao playerDao) {
        this.userDao = userDao;
        this.playerDao = playerDao;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return processOAuth2User(user);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User){
        OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo(oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())){
            throw new OAuth2AuthenticationProcessingException("Please enable public email in GitHub settings.");
        }
        Optional<User> userOptional = userDao.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()){
            user = userOptional.get();
            if(user.getProvider().equals(Provider.BASIC)){
                throw new OAuth2AuthenticationProcessingException("Looks like you are already signed up with basic authorization. " +
                        "Please use your actual account or contact with support.");
            }
            user = update(user, oAuth2UserInfo);
            return UserPrincipal.create(user);
        }
        user = register(oAuth2UserInfo);
        return UserPrincipal.create(user);
    }

    private User update(User user, OAuth2UserInfo oAuth2UserInfo){
        user.setUsername(oAuth2UserInfo.getName());
        return userDao.save(user);
    }

    private User register(OAuth2UserInfo oAuth2UserInfo){
        User user = UserBuilder.builder()
                .username(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .provider(Provider.OAUTH)
                .roles(Collections.singleton(Role.User))
                .build();
        Player player = PlayerBuilder.builder()
                .user(user)
                .build();
        userDao.save(user);
        playerDao.save(player);
        user.setPlayer(player);
        return user;
    }
}
