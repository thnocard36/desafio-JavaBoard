--liquibase formatted sql
--changeset thnocard36:202603261515
--comment: boards_columns table create

    CREATE TABLE IF NOT EXISTS Boards_Columns (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        `order` INT NOT NULL,
        kind VARCHAR(7) NOT NULL,
        board_id INT NOT NULL,
        CONSTRAINT boards__boards_columns_fk FOREIGN KEY(board_id) REFERENCES Boards(id) ON DELETE CASCADE,
        CONSTRAINT id_order_uk UNIQUE KEY unique_board_id_order (board_id, `order`)
    ) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS_COLUMNS