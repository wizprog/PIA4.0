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
public class DonationContract {

    @Id
    @Column(name = "IdDonationContract")
    private int idDonationContract;

    @Column(name = "IdP")
    private int idP;
    
    @Column(name = "IdStatus")
    private int idStatus;
    
    @Column(name = "Description")
    private String description;
    
    @Column(name = "Date")
    private Date date;
    
    @Column(name = "ShipmentDate")
    private Date shipmentDate;
    
    @Column(name = "Comment")
    private String comment;
    
    @Column(name = "PIB")
    private int PIB;
    
    @Column(name = "DueDate")
    private Date dueDate;
    
    public DonationContract(){}

    public DonationContract(int idP, int idStatus, String description, Date date, Date shipmentDate, String comment, int PIB, Date dueDate) {
        this.idP = idP;
        this.idStatus = idStatus;
        this.description = description;
        this.date = date;
        this.shipmentDate = shipmentDate;
        this.comment = comment;
        this.PIB = PIB;
        this.dueDate = dueDate;
    }

    public int getIdDonationContract() {
        return idDonationContract;
    }

    public void setIdDonationContract(int idDonationContract) {
        this.idDonationContract = idDonationContract;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPIB() {
        return PIB;
    }

    public void setPIB(int PIB) {
        this.PIB = PIB;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }  

}
