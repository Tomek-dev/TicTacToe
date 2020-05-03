package com.app.tictactoe.dao;

import com.app.tictactoe.model.Disconnect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DisconnectDao extends JpaRepository<Disconnect, Long> {
    Optional<Disconnect> findByGameId(Long id);
    List<Disconnect> findByDateLessThanEqual(LocalDateTime date);
}
