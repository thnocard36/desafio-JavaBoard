package br.com.thnocard.board.services;

import br.com.thnocard.board.persistence.config.dao.BoardColumnDAO;
import br.com.thnocard.board.persistence.config.dao.BoardDAO;
import br.com.thnocard.board.persistence.config.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var optional = dao.findById(id);

        if(optional.isPresent()) {
            var entity = optional.get();
            entity.setBoardColumns(boardColumnDAO.findByBoardid(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }
}