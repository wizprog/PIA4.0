package controlers;

import beans.InContact;
import beans.Kompanija;
import java.security.MessageDigest;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import beans.User;
import database.HibernateUtil;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class CreateCompany {

    private String name;
    private String address;
    private String bankAccount;
    private int PIB;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private String companyLogo;
    private String textCompany;

    public String getTextCompany() {
        return textCompany;
    }

    public void setTextCompany(String textCompany) {
        this.textCompany = textCompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public int getPIB() {
        return PIB;
    }

    public void setPIB(int pIB) {
        PIB = pIB;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public void create() {
        this.textCompany = "";
        SessionFactory sessionF = HibernateUtil.getSessionFactory();
        Session session = sessionF.openSession();
        Transaction tx = null;

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        User u = (User) hs.getAttribute("user");

        try {
            tx = session.beginTransaction();
            Kompanija k = new Kompanija(this.name, this.address, this.bankAccount, this.PIB, this.contactPerson, this.contactPhone, this.contactEmail, this.companyLogo);
            session.save(k);
            InContact k2 = new InContact(u.getUsername(), this.PIB);
            session.save(k2);
            tx.commit();
            this.textCompany = "Company created";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            this.textCompany = "Error occurred";
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
