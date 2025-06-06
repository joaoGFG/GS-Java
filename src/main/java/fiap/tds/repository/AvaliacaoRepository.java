package fiap.tds.repository;


import fiap.tds.exception.AvaliacaoInvalidaException;
import fiap.tds.infrastructure.DatabaseConfig;
import fiap.tds.model.Avaliacao;
import fiap.tds.model.NivelRisco;
import fiap.tds.model.TipoConstrucao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoRepository {

    public boolean inserir(Avaliacao avaliacao) {
        String sql = """
            INSERT INTO Avaliacoes 
            (usuario_id, cidade, estado, mora_emergencia, rua_alaga, tipo_construcao, numero_pessoas, nivel_risco)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, avaliacao.getUsuario_id());
            stmt.setBoolean(4, avaliacao.isMoraEmEncosta());
            stmt.setBoolean(5, avaliacao.isRuaAlaga());

            if (avaliacao.getTipoConstrucao() == null) {
                throw new AvaliacaoInvalidaException("Tipo de construção não pode ser nulo.");
            }
            stmt.setString(6, avaliacao.getTipoConstrucao().name());
            stmt.setInt(7, avaliacao.getNumeroPessoas());

            if (avaliacao.getNivelRisco() == null) {
                throw new AvaliacaoInvalidaException("Nível de risco não pode ser nulo.");
            }

            stmt.setString(8, avaliacao.getNivelRisco().name());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new AvaliacaoInvalidaException("Erro ao inserir avaliação: " + e.getMessage());
        }
    }

    public Avaliacao buscarPorId(int id) {
        String sql = "SELECT * FROM avaliacoes WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAvaliacao(rs);
            }
            throw new AvaliacaoInvalidaException("Avaliação não encontrada com ID " + id);

        } catch (SQLException e) {
            throw new AvaliacaoInvalidaException("Erro ao buscar avaliação: " + e.getMessage());
        }
    }

    public List<Avaliacao> listarPorUsuario(int usuario_id) {
        String sql = "SELECT * FROM avaliacoes WHERE usuario_id = ?";
        List<Avaliacao> lista = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapResultSetToAvaliacao(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new AvaliacaoInvalidaException("Erro ao listar avaliações: " + e.getMessage());
        }
    }

    public boolean atualizar(int id, Avaliacao novaAvaliacao) {
        String sql = """
            UPDATE avaliacoes SET
            cidade = ?, estado = ?, mora_encosta = ?, rua_alaga = ?, 
            tipo_construcao = ?, numero_pessoas = ?, nivel_risco = ?
            WHERE id = ?
        """;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, novaAvaliacao.isMoraEmEncosta());
            stmt.setBoolean(2, novaAvaliacao.isRuaAlaga());
            stmt.setString(3, novaAvaliacao.getTipoConstrucao().name());
            stmt.setInt(4, novaAvaliacao.getNumeroPessoas());
            stmt.setString(5, novaAvaliacao.getNivelRisco().name());
            stmt.setInt(6, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new AvaliacaoInvalidaException("Erro ao atualizar avaliação: " + e.getMessage());
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM avaliacoes WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new AvaliacaoInvalidaException("Erro ao deletar avaliação: " + e.getMessage());
        }
    }

    private Avaliacao mapResultSetToAvaliacao(ResultSet rs) throws SQLException {
        Avaliacao a = new Avaliacao();
        a.setId(rs.getInt("id"));
        a.setUsuario_id(rs.getInt("usuario_id"));
        a.setMoraEmEncosta(rs.getBoolean("mora_emergencia"));
        a.setRuaAlaga(rs.getBoolean("rua_alaga"));
        a.setTipoConstrucao(TipoConstrucao.valueOf(rs.getString("tipo_construcao")));
        a.setNumeroPessoas(rs.getInt("numero_pessoas"));
        a.setNivelRisco(NivelRisco.valueOf(rs.getString("nivel_risco")));
        return a;
    }
}
