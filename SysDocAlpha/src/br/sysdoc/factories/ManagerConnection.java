/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sysdoc.factories;

import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author tulio.xcrtf
 */
public abstract class ManagerConnection {

    private Session session;
    private Transaction transaction;

    private void openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    private void beginTransaction() {
        transaction = session.beginTransaction();
    }

    /**
     * @return the session
     */
    public Session getSession() {
        if (session == null) {
            openSession();
        }
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * @return the transaction
     */
    public Transaction getTransaction() {
        if (transaction == null) {
            if (session == null) {
                openSession();
            }
            beginTransaction();
        }
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    public void closeConnection() {
        session.close();
    }

}