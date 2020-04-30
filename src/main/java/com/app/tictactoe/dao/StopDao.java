package com.app.tictactoe.dao;

import com.app.tictactoe.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StopDao extends JpaRepository<Stop, Long> {
    Optional<Stop> findByGameId(Long id);
    Boolean existsByGameId(Long id);
    void deleteByGameId(Long id);
}
