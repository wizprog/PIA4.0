/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Kompanija;
import beans.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Marko
 */
@ManagedBean
@SessionScoped
public class Dosie {

    private String name;
    private String address;
    private String bankAccount;
    private int PIB;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private String companyLogo;

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

    public void setPIB(int PIB) {
        this.PIB = PIB;
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
    
    public void onLoad(){
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession hs = (HttpSession) fc.getExternalContext().getSession(false);
        Kompanija k = (Kompanija) hs.getAttribute("company");
        System.out.println("DOSIJE:" + k.getName());
        
        this.name = k.getName();
        this.address = k.getAddress();
        this.bankAccount = k.getBankAccount();
        this.PIB = k.getPIB();
        this.contactPerson = k.getContactPerson();
        this.contactPhone = k.getContactPhone();
        this.contactEmail = k.getContactEmail();
        this.companyLogo = k.getCompanyLogo();

    }
}
