package br.com.thnocard.board.persistence.config.entity;

import lombok.Data;

@Data
public class BoardColumnEntity {

    private Long id;
    private String name;
    private int order;
    private BoardColumnKindEnum kind;
    private BoardEntity board = new BoardEntity();

    /**
     * d INT AUTO_INCREMENT PRIMARY KEY,
     *         name VARCHAR(255) NOT NULL,
     *         `order` INT NOT NULL,
     *         kind VARCHAR(7) NOT NULL,
     *         board_id INT NOT NULL,
     */
}