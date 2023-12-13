package org.edu.fabs.rdsapirest.hateoas.repository;

import org.edu.fabs.rdsapirest.hateoas.entity.CustomerHateoas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepositoryHateoas extends JpaRepository<CustomerHateoas, Long> {
}
