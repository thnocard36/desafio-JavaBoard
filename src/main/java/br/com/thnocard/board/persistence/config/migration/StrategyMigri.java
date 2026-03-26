package br.com.thnocard.board.persistence.config.migration;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import static br.com.thnocard.board.persistence.config.config.ConnectionConfig.getConnection;


@AllArgsConstructor
public class StrategyMigri {

    private final Connection connection;

    // public void executeMigration() throws SQLException, FileNotFoundException {
    public void executeMigration() {

        var originalOut = System.out;
        var originalErr = System.err;

        try {
            try(var foz = new FileOutputStream("liquibase.log")) {
                System.setOut(new PrintStream(foz));
                System.setOut(new PrintStream(foz));

                try (var connection = getConnection();
                     var jdbcConnection = new JdbcConnection(connection);
                ) {
                    var liquibase = new Liquibase(
                            "db.changelog/db.changelog-master.yml",
                            new ClassLoaderResourceAccessor(),
                            jdbcConnection);
                    liquibase.update();
                } catch (SQLException | LiquibaseException e) {
                    e.printStackTrace();
                    System.setErr(originalErr);
                }
            }
        } catch(IOException exp) {
            exp.printStackTrace();
        } finally {
            System.setOut(originalOut);
            System.setOut(originalErr);
        }

    }

}