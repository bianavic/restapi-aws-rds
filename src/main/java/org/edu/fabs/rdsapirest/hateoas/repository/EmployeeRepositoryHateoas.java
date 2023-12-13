package org.edu.fabs.rdsapirest.hateoas.repository;

import org.edu.fabs.rdsapirest.hateoas.entity.EmployeeHateoas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepositoryHateoas extends JpaRepository<EmployeeHateoas, Long> {
}
