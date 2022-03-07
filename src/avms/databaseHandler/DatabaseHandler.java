package avms.databaseHandler;

import java.sql.*;

public abstract class DatabaseHandler {
    private final String CONNECTION_URL = "jdbc:mysql://localhost:3306/pap";
    private final String USERNAME = "pap_admin";
    private final String PASSWORD = "pap_admin";

    protected Connection connection;
    protected Statement statement;
    protected PreparedStatement preparedStatement;
    protected CallableStatement callableStatement;

    public DatabaseHandler() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        statement = connection.createStatement();
    }

    public void close() throws SQLException {
        connection.close();
    }
}
