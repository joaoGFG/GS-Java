package fiap.tds.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioAtualizacaoDTO {

    @JsonProperty("email")
    private String email;

    @JsonProperty("novoNome")
    private String novoNome;

    @JsonProperty("novaSenha")
    private String novaSenha;

    public UsuarioAtualizacaoDTO() {}

    public UsuarioAtualizacaoDTO(String email, String novoNome, String novaSenha) {
        this.email = email;
        this.novoNome = novoNome;
        this.novaSenha = novaSenha;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNovoNome() { return novoNome; }
    public void setNovoNome(String novoNome) { this.novoNome = novoNome; }

    public String getNovaSenha() { return novaSenha; }
    public void setNovaSenha(String novaSenha) { this.novaSenha = novaSenha; }
}
