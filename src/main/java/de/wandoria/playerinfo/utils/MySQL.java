package de.wandoria.playerinfo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Getter
@Setter
@AllArgsConstructor
public class MySQL {

    public static Connection connection = null;

    String host;
    String port;
    String database;
    String username;
    String password;

    // Getter
    public Connection getConnection() {
        return connection;
    }


    // Methods
    public void connect() {
        if (connection != null) {
            try {
                connection = java.sql.DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
                System.out.println("[PlayerInfo] MySQL connected!");
            } catch (java.sql.SQLException e) {
                System.out.println("[PlayerInfo] MySQL connection failed!");
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("[PlayerInfo] MySQL disconnected!");
            } catch (java.sql.SQLException e) {
                System.out.println("[PlayerInfo] MySQL disconnection failed!");
                e.printStackTrace();
            }
        }
    }

    public void update(String query) {
        try {
            getStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet query(String query) {

        ResultSet resultSet = null;
        try {
            resultSet = getStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public Statement getStatement() {

        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return statement;
    }



}
