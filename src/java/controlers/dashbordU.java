/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import java.util.*;
import beans.Kompanija;
import database.HibernateUtil;
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
public class dashbordU {

    private List<Kompanija> listNew;
    private List<Kompanija> listExp;

    public List<Kompanija> getListNew() {
        return listNew;
    }

    public void setListNew(List<Kompanija> listNew) {
        this.listNew = listNew;
    }

    public List<Kompanija> getListExp() {
        return listExp;
    }

    public void setListExp(List<Kompanija> listExp) {
        this.listExp = listExp;
    }

    public void refresh() {
        List<Kompanija> ls = new ArrayList<Kompanija>();

        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String sql = "SELECT kompanija.PIB,donationcontract.Date FROM kompanija,donationcontract WHERE kompanija.PIB = donationcontract.PIB and donationcontract.Date < now()\n"
                    + "UNION\n"
                    + "SELECT kompanija.PIB,moneycontract.Date FROM kompanija,moneycontract WHERE kompanija.PIB = moneycontract.PIB and moneycontract.Date < now()\n"
                    + "ORDER BY Date DESC\n"
                    + "LIMIT 5";

            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = query.list();
            System.out.println("Lista1" + data.toString());
            for (Object object : data) {
                Map row = (Map) object;
                Integer pib = (Integer) row.get("PIB");
                Kompanija k = (Kompanija) session.load(Kompanija.class, pib);
                ls.add(k);
            }
            System.out.println("Proradi: " + ls.toString());
            this.listNew = ls;
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

    public void refresh2() {
        List<Kompanija> ls = new ArrayList<Kompanija>();

        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String sql = "SELECT kompanija.PIB,donationcontract.DueDate FROM kompanija,donationcontract WHERE kompanija.PIB = donationcontract.PIB and donationcontract.DueDate > now()\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "SELECT kompanija.PIB,moneycontract.DueDate FROM kompanija,moneycontract WHERE kompanija.PIB = moneycontract.PIB and moneycontract.DueDate > now()\n"
                    + "\n"
                    + "ORDER BY DueDate ASC\n"
                    + "\n"
                    + "LIMIT 5";

            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = query.list();
            System.out.println("Lista1" + data.toString());
            for (Object object : data) {
                Map row = (Map) object;
                Integer pib = (Integer) row.get("PIB");
                Kompanija k = (Kompanija) session.load(Kompanija.class, pib);
                ls.add(k);
            }
            System.out.println("Proradi: " + ls.toString());
            this.listExp = ls;
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
