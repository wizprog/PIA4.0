/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;
import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Marko
 */
@Entity
@RequestScoped
public class MoneyContract {

    @Id
    @Column(name = "IdMoneyContract")
    private int idMoneyContract;

    @Column(name = "IdP")
    private int idP;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Status")
    private int status;

    @Column(name = "Bill")
    private boolean bill;

    @Column(name = "Payment")
    private boolean payment;

    @Column(name = "DateOfPayment")
    private Date dateOfPayment;
    
    @Column(name = "PIB")
    private int PIB;
    
    @Column(name = "Comment")
    private String comment;
    
    @Column(name = "DueDate")
    private Date dueDate;

    public MoneyContract(){}
    
    public MoneyContract(int idP, Date date, int status, boolean bill, boolean payment, Date dateOfPayment, int PIB, String comment, Date dueDate) {
        this.idP = idP;
        this.date = date;
        this.status = status;
        this.bill = bill;
        this.payment = payment;
        this.dateOfPayment = dateOfPayment;
        this.PIB = PIB;
        this.comment = comment;
        this.dueDate = dueDate;
    }

    public int getIdMoneyContract() {
        return idMoneyContract;
    }

    public void setIdMoneyContract(int idMoneyContract) {
        this.idMoneyContract = idMoneyContract;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isBill() {
        return bill;
    }

    public void setBill(boolean bill) {
        this.bill = bill;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public int getPIB() {
        return PIB;
    }

    public void setPIB(int PIB) {
        this.PIB = PIB;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    
    
    
}
