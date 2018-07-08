package beans;

import java.util.Date;

import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.primefaces.model.UploadedFile;


@Entity
@SessionScoped
public class User {
	
	@Id
    @Column(name="Username")
    private String username;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="Name")
	private String name;
	    
	@Column(name="Surname")
	private String prezime;
	    
	@Column(name="Institution")
    private String institution;
	
	@Column(name="Email")
	private String Email;
	
	@Column(name="Gender")
	private String Gender;
	
	@Column(name="LinkedIn")
	private String LinkedIn;
	
	@Column(name="BirthDate")
	private Date BirthDate;	
	
	@Column(name="Type")
	private String type;
	
	public User() {}
	
	

	public User(String username, String password, String name, String prezime, String institution, String email,
			String gender, String linkedIn, Date birthDate, String type) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.prezime = prezime;
		this.institution = institution;
		this.Email = email;
		this.Gender = gender;
		this.LinkedIn = linkedIn;
		this.BirthDate = birthDate;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public void setEmail(String email) {
		Email = email;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getLinkedIn() {
		return LinkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		LinkedIn = linkedIn;
	}

	public Date getBirthDate() {
		return BirthDate;
	}

	public void setBirthDate(Date birthDate) {
		BirthDate = birthDate;
	}
/*
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
*/	
	
	
	
}
