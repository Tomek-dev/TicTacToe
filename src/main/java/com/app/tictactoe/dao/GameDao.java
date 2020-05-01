package com.app.tictactoe.dao;

import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.other.enums.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameDao extends JpaRepository<Game, Long> {
    Boolean existsByXOrOAndProcess(Player x, Player o, Process process);
    Optional<Game> findByXOrOAndProcess(Player x, Player o, Process during);
}
