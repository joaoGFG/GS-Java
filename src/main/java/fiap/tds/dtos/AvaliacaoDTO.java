package fiap.tds.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import fiap.tds.model.TipoConstrucao;

public class AvaliacaoDTO {

    @JsonProperty("usuarioId")
    private int usuarioId;

    @JsonProperty("cidade")
    private String cidade;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("moraEmEncosta")
    private boolean moraEmEncosta;

    @JsonProperty("ruaAlaga")
    private boolean ruaAlaga;

    @JsonProperty("tipoConstrucao")
    private TipoConstrucao tipoConstrucao;

    @JsonProperty("numeroPessoas")
    private int numeroPessoas;

    public AvaliacaoDTO() {}

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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
