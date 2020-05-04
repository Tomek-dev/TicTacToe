package com.app.tictactoe.dao;

import com.app.tictactoe.model.User;
import com.app.tictactoe.other.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameAndProvider(String username, Provider basic);
}
