package fiap.tds.model;

import java.util.Objects;

public class Avaliacao {
    private int id;
    private int usuario_id;
    private boolean moraEmEncosta;
    private boolean ruaAlaga;
    private TipoConstrucao tipoConstrucao;
    private int numeroPessoas;
    private NivelRisco nivelRisco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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

    public NivelRisco getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(NivelRisco nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avaliacao avaliacao = (Avaliacao) o;
        return id == avaliacao.id && usuario_id == avaliacao.usuario_id && moraEmEncosta == avaliacao.moraEmEncosta && ruaAlaga == avaliacao.ruaAlaga && numeroPessoas == avaliacao.numeroPessoas && tipoConstrucao == avaliacao.tipoConstrucao && Objects.equals(nivelRisco, avaliacao.nivelRisco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario_id, moraEmEncosta, ruaAlaga, tipoConstrucao, numeroPessoas, nivelRisco);
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id=" + id +
                ", usuarioId=" + usuario_id +
                ", cidade='" + '\'' +
                ", estado='" + '\'' +
                ", moraEmEncosta=" + moraEmEncosta +
                ", ruaAlaga=" + ruaAlaga +
                ", tipoConstrucao=" + tipoConstrucao +
                ", numeroPessoas=" + numeroPessoas +
                ", nivelRisco='" + nivelRisco + '\'' +
                '}';
    }


}
