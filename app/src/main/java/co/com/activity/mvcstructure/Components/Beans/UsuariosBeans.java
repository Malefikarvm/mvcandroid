package co.com.activity.mvcstructure.Components.Beans;

/**
 * Created by Analista on 15/01/2016.
 */
public class UsuariosBeans {
    private String usuarios;
    private String contrasena;

    public UsuariosBeans(String usu, String pass){
        this.usuarios = usu;
        this.contrasena = pass;
    }

    public String getUsuarios() { return usuarios; }

    public String getContrasena() { return contrasena; }

    public void setUsuarios(String usuarios) { this.usuarios = usuarios; }

    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
