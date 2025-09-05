package fiap.tds.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String HOST = "db.fggbbvlmhltcswxadefm.supabase.co"; 
    private static final String DATABASE = "postgres"; 
    private static final String PORT = "5432"; 
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "qGRXOeIaiOvELJZv"; 

    // URL de conexão para PostgreSQL com SSL obrigatório
    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE + "?sslmode=require";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do PostgreSQL não encontrado!", e);
        }
    }
}
