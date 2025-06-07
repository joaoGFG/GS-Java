package fiap.tds.resource;

import fiap.tds.bo.AvaliacaoBO;
import fiap.tds.dtos.AvaliacaoDTO;
import fiap.tds.exception.AvaliacaoInvalidaException;
import fiap.tds.model.Avaliacao;
import fiap.tds.model.NivelRisco;
import fiap.tds.repository.AvaliacaoRepository;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("avaliacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvaliacaoResource {

    private AvaliacaoRepository repository = new AvaliacaoRepository();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(AvaliacaoDTO dto) {
        try {
            AvaliacaoBO bo = new AvaliacaoBO();
            String nivelRisco = bo.calcularRisco(dto);

            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setUsuario_id(dto.getUsuarioId());
            avaliacao.setCidade(dto.getCidade());
            avaliacao.setEstado(dto.getEstado());
            avaliacao.setRuaAlaga(dto.isRuaAlaga());
            avaliacao.setMoraEmEncosta(dto.isMoraEmEncosta());
            avaliacao.setNumeroPessoas(dto.getNumeroPessoas());
            avaliacao.setTipoConstrucao(dto.getTipoConstrucao());
            avaliacao.setNivelRisco(NivelRisco.valueOf(nivelRisco));

            System.out.println("Recebido JSON:");
            System.out.println("usuarioId: " + dto.getUsuarioId());
            System.out.println("cidade: " + dto.getCidade());
            System.out.println("estado: " + dto.getEstado());
            System.out.println("moraEmEncosta: " + dto.isMoraEmEncosta());
            System.out.println("ruaAlaga: " + dto.isRuaAlaga());
            System.out.println("tipoConstrucao: " + dto.getTipoConstrucao());
            System.out.println("numeroPessoas: " + dto.getNumeroPessoas());

            boolean sucesso = repository.inserir(avaliacao);

            if (sucesso) {
                return Response.status(Response.Status.CREATED).entity(avaliacao).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Não foi possível cadastrar a avaliação.").build();
            }
        } catch (AvaliacaoInvalidaException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao processar cadastro: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/simular")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response simularRisco(AvaliacaoDTO dto) {
        AvaliacaoBO bo = new AvaliacaoBO();
        String nivelRisco = bo.calcularRisco(dto);
        return Response.ok().entity("{\"nivelRisco\":\"" + nivelRisco + "\"}").build();
    }

}
