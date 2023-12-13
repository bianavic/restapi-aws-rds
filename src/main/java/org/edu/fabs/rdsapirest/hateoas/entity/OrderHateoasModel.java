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
@Table(name = "CUSTOMER_ORDER")
// classe RepresentationModel para HABILITAR uso de links nos recursos
public class OrderHateoasModel extends RepresentationModel<OrderHateoasModel> {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Status status;
    private String description;

    public OrderHateoasModel() {
    }

    public OrderHateoasModel(Status status, String description) {
        this.id = id;
        this.status = status;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderHateoasModel that = (OrderHateoasModel) o;
        return Objects.equals(id, that.id) && status == that.status && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, status, description);
    }

    @Override
    public String toString() {
        return "OrderHateoas{" +
                "id=" + id +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }

}
