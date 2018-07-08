/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import database.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import beans.*;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;

/**
 *
 * @author Marko
 */
@ManagedBean
@SessionScoped
public class PartnerSearch {

    private String packageName;
    private String companyName;
    private boolean actual;
    private List<Kompanija> companyList;

    public List<Kompanija> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Kompanija> companyList) {
        this.companyList = companyList;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public void search() {
        List<Kompanija> ls = new ArrayList<Kompanija>();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        String s1 = "";
        String s2 = "";
        if (this.actual) {
            s1 = " AND donationcontract.DueDate > now()";
            s2 = " AND moneycontract.DueDate > now()";
        }
        try {
            tx = session.beginTransaction();
            String sql = "SELECT DISTINCT kompanija.PIB FROM kompanija,donationcontract,moneycontract,package \n"
                    + "WHERE\n"
                    + "((kompanija.PIB = donationcontract.PIB  AND donationcontract.DueDate > now()) OR (kompanija.PIB = moneycontract.PIB AND moneycontract.DueDate > now()))\n"
                    + "AND kompanija.Name LIKE '%%' \n"
                    + "AND ((kompanija.PIB = moneycontract.PIB AND moneycontract.IdP = package.IdP AND package.PackName LIKE '%%')\n"
                    + "     OR (kompanija.PIB = donationcontract.PIB AND donationcontract.IdP = package.IdP AND package.PackName LIKE '%%'))";
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
            this.companyList = ls;
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
