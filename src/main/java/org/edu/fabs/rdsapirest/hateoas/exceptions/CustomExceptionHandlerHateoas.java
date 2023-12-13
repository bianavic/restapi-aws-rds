package org.edu.fabs.rdsapirest.hateoas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandlerHateoas {

    @ResponseBody
    @ExceptionHandler(OrderNotFoundExceptionHateoas.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderNotFoundHandler(OrderNotFoundExceptionHateoas ex) {
        final String message = ex.getMessage();
        return message;
    }

    @ResponseBody
    @ExceptionHandler(CustomerNotFoundExceptionHateoas.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String customerNotFoundHandler(CustomerNotFoundExceptionHateoas ex) {
        final String message = ex.getMessage();
        return message;
    }

}
