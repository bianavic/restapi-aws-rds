package org.edu.fabs.rdsapirest.hateoas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Entity
@Table(name = "CUSTOMER")
public class CustomerHateoas extends RepresentationModel<OrderHateoasModel> {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String occupation;
    private String address;

    public CustomerHateoas(String name, String occupation, String address) {
        this.id = id;
        this.name = name;
        this.occupation = occupation;
        this.address = address;
    }

    public CustomerHateoas() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String role) {
        this.occupation = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerHateoas employee = (CustomerHateoas) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(occupation, employee.occupation) && Objects.equals(address, employee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, occupation, address);
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

}
