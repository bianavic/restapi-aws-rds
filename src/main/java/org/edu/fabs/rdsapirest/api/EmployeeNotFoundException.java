package org.edu.fabs.rdsapirest.api;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super(String.format("Could not find id %d ", id));
    }

}
