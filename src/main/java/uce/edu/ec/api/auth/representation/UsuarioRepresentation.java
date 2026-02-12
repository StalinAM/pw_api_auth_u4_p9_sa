package uce.edu.ec.api.auth.representation;

public class UsuarioRepresentation {
    private Long id;
    private String username;
    private String rol;
    private String password;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
