package br.com.thnocard.board.services;

import br.com.thnocard.board.persistence.config.dao.CardDAO;
import br.com.thnocard.board.persistence.config.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class CardService {

    private final Connection connection;

    public CardEntity insert(final CardEntity cardEntity) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            dao.insert(cardEntity);
            connection.commit();
            return cardEntity;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}