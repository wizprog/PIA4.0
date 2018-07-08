package beans;

import java.util.Date;

import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@RequestScoped
public class Lectures {

    @Id
    @Column(name = "IdL")
    private int idL;

    @Column(name = "Name")
    private String name;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Time")
    private Date time;

    @Column(name = "Auditorium")
    private String auditorium;

    @Column(name = "Lecturer")
    private String lecturer;

    @Column(name = "Biography")
    private String biography;

    @Column(name = "Username")
    private String username;
    
    @Column(name = "Company")
    private String company;
    
    public Lectures(){}

    public Lectures(String name, Date date, Date time, String auditorium, String lecturer, String biography, String username, String company) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.auditorium = auditorium;
        this.lecturer = lecturer;
        this.biography = biography;
        this.username = username;
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getIdL() {
        return idL;
    }

    public void setIdL(int idL) {
        this.idL = idL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
