/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.*;
import database.HibernateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
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
public class CreateAddLect {

    private List<String> addUserCompanies;
    private String addCompany;
    private String addName;
    private Date addEnterDate;
    private String addDescription;
    private String[] addPracEmp;
    
    private String lecName;
    private Date lecDate;
    private Date lecTime;
    private String lecAuditorium;
    private String lecLecturer;
    private String lecBiography;
    
    private String lecText;
    private String addText;

    public String getLecText() {
        return lecText;
    }

    public void setLecText(String lecText) {
        this.lecText = lecText;
    }

    public String getAddText() {
        return addText;
    }

    public void setAddText(String addText) {
        this.addText = addText;
    }  

    public String getLecName() {
        return lecName;
    }

    public void setLecName(String lecName) {
        this.lecName = lecName;
    }

    public Date getLecDate() {
        return lecDate;
    }

    public void setLecDate(Date lecDate) {
        this.lecDate = lecDate;
    }

    public Date getLecTime() {
        return lecTime;
    }

    public void setLecTime(Date lecTime) {
        this.lecTime = lecTime;
    }

    public String getLecAuditorium() {
        return lecAuditorium;
    }

    public void setLecAuditorium(String lecAuditorium) {
        this.lecAuditorium = lecAuditorium;
    }

    public String getLecLecturer() {
        return lecLecturer;
    }

    public void setLecLecturer(String lecLecturer) {
        this.lecLecturer = lecLecturer;
    }

    public String getLecBiography() {
        return lecBiography;
    }

    public void setLecBiography(String lecBiography) {
        this.lecBiography = lecBiography;
    }

    public List<String> getAddUserCompanies() {
        return addUserCompanies;
    }

    public void setAddUserCompanies(List<String> addUserCompanies) {
        this.addUserCompanies = addUserCompanies;
    }

    public String getAddCompany() {
        return addCompany;
    }

    public void setAddCompany(String addCompany) {
        this.addCompany = addCompany;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public Date getAddEnterDate() {
        return addEnterDate;
    }

    public void setAddEnterDate(Date addEnterDate) {
        this.addEnterDate = addEnterDate;
    }

    public String getAddDescription() {
        return addDescription;
    }

    public void setAddDescription(String addDescription) {
        this.addDescription = addDescription;
    }

    public String[] getAddPracEmp() {
        return addPracEmp;
    }

    public void setAddPracEmp(String[] addPracEmp) {
        this.addPracEmp = addPracEmp;
    }

    public void createAdd() {
        this.addText = "";
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        User u = (User) hs.getAttribute("user");

        try {
            tx = session.beginTransaction();
            boolean p = false;
            boolean e = false;
            if (this.addPracEmp.length == 2) {
                p = e = true;
            }
            if (this.addPracEmp.length == 0) {
                e = true;
            }
            if (this.addPracEmp.length == 1) {
                if (this.addPracEmp[0] == "p") {
                    p = true;
                } else {
                    e = true;
                }
            }
            Advertisement a = new Advertisement(this.addName, this.addDescription, p, e, u.getUsername(), new Date(),this.addCompany);
            session.save(a);
            tx.commit();
            this.addText = "Advertisement created";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            this.addText = "Error occurred";
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void onLoad() { 
        List<String> ls = new ArrayList<String>();
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        User u = (User) hs.getAttribute("user");
        try {
            tx = session.beginTransaction();
            List contacts = session.createQuery("FROM InContact").list();
            for (Iterator iterator = contacts.iterator(); iterator.hasNext();) {
                InContact con = (InContact) iterator.next();
                if (con.getUsername().equals(u.getUsername())) {
                    Criteria cr = session.createCriteria(Kompanija.class);
                    cr.add(Restrictions.eq("PIB", con.getPIB()));
                    List comp = cr.list();
                    Kompanija k = (Kompanija) comp.get(0);
                    ls.add(k.getName());
                }
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        this.addUserCompanies = ls;
    }
    
     public void createLec() {
        this.lecText = "";
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        User u = (User) hs.getAttribute("user");
        
        try {
            tx = session.beginTransaction();
            Lectures l = new Lectures(this.lecName,this.lecDate,this.lecTime,this.lecAuditorium,this.lecLecturer,this.lecBiography,u.getUsername(),this.addCompany);
            session.save(l);
            tx.commit();
            this.lecText = "Lecture created";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            this.lecText = "Error occurred";
            e.printStackTrace();
        } finally {
            session.close();
        }       
     }

}
