package br.com.thnocard.board.services;

import br.com.thnocard.board.persistence.config.dao.BoardColumnDAO;
import br.com.thnocard.board.persistence.config.dao.BoardDAO;
import br.com.thnocard.board.persistence.config.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class BoardServices {

    private final Connection connection;


    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        try {
            dao.insert(entity);
            var columns = entity.getBoardColumns().stream().map(c -> {
                c.setBoard(entity);
                return c;
            }).toList();
            for(var column : columns) {
                boardColumnDAO.insert(column);
            }
            connection.commit();
        } catch(SQLException e) {
            connection.rollback();
            throw e;
        }
        return entity;
    }

    public boolean delete(final long id) throws SQLException {
        var dao = new BoardDAO(connection);
        try {
            if (! dao.exists(id)) {
                return false;
            }
            dao.delete(id);
            connection.commit();
            return true;
        } catch(SQLException p) {
            connection.rollback();
            throw p;
        }
    }
}