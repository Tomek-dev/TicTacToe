package com.app.tictactoe.dao;

import com.app.tictactoe.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopDao extends JpaRepository<Stop, Long> {
}
