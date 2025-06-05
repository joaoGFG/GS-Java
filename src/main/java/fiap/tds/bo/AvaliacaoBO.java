package fiap.tds.bo;


import fiap.tds.dtos.AvaliacaoDTO;
import fiap.tds.model.TipoConstrucao;

public class AvaliacaoBO {

    public String calcularRisco(AvaliacaoDTO dto) {
        int pontos = 0;
        if (dto.moraEmEncosta) pontos += 3;
        if (dto.ruaAlaga) pontos += 2;

        if (dto.tipoConstrucao == TipoConstrucao.IMPROVISADA) pontos += 3;
        else if (dto.tipoConstrucao == TipoConstrucao.MADEIRA) pontos += 2;

        if (dto.numeroPessoas > 5) pontos += 1;

        if (pontos >= 7) return "ALTO";
        if (pontos >= 4) return "MÉDIO";
        return "BAIXO";
    }
}
