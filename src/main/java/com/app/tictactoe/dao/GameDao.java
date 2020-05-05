package com.app.tictactoe.dao;

import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.other.enums.Process;
import com.app.tictactoe.other.enums.Win;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameDao extends JpaRepository<Game, Long> {
    Boolean existsByXAndProcessOrOAndProcess(Player x, Process processX, Player o, Process processO);
    Optional<Game> findByXAndProcessOrOAndProcess(Player x, Process processX, Player o, Process processO);
    List<Game> findByXOrO(Player player, Player player1);
    Long countByXAndWinnerOrOAndWinner(Player player, Win draw, Player player1, Win draw1);
    Long countByXAndWinnerInOrOAndWinnerIn(Player x, List<Win> wins1, Player o, List<Win> wins2);
    Long countByXOrO(Player player, Player player1);
}
