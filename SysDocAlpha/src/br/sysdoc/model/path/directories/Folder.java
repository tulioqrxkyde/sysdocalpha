
package br.sysdoc.model.path.directories;

import br.sysdoc.model.entidades.Funcionario;
import br.sysdoc.model.path.path.Paths;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;


@Entity
public class Folder extends Paths implements Serializable {
    
    @OneToOne(mappedBy = "folder",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Funcionario functionary;

    public Folder() {
        
    }
    
    public Folder(String name, Funcionario functionary) {
        setName(name);
        this.functionary = functionary;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.getName());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Folder other = (Folder) obj;
        return Objects.equals(this.getName(), other.getName());
    }

    /**
     * @return the functionary
     */
    public Funcionario getFunctionary() {
        return functionary;
    }

    /**
     * @param functionary the functionary to set
     */
    public void setFunctionary(Funcionario functionary) {
        this.functionary = functionary;
    }

}