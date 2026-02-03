package uce.edu.ec.api.auth.interfaces;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.ec.api.auth.application.UsuarioService;
import uce.edu.ec.api.auth.application.representation.UsuarioRepresentation;

@Path("/auth")
public class AuthResource {
    @Inject
    private UsuarioService usuarioService;

    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response token(
            @QueryParam("user") String user,
            @QueryParam("password") String password) {

        UsuarioRepresentation userRep = usuarioService.validarCredenciales(user, password);

        if (userRep == null) {
            // Credenciales inválidas -> 401 Unauthorized con mensaje JSON
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", "Credenciales inválidas"))
                    .build();
        }

        String role = userRep.getRol();

        String issuer = "matricula-auth";
        long ttl = 3600;

        Instant now = Instant.now();
        Instant exp = now.plusSeconds(ttl);

        String jwt = Jwt.issuer(issuer)
                .subject(user)
                .groups(Set.of(role)) // roles: user / admin
                .issuedAt(now)
                .expiresAt(exp)
                .sign();

        TokenResponse resp = new TokenResponse(jwt, exp.getEpochSecond(), role);
        return Response.ok(resp).build();
    }

    public static class TokenResponse {
        public String accessToken;
        public long expiresAt;
        public String role;

        public TokenResponse() {
        }

        public TokenResponse(String accessToken, long expiresAt, String role) {
            this.accessToken = accessToken;
            this.expiresAt = expiresAt;
            this.role = role;
        }
    }
}
