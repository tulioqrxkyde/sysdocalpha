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

    public static void salvar(Object u) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        sessao.save(u);
        transacao.commit();
        sessao.close();
    }

    public static void deletar(Object u) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        sessao.delete(u);
        transacao.commit();
        sessao.close();
    }

    public static void editar(Object u) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        sessao.update(u);
        transacao.commit();
        sessao.close();
    }

    public static List<Login> pesquisarUsuarios(Login u) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        List<Login> logins = sessao.createCriteria(Login.class).add(Restrictions.ilike("usuario", u.getUsuario()))
                .add(Restrictions.ilike("senha", u.getSenha())).list();
        sessao.close();
        return logins;
    }

    public static List<Login> listarUsuarios() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        List<Login> logins = sessao.createCriteria(Login.class).list();
        sessao.close();
        return logins;
    }
}
