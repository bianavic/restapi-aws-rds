package org.edu.fabs.rdsapirest.hateoas.repository;

import org.edu.fabs.rdsapirest.hateoas.entity.OrderHateoasModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositoryHateoas extends JpaRepository<OrderHateoasModel, Long> {
}
