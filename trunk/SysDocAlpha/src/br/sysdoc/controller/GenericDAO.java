
package br.sysdoc.controller;

import br.sysdoc.factories.ManagerConnection;
import java.io.Serializable;
import java.util.List;


public class GenericDAO<T> extends ManagerConnection {

    /**
     * Descrição: Salva um objeto de tipo genérico.
     * 
     * @param object 
     */
    public void save(T object) {
        getSession().save(object);
        getTransaction().commit();
    }
    
    /**
     * Descrição: Atualiza um objeto de tipo genérico.
     * 
     * @param object 
     */
    public void update(T object) {
        getSession().update(object);
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
    public T get(T object, Object value) {
        return ((T) getSession().get(object.getClass(), (Serializable) value));
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