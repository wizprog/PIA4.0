/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Marko
 */
@Entity
@SessionScoped
public class Waiting {

    @Id
    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Name")
    private String name;

    @Column(name = "Surname")
    private String prezime;

    @Column(name = "Institution")
    private String institution;

    @Column(name = "Email")
    private String Email;

    @Column(name = "Gender")
    private String Gender;

    @Column(name = "LinkedIn")
    private String LinkedIn;

    @Column(name = "BirthDate")
    private Date BirthDate;
    
    public Waiting(){
        
    }

    public Waiting(String username, String password, String name, String prezime, String institution, String Email, String Gender, String LinkedIn, Date BirthDate) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.prezime = prezime;
        this.institution = institution;
        this.Email = Email;
        this.Gender = Gender;
        this.LinkedIn = LinkedIn;
        this.BirthDate = BirthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getLinkedIn() {
        return LinkedIn;
    }

    public void setLinkedIn(String LinkedIn) {
        this.LinkedIn = LinkedIn;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date BirthDate) {
        this.BirthDate = BirthDate;
    }


}
