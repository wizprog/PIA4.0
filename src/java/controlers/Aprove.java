/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.User;
import beans.Waiting;
import database.HibernateUtil;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Marko
 */
@ManagedBean
@SessionScoped
public class Aprove {

    private List<Waiting> waitList;

    public List<Waiting> getWaitList() {
        return waitList;
    }

    public void setWaitList(List<Waiting> waitList) {
        this.waitList = waitList;
    }

    public void onLoad() {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        List<Waiting> ls = new ArrayList<Waiting>();

        try {
            tx = session.beginTransaction();
            List waiting = session.createQuery("FROM Waiting").list();
            for (Iterator iterator = waiting.iterator(); iterator.hasNext();) {
                Waiting w = (Waiting) iterator.next();
                ls.add(w);
            }
            this.waitList = ls;
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public void aproveReq(Waiting w){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User u = new User(w.getUsername(),w.getPassword(),w.getName(),w.getPrezime(),w.getInstitution(),w.getEmail(),w.getGender(),w.getLinkedIn(),w.getBirthDate(),"User");
            session.save(u);
            session.delete(w);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public void denyReq(Waiting w){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(w);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
