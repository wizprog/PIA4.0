package beans;

import java.util.Date;

import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@RequestScoped
public class Advertisement {

    @Id
    @Column(name = "IdA")
    private int idA;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Practice")
    private boolean practice;

    @Column(name = "Employment")
    private boolean employment;

    @Column(name = "Username")
    private String username;

    @Column(name = "EnterDate")
    private Date enterdate;

    @Column(name = "Company")
    private String company;

    public Advertisement() {
    }

    public Advertisement(String name, String description, boolean practice, boolean employment, String username,
            Date enterdate, String company) {
        super();
        this.name = name;
        this.description = description;
        this.practice = practice;
        this.employment = employment;
        this.username = username;
        this.enterdate = enterdate;
        this.company = company;
    }

    public Date getEnterdate() {
        return enterdate;
    }

    public void setEnterdate(Date enterdate) {
        this.enterdate = enterdate;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPractice() {
        return practice;
    }

    public void setPractice(boolean practice) {
        this.practice = practice;
    }

    public boolean isEmployment() {
        return employment;
    }

    public void setEmployment(boolean employment) {
        this.employment = employment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    

}
