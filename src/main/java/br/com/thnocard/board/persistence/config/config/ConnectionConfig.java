package br.com.thnocard.board.persistence.config.config;

import static lombok.AccessLevel.PRIVATE;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = PRIVATE)
public final class ConnectionConfig {

    public static Connection getConnection() throws SQLException {
        var urlDB = "jdbc:mysql://172.30.0.22/board";
        var userDB = "root";
        var passDB = "P@ssw0rd123";

        var connection = DriverManager.getConnection(urlDB, userDB, passDB);
        // var conectK = DriverManager.getConnection("jdbc:mysql://172.30.0.22/board", "root", "P@ssw0rd123");

        connection.setAutoCommit(false);
        return connection;

    }

}