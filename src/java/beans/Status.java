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
 * @author mbabi
 */

@Entity
@RequestScoped
public class Status {
    
    @Id
    @Column(name="IdStatus")
    private int idStatus;
    
    @Column(name="Name")
    private String name;
    
    public Status(){
        
    }

    public Status(String name) {
        this.name = name;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
}
