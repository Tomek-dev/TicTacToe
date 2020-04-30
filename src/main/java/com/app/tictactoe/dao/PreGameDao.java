package com.app.tictactoe.dao;

import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.PreGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreGameDao extends JpaRepository<PreGame, Long> {
    Optional<PreGame> findTop1ByOrderByIdAsc();
    Boolean existsByPlayer(Player player);
    void deleteByPlayer(Player player);
}
