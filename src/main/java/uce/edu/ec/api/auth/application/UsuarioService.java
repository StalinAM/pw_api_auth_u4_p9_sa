package uce.edu.ec.api.auth.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import uce.edu.ec.api.auth.domain.Usuario;
import uce.edu.ec.api.auth.infraestructure.UsuarioRepository;
import uce.edu.ec.api.auth.representation.UsuarioRepresentation;

@ApplicationScoped
public class UsuarioService {
    @Inject
    UsuarioRepository usuarioRepository;

    public UsuarioRepresentation validarCredenciales(String username, String password) {
        Usuario usuario = this.usuarioRepository.findByUsername(username);

        if (usuario != null && usuario.getPassword().equals(password)) {
            return mapToRepresentation(usuario);
        }
        return null;
    }

    private UsuarioRepresentation mapToRepresentation(Usuario usuario) {
        UsuarioRepresentation ur = new UsuarioRepresentation();
        ur.setId(usuario.getId());
        ur.setUsername(usuario.getUsername());
        ur.setPassword(usuario.getPassword());
        ur.setRol(usuario.getRol());
        return ur;
    }

    public List<UsuarioRepresentation> listarUsuarios() {
        List<Usuario> usuarios = this.usuarioRepository.listAll();
        return usuarios.stream()
                .map(this::mapToRepresentation)
                .toList();
    }

    public List<UsuarioRepresentation> listarTodos() {
        return usuarioRepository.listAll().stream()
                .map(this::mapToRepresentation)
                .toList();
    }
}
