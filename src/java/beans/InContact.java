/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

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
public class InContact {


    @Column(name = "username")
    private String username;

    @Column(name = "PIB")
    private int PIB;
    
    @Id
    @Column(name = "IdC")
    private int IdC;

    public InContact() {
    }

    public InContact(String username, int PIB) {
        this.username = username;
        this.PIB = PIB;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPIB() {
        return PIB;
    }

    public void setPIB(int PIB) {
        this.PIB = PIB;
    }

    public int getIdC() {
        return IdC;
    }

    public void setIdC(int IdC) {
        this.IdC = IdC;
    }

    
    
    

}
