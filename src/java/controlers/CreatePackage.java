/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import database.HibernateUtil;
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
public class CreatePackage {

    private String packageName;
    private int prackagePrice;
    private int packageLength;
    private int packageMaxCompanies;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getPrackagePrice() {
        return prackagePrice;
    }

    public void setPrackagePrice(int prackagePrice) {
        this.prackagePrice = prackagePrice;
    }

    public int getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(int packageLength) {
        this.packageLength = packageLength;
    }

    public int getPackageMaxCompanies() {
        return packageMaxCompanies;
    }

    public void setPackageMaxCompanies(int packageMaxCompanies) {
        this.packageMaxCompanies = packageMaxCompanies;
    }

    public void create() {
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            beans.Package p = new beans.Package(this.packageName, this.prackagePrice, this.packageLength, this.packageMaxCompanies);
            session.save(p);
            tx.commit();
            this.msg = "Package created";
        } catch (Exception e) {
            this.msg = "Error occurred";
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        clear();
    }

    private void clear() {
        this.packageName = null;
        this.prackagePrice = 0;
        this.packageLength = 0;
        this.packageMaxCompanies = 0;
    }
}
