/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Package;
import beans.Kompanija;
import beans.Status;
import database.HibernateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import beans.MoneyContract;
import beans.DonationContract;
import java.util.Calendar;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Marko
 */
@ManagedBean
@SessionScoped
public class ContractCreator {

    public String selectedCompany;
    public String selectedStatus;
    public String selectedPackage;
    public List<String> listOfCompanies;
    public List<String> listPackage;
    public List<String> listStatus;

    public int moneyIdP;
    public Date moneyDate;
    public int moneyIdStatus;
    public boolean moneyBill;
    public boolean moneyPayment;
    public Date dateOfPayment;
    public String moneyComment;

    public int donationIdP;
    public int donationIdStatus;
    public String donationDesription;
    public Date donationDate;
    public Date donationShipmentDate;
    public String donationComment;

    public String getSelectedPackage() {
        return selectedPackage;
    }

    public void setSelectedPackage(String selectedPackage) {
        this.selectedPackage = selectedPackage;
    }

    public List<String> getListPackage() {
        return listPackage;
    }

    public void setListPackage(List<String> listPackage) {
        this.listPackage = listPackage;
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public List<String> getListStatus() {
        return listStatus;
    }

    public void setListStatus(List<String> listStatus) {
        this.listStatus = listStatus;
    }

    public String getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(String selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    public List<String> getListOfCompanies() {
        return listOfCompanies;
    }

    public void setListOfCompanies(List<String> listOfCompanies) {
        this.listOfCompanies = listOfCompanies;
    }

    public int getMoneyIdP() {
        return moneyIdP;
    }

    public void setMoneyIdP(int moneyIdP) {
        this.moneyIdP = moneyIdP;
    }

    public Date getMoneyDate() {
        return moneyDate;
    }

    public void setMoneyDate(Date moneyDate) {
        this.moneyDate = moneyDate;
    }

    public int getMoneyIdStatus() {
        return moneyIdStatus;
    }

    public void setMoneyIdStatus(int moneyIdStatus) {
        this.moneyIdStatus = moneyIdStatus;
    }

    public boolean isMoneyBill() {
        return moneyBill;
    }

    public void setMoneyBill(boolean moneyBill) {
        this.moneyBill = moneyBill;
    }

    public boolean isMoneyPayment() {
        return moneyPayment;
    }

    public void setMoneyPayment(boolean moneyPayment) {
        this.moneyPayment = moneyPayment;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getMoneyComment() {
        return moneyComment;
    }

    public void setMoneyComment(String moneyComment) {
        this.moneyComment = moneyComment;
    }

    public int getDonationIdP() {
        return donationIdP;
    }

    public void setDonationIdP(int donationIdP) {
        this.donationIdP = donationIdP;
    }

    public int getDonationIdStatus() {
        return donationIdStatus;
    }

    public void setDonationIdStatus(int donationIdStatus) {
        this.donationIdStatus = donationIdStatus;
    }

    public String getDonationDesription() {
        return donationDesription;
    }

    public void setDonationDesription(String donationDesription) {
        this.donationDesription = donationDesription;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public Date getDonationShipmentDate() {
        return donationShipmentDate;
    }

    public void setDonationShipmentDate(Date donationShipmentDate) {
        this.donationShipmentDate = donationShipmentDate;
    }

    public String getDonationComment() {
        return donationComment;
    }

    public void setDonationComment(String donationComment) {
        this.donationComment = donationComment;
    }

    public void onLoad() {
        List<String> ls = new ArrayList<String>();
        List<String> ls1 = new ArrayList<String>();
        List<String> ls2 = new ArrayList<String>();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List statusList = session.createQuery("FROM Status").list();
            List packageList = session.createQuery("FROM Package").list();
            List companyList = session.createQuery("FROM Kompanija").list();
            for (Iterator iterator = statusList.iterator(); iterator.hasNext();) {
                Status s = (Status) iterator.next();
                ls.add(s.getName());
            }
            for (Iterator iterator = packageList.iterator(); iterator.hasNext();) {
                beans.Package s = (beans.Package) iterator.next();
                ls1.add(s.getName());
            }
            for (Iterator iterator = companyList.iterator(); iterator.hasNext();) {
                Kompanija s = (Kompanija) iterator.next();
                ls2.add(s.getName());
            }
            this.listStatus = ls;
            this.listPackage = ls1;
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

    public void createMoneyContract() {
        int years = 0;
        int status = 0;
        int pib = 0;

        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(beans.Package.class);
            cr.add(Restrictions.eq("PackName", this.selectedPackage));
            List pack = cr.list();
            for (Iterator iterator = pack.iterator(); iterator.hasNext();) {
                beans.Package p = (beans.Package) iterator.next();
                this.moneyIdP = p.getIdp();
                years = p.getLength();
            }

            Criteria x1 = session.createCriteria(Kompanija.class);
            x1.add(Restrictions.eq("Name", this.selectedCompany));
            List comp = x1.list();
            for (Iterator iterator = comp.iterator(); iterator.hasNext();) {
                Kompanija p = (Kompanija) iterator.next();
                pib = p.getPIB();
            }

            Criteria x2 = session.createCriteria(Status.class);
            x2.add(Restrictions.eq("Name", this.selectedStatus));
            List stat = x2.list();
            for (Iterator iterator = stat.iterator(); iterator.hasNext();) {
                Status p = (Status) iterator.next();
                status = p.getIdStatus();
            }
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, years);
            
            MoneyContract m = new MoneyContract(this.moneyIdP,this.moneyDate,status,this.moneyBill,this.moneyPayment,this.dateOfPayment,pib,this.moneyComment,c.getTime());
            session.save(m);
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

    public void createDonationContract() {
        int pib = 0;
        int status = 0;
        int years = 0;
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Criteria cr = session.createCriteria(beans.Package.class);
            cr.add(Restrictions.eq("PackName", this.selectedPackage));
            List pack = cr.list();
            for (Iterator iterator = pack.iterator(); iterator.hasNext();) {
                beans.Package p = (beans.Package) iterator.next();
                this.donationIdP = p.getIdp();
                years = p.getLength();
            }

            Criteria x1 = session.createCriteria(Kompanija.class);
            x1.add(Restrictions.eq("Name", this.selectedCompany));
            List comp = x1.list();
            for (Iterator iterator = comp.iterator(); iterator.hasNext();) {
                Kompanija p = (Kompanija) iterator.next();
                pib = p.getPIB();
            }

            Criteria x2 = session.createCriteria(Status.class);
            x2.add(Restrictions.eq("Name", this.selectedStatus));
            List stat = x2.list();
            for (Iterator iterator = stat.iterator(); iterator.hasNext();) {
                Status p = (Status) iterator.next();
                status = p.getIdStatus();
            }
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, years);
            DonationContract d = new DonationContract(this.donationIdP,status,this.donationDesription,this.donationDate,this.donationShipmentDate,this.donationComment,pib,c.getTime());
            session.save(d);
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
