package fiap.tds.resource;

import fiap.tds.exception.AvaliacaoInvalidaException;
import fiap.tds.model.Avaliacao;
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
    public Response cadastrar(Avaliacao avaliacao) {
        try {
            if (avaliacao.getTipoConstrucao() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("O campo 'tipoConstrucao' é obrigatório e não foi fornecido ou é inválido.")
                        .build();
            }
            if (avaliacao.getNivelRisco() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("O campo 'nivelRisco' é obrigatório e não foi fornecido ou é inválido.")
                        .build();
            }
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
