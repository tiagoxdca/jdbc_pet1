package database;

import java.sql.*;

public class DB {

    // JDBC driver name and database URL
    private String DB_URL = "jdbc:mysql://localhost/webdb";

    //  Database credentials
    private String USER = "root";
    private String PASS = "q1w2e3r4";

    public Connection conn;

    private static DB instance;

    private DB() throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        conn.setAutoCommit(false);
    }

    public static synchronized DB getDB() throws SQLException {
         if(instance == null) {
             instance = new DB();
         }

        return instance;
    }

    public Connection getConn() {
        return conn;
    }

    public ResultSet query(String query) throws SQLException{
        PreparedStatement statement = instance.conn.prepareStatement(query);
        ResultSet res = statement.executeQuery(query);
        return res;
    }

    public PreparedStatement queryPrepared(String query) throws SQLException{
        PreparedStatement statement = instance.conn.prepareStatement(query);
        return statement;
    }

    //CRUD
    public int create(String query) throws SQLException {
        PreparedStatement statement = instance.conn.prepareStatement(query);
        int numRowsAffected = statement.executeUpdate(query);
        return numRowsAffected;
    }

    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
