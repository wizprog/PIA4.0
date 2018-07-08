/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Kompanija;
import database.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Marko
 */
@ManagedBean
@SessionScoped
public class ItDash {

    List<Kompanija> list1;
    List<Kompanija> list2;
    Kompanija selectedCompany1;
    Kompanija selectedCompany2;

    public Kompanija getSelectedCompany1() {
        return selectedCompany1;
    }

    public void setSelectedCompany1(Kompanija selectedCompany1) {
        this.selectedCompany1 = selectedCompany1;
    }

    public Kompanija getSelectedCompany2() {
        return selectedCompany2;
    }

    public void setSelectedCompany2(Kompanija selectedCompany2) {
        this.selectedCompany2 = selectedCompany2;
    }

    public List<Kompanija> getList1() {
        return list1;
    }

    public void setList1(List<Kompanija> list1) {
        this.list1 = list1;
    }

    public List<Kompanija> getList2() {
        return list2;
    }

    public void setList2(List<Kompanija> list2) {
        this.list2 = list2;
    }

    public void onLoadTable1() {
        List<Kompanija> ls = new ArrayList<Kompanija>();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "SELECT kompanija.PIB FROM kompanija,donationcontract\n"
                    + "WHERE kompanija.PIB = donationcontract.PIB AND donationcontract.DueDate>now() AND donationcontract.DueDate < DATE_ADD(now(), INTERVAL 18 MONTH)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "SELECT kompanija.PIB FROM kompanija,moneycontract\n"
                    + "WHERE kompanija.PIB = moneycontract.PIB AND moneycontract.DueDate>now() AND moneycontract.DueDate < DATE_ADD(now(), INTERVAL 18 MONTH)\n"
                    + "\n"
                    + "LIMIT 20";
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = query.list();
            for (Object object : data) {
                Map row = (Map) object;
                int companyPib = (int) row.get("PIB");
                Kompanija k = (Kompanija) session.load(Kompanija.class, companyPib);
                System.out.println(k.getName());
                ls.add(k);
            }
            this.list1 = ls;
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

    public void onLoadTable2() {
        List<Kompanija> ls = new ArrayList<Kompanija>();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "SELECT kompanija.PIB FROM kompanija,donationcontract\n"
                    + "WHERE kompanija.PIB = donationcontract.PIB AND donationcontract.DueDate<now() AND donationcontract.DueDate > DATE_SUB(now(), INTERVAL 18 MONTH)\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "SELECT kompanija.PIB FROM kompanija,moneycontract\n"
                    + "WHERE kompanija.PIB = moneycontract.PIB AND moneycontract.DueDate<now() AND moneycontract.DueDate > DATE_SUB(now(), INTERVAL 18 MONTH)\n"
                    + "\n"
                    + "LIMIT 20";
            SQLQuery query = session.createSQLQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List data = query.list();
            for (Object object : data) {
                Map row = (Map) object;
                int companyPib = (int) row.get("PIB");
                Kompanija k = (Kompanija) session.load(Kompanija.class, companyPib);
                System.out.println(k.getName());
                ls.add(k);
            }
            this.list2 = ls;
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

    public void onRowSelect1(SelectEvent event) {
        System.out.print(((Kompanija) event.getObject()).getName());
        try {
            if (event.getObject() != null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
                hs.setAttribute("company", (Kompanija) event.getObject());
                FacesContext.getCurrentInstance().getExternalContext().redirect("dosie.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onRowSelect2(SelectEvent event) {
        System.out.print(((Kompanija) event.getObject()).getName());
        try {
            if (event.getObject() != null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
                hs.setAttribute("company", (Kompanija) event.getObject());
                FacesContext.getCurrentInstance().getExternalContext().redirect("dosie.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
