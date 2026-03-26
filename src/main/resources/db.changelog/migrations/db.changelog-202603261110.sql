--liquibase formatted sql
--changeset thnocard36:202603261110
--coment: boards table create

    CREATE TABLE Boards(
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL
    ) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS