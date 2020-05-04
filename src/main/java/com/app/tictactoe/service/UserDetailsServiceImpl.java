package com.app.tictactoe.service;

import com.app.tictactoe.dao.UserDao;
import com.app.tictactoe.model.User;
import com.app.tictactoe.other.enums.Provider;
import com.app.tictactoe.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userOptional = userDao.findByUsernameAndProvider(s, Provider.BASIC);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return UserPrincipal.create(user);
    }
}
