package br.com.thnocard.board.services;

import br.com.thnocard.board.dto.CardDetailsDTO;
import br.com.thnocard.board.persistence.config.dao.CardDAO;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class CardQueryService {

    private final Connection connection;

    public Optional<CardDetailsDTO> findById(Long id) throws SQLException {
        var dao = new CardDAO(connection);
        return dao.findById(id);
    }

}