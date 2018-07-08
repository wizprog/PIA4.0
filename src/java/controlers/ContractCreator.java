/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Kompanija;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marko
 */

@ManagedBean
@SessionScoped
public class ContractCreator {
    
    public String selectedCompany;
    public List<Kompanija> listOfCompanies;
    
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
    
    
    
    
}
