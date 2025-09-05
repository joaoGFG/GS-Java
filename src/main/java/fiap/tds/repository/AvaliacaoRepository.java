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
            stmt.setString(2, avaliacao.getCidade());
            stmt.setString(3, avaliacao.getEstado());
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

    private Avaliacao mapResultSetToAvaliacao(ResultSet rs) throws SQLException {
        Avaliacao a = new Avaliacao();
        a.setId(rs.getInt("id"));
        a.setUsuario_id(rs.getInt("usuario_id"));
        a.setCidade(rs.getString("cidade"));
        a.setEstado(rs.getString("estado"));
        a.setMoraEmEncosta(rs.getBoolean("mora_emergencia"));
        a.setRuaAlaga(rs.getBoolean("rua_alaga"));
        a.setTipoConstrucao(TipoConstrucao.valueOf(rs.getString("tipo_construcao")));
        a.setNumeroPessoas(rs.getInt("numero_pessoas"));
        a.setNivelRisco(NivelRisco.valueOf(rs.getString("nivel_risco")));
        return a;
    }
}