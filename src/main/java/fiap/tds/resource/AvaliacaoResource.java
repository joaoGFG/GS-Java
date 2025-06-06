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
    public Response cadastrar(AvaliacaoDTO dto) {
        try {

            System.out.println("Recebido JSON:");
            System.out.println("usuarioId: " + dto.getUsuarioId());
            System.out.println("moraEmEncosta: " + dto.isMoraEmEncosta());
            System.out.println("ruaAlaga: " + dto.isRuaAlaga());
            System.out.println("tipoConstrucao: " + dto.getTipoConstrucao());
            System.out.println("numeroPessoas: " + dto.getNumeroPessoas());

            if (dto.getTipoConstrucao() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("O campo 'tipoConstrucao' é obrigatório e não foi fornecido ou é inválido.")
                        .build();
            }

            AvaliacaoBO bo = new AvaliacaoBO();
            String nivelRisco = bo.calcularRisco(dto);

            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setUsuario_id(dto.getUsuarioId());
            avaliacao.setRuaAlaga(dto.isRuaAlaga());
            avaliacao.setMoraEmEncosta(dto.isMoraEmEncosta());
            avaliacao.setNumeroPessoas(dto.getNumeroPessoas());
            avaliacao.setTipoConstrucao(dto.getTipoConstrucao());
            avaliacao.setNivelRisco(NivelRisco.valueOf(nivelRisco));

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


    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            Avaliacao avaliacao = repository.buscarPorId(id);
            return Response.ok(avaliacao).build();
        } catch (AvaliacaoInvalidaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/usuario/{usuarioId}")
    public Response listarPorUsuario(@PathParam("usuarioId") int usuarioId) {
        try {
            List<Avaliacao> avaliacoes = repository.listarPorUsuario(usuarioId);
            return Response.ok(avaliacoes).build();
        } catch (AvaliacaoInvalidaException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Avaliacao novaAvaliacao) {
        try {
            boolean atualizado = repository.atualizar(id, novaAvaliacao);
            if (atualizado) {
                return Response.ok().entity("Avaliação atualizada com sucesso.").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Avaliação não encontrada.").build();
            }
        } catch (AvaliacaoInvalidaException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            boolean deletado = repository.deletar(id);
            if (deletado) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Avaliação não encontrada.").build();
            }
        } catch (AvaliacaoInvalidaException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
