package org.edu.fabs.rdsapirest.hateoas.exceptions;

public class OrderNotFoundExceptionHateoas extends RuntimeException {

    public OrderNotFoundExceptionHateoas(Long id) {
        super(String.format("Could not find order id %d ", id));
    }

}
