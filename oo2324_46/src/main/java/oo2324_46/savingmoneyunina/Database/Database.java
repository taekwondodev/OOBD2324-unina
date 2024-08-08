package oo2324_46.savingmoneyunina.Database;

import java.sql.*;

public class Database {
    private static Database database = null;
    private Connection conn = null;

    //costruttore privato -> Singlethon
    private Database() {}

    //metodo per accedere al singleton privato
    public static Database getDatabase() {
        if (database == null)
            database = new Database();

        return database;
    }

    //metodo per creare il collegamento
    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                String url = "jdbc:postgresql://localhost:5432/postgres";
                String user = "postgres";
                String password = "postgres";

                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }

        return conn;
    }

    public void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

    public void closePreparedStatement(PreparedStatement ps) throws SQLException {
        ps.close();
    }

    public void closeResultSet(ResultSet rs) throws SQLException {
        rs.close();
    }
}
