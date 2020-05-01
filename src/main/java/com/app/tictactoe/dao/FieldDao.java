package com.app.tictactoe.dao;

import com.app.tictactoe.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldDao extends JpaRepository<Field, Long> {
    List<Field> findByGameId(Long id);
}
