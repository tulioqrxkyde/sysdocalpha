
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

    public static void salvar(Object u) {
        sessao.save(u);
        transacao.commit();
    }
    
    public static void deletar(Object u) {
        sessao.delete(u);
        transacao.commit();
    }

    public static List<Login> pesquisarUsuarios(Login u) {
        return (List<Login>) sessao.createCriteria(Login.class).add(Restrictions.ilike("usuario", u.getUsuario()))
                .add(Restrictions.ilike("senha", u.getSenha())).list();
    }

    public static List<Login> listarUsuarios() {
        return (List<Login>) sessao.createCriteria(Login.class).list();
    }
}
