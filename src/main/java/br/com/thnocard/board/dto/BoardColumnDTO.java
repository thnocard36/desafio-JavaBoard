package br.com.thnocard.board.dto;

import br.com.thnocard.board.persistence.config.entity.BoardColumnKindEnum;

public record BoardColumnDTO(Long id,
                             String name,
                             BoardColumnKindEnum kind,
                             int cardsAmoutn) {
}