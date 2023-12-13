package org.edu.fabs.rdsapirest.hateoas.exceptions;

public class CustomerNotFoundExceptionHateoas extends RuntimeException {

    public CustomerNotFoundExceptionHateoas(Long id) {
        super(String.format("Could not find id %d ", id));
    }

}
