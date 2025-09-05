package fiap.tds.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String HOST = "db.fggbbvlmhltcswxadefm.supabase.co"; // Cole o Host aqui
    private static final String DATABASE = "postgres"; // Geralmente é 'postgres'
    private static final String PORT = "5432"; // Cole a Porta aqui
    private static final String USER = "postgres"; // Cole o User aqui
    private static final String PASSWORD = "qGRXOeIaiOvELJZv"; // Cole a Senha aqui

    // URL de conexão para PostgreSQL
    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE;

    public static Connection getConnection() throws SQLException {
        try {
            // Garante que o driver do PostgreSQL foi carregado
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Este erro acontece se o .jar do driver não estiver no projeto
            throw new SQLException("Driver do PostgreSQL não encontrado!", e);
        }
    }
}