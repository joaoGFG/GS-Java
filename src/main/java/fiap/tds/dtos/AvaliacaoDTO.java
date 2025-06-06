package fiap.tds.dtos;

import fiap.tds.model.TipoConstrucao;

public class AvaliacaoDTO {
    private int usuarioId;
    private boolean moraEmEncosta;
    private boolean ruaAlaga;
    private TipoConstrucao tipoConstrucao;
    private int numeroPessoas;

    public AvaliacaoDTO() {}

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isMoraEmEncosta() {
        return moraEmEncosta;
    }

    public void setMoraEmEncosta(boolean moraEmEncosta) {
        this.moraEmEncosta = moraEmEncosta;
    }

    public boolean isRuaAlaga() {
        return ruaAlaga;
    }

    public void setRuaAlaga(boolean ruaAlaga) {
        this.ruaAlaga = ruaAlaga;
    }

    public TipoConstrucao getTipoConstrucao() {
        return tipoConstrucao;
    }

    public void setTipoConstrucao(TipoConstrucao tipoConstrucao) {
        this.tipoConstrucao = tipoConstrucao;
    }

    public int getNumeroPessoas() {
        return numeroPessoas;
    }

    public void setNumeroPessoas(int numeroPessoas) {
        this.numeroPessoas = numeroPessoas;
    }
}
