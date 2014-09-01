/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import Util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Farley
 */
public class DAO {

    static final Session sessao = HibernateUtil.getSessionFactory().openSession();
    static final Transaction transacao = sessao.beginTransaction();

    ;

    public static void salvar(Object l) {
        sessao.save(l);
        transacao.commit();
    }
    
    public static void deletar(Object l) {
        sessao.delete(l);
        transacao.commit();
    }

    public static List<Login> pesquisarUsuarios(Login l) {
        return (List<Login>) sessao.createCriteria(Login.class).add(Restrictions.ilike("usuario", l.getUsuario()))
                .add(Restrictions.ilike("senha", l.getSenha())).list();
    }

    public static List<Login> listarUsuarios() {
        return (List<Login>) sessao.createCriteria(Login.class).list();
    }
}
