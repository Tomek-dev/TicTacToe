package com.app.tictactoe.dao;

import com.app.tictactoe.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDao extends JpaRepository<Game, Long> {
}
