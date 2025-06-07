package fiap.tds.repository;

import fiap.tds.exception.AvaliacaoInvalidaException;
import fiap.tds.exception.UsuarioNaoEncontradoException;
import fiap.tds.infrastructure.DatabaseConfig;
import fiap.tds.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO Usuario (NOME, EMAIL, SENHA) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) return false;

            String sqlBusca = "SELECT id_usuario FROM Usuario WHERE email = ?";
            try (PreparedStatement buscaStmt = conn.prepareStatement(sqlBusca)) {
                buscaStmt.setString(1, usuario.getEmail());
                ResultSet rs = buscaStmt.executeQuery();
                if (rs.next()) {
                    usuario.setId(rs.getInt("id_usuario"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário no banco de dados", e);
        }
        return true;
    }


    public Usuario buscarPorEmailSenha(String email, String senha) {
        String sql = "SELECT * FROM Usuario WHERE EMAIL = ? AND SENHA = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                return usuario;
            } else {
                throw new UsuarioNaoEncontradoException("Usuário não encontrado com o e-mail e senha informados.");
            }

        } catch (SQLException e) {
            throw new UsuarioNaoEncontradoException("Erro ao buscar usuário no banco de dados", e);
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuarios.add(usuario);
            }

            return usuarios;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência de e-mail no banco de dados", e);
        }
    }

    public boolean existeEmail(String email) {
        String sql = "SELECT 1 FROM Usuario WHERE EMAIL = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new UsuarioNaoEncontradoException("Erro ao buscar usuário no banco de dados", e);
        }
    }

    public boolean atualizar(String email, String novoNome, String novaSenha) {
        StringBuilder sql = new StringBuilder("UPDATE Usuario SET ");
        boolean atualizaNome = novoNome != null && !novoNome.trim().isEmpty();
        boolean atualizaSenha = novaSenha != null && !novaSenha.trim().isEmpty();

        if (!atualizaNome && !atualizaSenha) {
            return false;
        }

        List<Object> parametros = new ArrayList<>();

        if (atualizaNome) {
            sql.append("nome = ?");
            parametros.add(novoNome);
        }

        if (atualizaSenha) {
            if (atualizaNome) sql.append(", ");
            sql.append("senha = ?");
            parametros.add(novaSenha);
        }

        sql.append(" WHERE email = ?");
        parametros.add(email);

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new UsuarioNaoEncontradoException("Usuário com email '" + email + "' não encontrado.");
            }

            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário no banco de dados", e);
        }
    }

    public boolean deletarPorEmail(String email) {
        String sql = "DELETE FROM Usuario WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }


}
