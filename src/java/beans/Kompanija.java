package beans;

import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@RequestScoped
public class Kompanija {

    @Column(name="Name")
	public String name;
	
    @Column(name="Address")
	public String address;
	
    @Column(name="BankAccount")
	public String bankAccount;
	
	@Id
    @Column(name="PIB")
	public int PIB;
	
	@Column(name="ContactPerson")
	public String contactPerson;
	
    @Column(name="ContactPhone")
	public String contactPhone;
	
    @Column(name="ContactEmail")
	public String contactEmail;
	
    @Column(name="CompanyLogo")
	public String companyLogo;
    
    public Kompanija() {}
    
	public Kompanija(String name, String address, String bankAccount, int pIB, String contactPerson,
			String contactPhone, String contactEmail, String companyLogo) {
		super();
		this.name = name;
		this.address = address;
		this.bankAccount = bankAccount;
		this.PIB = pIB;
		this.contactPerson = contactPerson;
		this.contactPhone = contactPhone;
		this.contactEmail = contactEmail;
		this.companyLogo = companyLogo;
	}

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

	public void setPIB(int pIB) {
		PIB = pIB;
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
    
    
	
}
