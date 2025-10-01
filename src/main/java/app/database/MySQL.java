package app.database;

import java.sql.*;

public class MySQL {

    private static MySQL instance; // Única instancia de la clase
    private Connection connection; // Conexión única

    // Datos de conexión (puedes parametrizarlos o cargarlos de un config)
    private String host;
    private String port;
    private String user;
    private String password;
    private String dbName;

    // Constructor privado → evita instanciar la clase directamente
    private MySQL(String host, String port, String user, String password, String dbName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.dbName = dbName;

        try {
            String url = "jdbc:mysql://" + host + ":" + port;
            if (dbName != null && !dbName.isEmpty()) {
                url += "/" + dbName;
            }
            url += "?serverTimezone=UTC";

            this.connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public static MySQL getInstance(String host, String port, String user, String password, String dbName) {
        if (instance == null) {
            instance = new MySQL(host, port, user, password, dbName);
        }
        return instance;
    }

    public Connection getConnection(){
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
