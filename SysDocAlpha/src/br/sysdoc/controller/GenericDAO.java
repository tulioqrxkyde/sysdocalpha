/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sysdoc.controller;

import br.sysdoc.factories.ManagerConnection;
import java.util.List;

/**
 *
 * @author tulio.xcrtf
 * @param <T>
 */
public class GenericDAO<T> extends ManagerConnection {

    /**
     * Descrição: Salva ou atualiza um objeto de tipo genérico.
     * 
     * @param object 
     */
    public void saveorUpdate(T object) {
        getSession().saveOrUpdate(object);
        getTransaction().commit();
    }

    /**
     * Descrição: Deleta um objeto de tipo genérico.
     * 
     * @param object a ser deletado
     */
    public void delete(T object) {
        getSession().delete(object);
        getTransaction().commit();
    }

    /**
     * Descrição: Retorna um objeto de tipo Genérico<(T)>.
     * 
     * @param object tipo de classe
     * @param value valor
     * @return  
     */
    public T get(T object, Integer value) {
        return ((T) getSession().get(object.getClass(), value));
    }

    /**
     * Descrição: Procura por objetos do tipo Genérico<(T)> e os retorna em forma de uma
     * lista genérica.
     * 
     * @param object tipo de classe
     * @return
     */
    public List<T> list(T object) {
        return (List<T>) getSession().createCriteria(object.getClass()).list();
    }
}