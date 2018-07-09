/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.InContact;
import beans.Kompanija;
import beans.Status;
import beans.User;
import database.HibernateUtil;
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
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marko
 */

@ManagedBean
@SessionScoped
public class PutInCharge {
    
    private List<String> listOfUsers;
    private List<String> listOfCompanies;
    
    private String chosenUser;
    private String chosenCompanie;
    
    private String errormsg;

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public List<String> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(List<String> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    public List<String> getListOfCompanies() {
        return listOfCompanies;
    }

    public void setListOfCompanies(List<String> listOfCompanies) {
        this.listOfCompanies = listOfCompanies;
    }

    public String getChosenUser() {
        return chosenUser;
    }

    public void setChosenUser(String chosenUser) {
        this.chosenUser = chosenUser;
    }

    public String getChosenCompanie() {
        return chosenCompanie;
    }

    public void setChosenCompanie(String chosenCompanie) {
        this.chosenCompanie = chosenCompanie;
    }
    
    public void onLoad(){
        
        List<String> ls1 = new ArrayList<String>();
        List<String> ls2 = new ArrayList<String>();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            
            List userList = session.createQuery("FROM User").list();
            List companyList = session.createQuery("FROM Kompanija").list();
            
            for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
                User u = (User) iterator.next();
                ls1.add(u.getUsername());
            }
            
            for (Iterator iterator = companyList.iterator(); iterator.hasNext();) {
                Kompanija k = (Kompanija) iterator.next();
                ls2.add(k.name);
            }
            
            this.listOfUsers = ls1;
            this.listOfCompanies = ls2;
            
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
    
    public void putInCharge(){
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        int pib = 0;
        try {
            tx = session.beginTransaction();
            
            Criteria cr = session.createCriteria(Kompanija.class);
            cr.add(Restrictions.eq("name", this.chosenCompanie));
            List users = cr.list();
            for (Iterator iterator = users.iterator(); iterator.hasNext();) {
                Kompanija k = (Kompanija) iterator.next();
                pib = k.getPIB();
            }
            
            Criteria cr1 = session.createCriteria(InContact.class);
            cr1.add(Restrictions.eq("username", this.chosenUser));
            cr1.add(Restrictions.eq("PIB", pib));
            List cont = cr1.list();
            System.out.println(this.chosenUser + " " + pib);
            if (cont.isEmpty()){
                InContact in = new InContact(this.chosenUser,pib);
                session.save(in);
                this.errormsg = "Done";
            }else{
                this.errormsg = "Already exists";
            }          
            tx.commit();
        } catch (Exception e) {
            this.errormsg = "Error occurred";
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
}
