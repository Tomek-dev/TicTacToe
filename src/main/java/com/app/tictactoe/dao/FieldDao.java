package com.app.tictactoe.dao;

import com.app.tictactoe.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldDao extends JpaRepository<Field, Long> {
}
