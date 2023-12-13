package org.edu.fabs.rdsapirest.hateoas.exceptions;

public class EmployeeNotFoundExceptionHateoas extends RuntimeException {

    public EmployeeNotFoundExceptionHateoas(Long id) {
        super(String.format("Could not find id %d ", id));
    }

}
