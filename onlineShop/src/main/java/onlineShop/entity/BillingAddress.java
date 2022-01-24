package onlineShop.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "billingaddress")
public class BillingAddress implements Serializable {
    private static final long serialVersionUID = 1028098616457762743L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    @OneToOne(mappedBy = "billingAddress")
    private Customer customer;

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
