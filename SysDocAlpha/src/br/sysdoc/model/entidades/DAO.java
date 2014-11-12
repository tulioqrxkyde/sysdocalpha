package br.sysdoc.model.entidades;

import br.sysdoc.util.HibernateUtil;
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
        sessao.saveOrUpdate(u);
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

    public static Login pesquisarUsuarios(Login u) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        Login logins = (Login) sessao.createCriteria(Login.class).add(Restrictions.ilike("usuario", u.getUsuario()))
                .add(Restrictions.ilike("senha", u.getSenha())).uniqueResult();
        sessao.close();
        return logins;
    }

    public static List<Funcionario> listarFuncionarios() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        List<Funcionario> functs = sessao.createCriteria(Funcionario.class).list();
        sessao.close();
        return functs;
    }

    public static List<Login> listarUsuarios() {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        Transaction transacao = sessao.beginTransaction();
        List<Login> logins = sessao.createCriteria(Login.class).list();
        sessao.close();
        return logins;
    }
}
