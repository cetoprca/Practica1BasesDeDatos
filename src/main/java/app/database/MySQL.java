package app.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySQL {
    public Connection getConnection(String host, String port, String user, String password){
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "?serverTimezone=UTC", user, password);

        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

        return connection;
    }

    public Connection getConnection(String host, String port, String user, String password, String nameDB){
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + nameDB + "?serverTimezone=UTC", user, password);

        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

        return connection;
    }

    public void execute(Connection connection, String SQL){
        try {
            Statement statement = connection.createStatement();
            statement.execute(SQL);

        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResultSet executeQuery(Connection connection, String SQL){
        ResultSet resultSet;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

        return resultSet;
    }
}
