package fiap.tds.resource;

import fiap.tds.dtos.UsuarioAtualizacaoDTO;
import fiap.tds.dtos.UsuarioDTO;
import fiap.tds.dtos.UsuarioResponseDTO;
import fiap.tds.model.Usuario;
import fiap.tds.repository.UsuarioRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/usuarios")
public class UsuarioResource {

    private UsuarioRepository repository = new UsuarioRepository();

    @POST
    @Path("/cadastro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(UsuarioDTO dto) {
        if (repository.existeEmail(dto.getEmail())) {
            return Response.status(Response.Status.CONFLICT).entity("Email já cadastrado").build();
        }

        Usuario usuario = new Usuario(0, dto.getNome(), dto.getEmail(), dto.getSenha());
        boolean criado = repository.inserir(usuario);

        if (criado) {
            return Response.status(Response.Status.CREATED).entity(usuario).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar usuário").build();
    }


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuario(UsuarioDTO dto) {
        Usuario usuario = repository.buscarPorEmailSenha(dto.getEmail(), dto.getSenha());
        if (usuario != null) {
            return Response.ok(usuario).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("Email ou senha inválidos").build();
    }

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() {
        List<Usuario> usuarios = repository.listarTodos();
        List<UsuarioResponseDTO> dtos = usuarios.stream()
                .map(u -> new UsuarioResponseDTO(u.getId(), u.getNome(), u.getEmail()))
                .toList();
        return Response.ok(dtos).build();
    }

    @PUT
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarUsuario(UsuarioAtualizacaoDTO dto) {
        boolean atualizado = repository.atualizar(dto.getEmail(), dto.getNovoNome(), dto.getNovaSenha());

        if (atualizado) {
            return Response.ok("Dados do usuário atualizados com sucesso").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar usuário").build();
        }
    }

}
