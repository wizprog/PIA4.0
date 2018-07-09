package beans;

import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@RequestScoped
public class Package {

    @Id
    @Column(name = "IdP")
    private int idp;

    @Column(name = "PackName")
    private String name;

    @Column(name = "Price")
    private int price;

    @Column(name = "Length")
    private int length;

    @Column(name = "MaxComp")
    private int maxcomp;

    public Package() {
    }

    public Package( String name, int price, int length, int maxcomp) {
        super();
        this.name = name;
        this.price = price;
        this.length = length;
        this.maxcomp = maxcomp;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMaxcomp() {
        return maxcomp;
    }

    public void setMaxcomp(int maxcomp) {
        this.maxcomp = maxcomp;
    }

}
