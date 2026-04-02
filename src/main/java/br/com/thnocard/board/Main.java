package br.com.thnocard.board;

import br.com.thnocard.board.persistence.config.migration.StrategyMigri;
import br.com.thnocard.board.ui.MainMenu;

import java.sql.SQLException;

import static br.com.thnocard.board.persistence.config.config.ConnectionConfig.getConnection;

public class Main {

    public static void main(String[] args) throws SQLException {

        try(var connection = getConnection()) {
            new StrategyMigri(connection).executeMigration();
        }

        new MainMenu().execute();

    }
}