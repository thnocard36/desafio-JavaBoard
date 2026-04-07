--liquibase formatted sql
--changeset thnocard36:202603261530
--comment: blocks table create

    CREATE TABLE IF NOT EXISTS Blocks (
        id INT AUTO_INCREMENT PRIMARY KEY,
        block_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        block_reason VARCHAR(255) NOT NULL,
        unblocked_at TIMESTAMP NULL,
        unblock_reason VARCHAR(255) NOT NULL,
        card_id INT NOT NULL,
        CONSTRAINT cards__blocks_fk FOREIGN KEY (card_id) REFERENCES Cards(id) ON DELETE CASCADE
    ) ENGINE=InnoDB;

--rollback DROP TABLE BLOCKS