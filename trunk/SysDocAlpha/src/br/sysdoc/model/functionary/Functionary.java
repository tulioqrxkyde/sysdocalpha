/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sysdoc.model.functionary;

import br.sysdoc.model.path.directories.Folder;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author tulio.xcrtf
 */
@Entity
public class Functionary implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 50)
    private String name;

    private int cpf;

    @Column(length = 70)
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    private Folder folder;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cpf
     */
    public int getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the pasta
     */
    public Folder getFolder() {
        return folder;
    }

    /**
     * @param folder the folder to set
     */
    public void setFolder(Folder folder) {
        this.folder = folder;
    }

}
