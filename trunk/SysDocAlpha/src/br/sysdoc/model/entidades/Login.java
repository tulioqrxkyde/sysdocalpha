
package br.sysdoc.model.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Farley
 */

@Entity
public class Login implements Serializable {
  
    @Id
    @GeneratedValue
    private int codLogin;
   
    @Column(nullable = false)
    private String usuario;
    
    @Column(nullable = false)
    private String senha;
    
    @Column
    private boolean administrador;

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    /**
     * @return the codLogin
     */
    public int getCodLogin() {
        return codLogin;
    }

    /**
     * @param codLogin the codLogin to set
     */
    public void setCodLogin(int codLogin) {
        this.codLogin = codLogin;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
