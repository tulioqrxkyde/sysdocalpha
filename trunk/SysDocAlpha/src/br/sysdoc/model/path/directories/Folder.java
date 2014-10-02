/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sysdoc.model.path.directories;

import br.sysdoc.model.path.path.Paths;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;

/**
 *
 * @author tulio.xcrtf
 */
@Entity
public class Folder extends Paths implements Serializable {

    public Folder() {
        
    }
    
    public Folder(String name) {
        setName(name);
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

}