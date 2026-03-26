--liquibase formatted sql
--changeset thnocard36:202603261525
--comment: cards table create

    CREATE TABLE Cards(
        id INT AUTO_INCREMENT PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        description VARCHAR(255) NOT NULL,
        `order` INT NOT NULL,
        board_column_id INT NOT NULL,
        CONSTRAINT boards_columns__cards_fk FOREIGN KEY (board_column_id) REFERENCES Boards_Columns(id) ON DELETE CASCADE
    ) ENGINE=InnoDB;

--rollback DROP TABLE CARDS