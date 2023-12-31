package org.edu.fabs.rdsapirest.hateoas.controller;

import org.edu.fabs.rdsapirest.hateoas.entity.OrderHateoasModel;
import org.edu.fabs.rdsapirest.hateoas.entity.Status;
import org.edu.fabs.rdsapirest.hateoas.exceptions.OrderNotFoundExceptionHateoas;
import org.edu.fabs.rdsapirest.hateoas.repository.OrderRepositoryHateoas;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("orders")
public class OrderControllerHateoas {

    private final OrderRepositoryHateoas orderRepositoryHateoas;

    public OrderControllerHateoas(OrderRepositoryHateoas orderRepositoryHateoas) {
        this.orderRepositoryHateoas = orderRepositoryHateoas;
    }

    @GetMapping("/all")
    ResponseEntity<List<OrderHateoasModel>> getAllOrders() {
        Long orderId;
        Link linkUri;

        List<OrderHateoasModel> orderList = orderRepositoryHateoas.findAll();

        if (orderList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        for (OrderHateoasModel orderHateoasModel : orderList) {
            orderId = orderHateoasModel.getId();
            linkUri = linkTo(methodOn(OrderControllerHateoas.class).getOrderById(orderId)).withSelfRel();
            orderHateoasModel.add(linkUri);
            linkUri = linkTo(methodOn(OrderControllerHateoas.class).getAllOrders()).withRel("Customer order list");
            orderHateoasModel.add(linkUri);
        }
        return new ResponseEntity<List<OrderHateoasModel>>(orderList, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    ResponseEntity<OrderHateoasModel> getOrderById(@PathVariable Long id) {

        Optional<OrderHateoasModel> orderPointer = orderRepositoryHateoas.findById(id);

        if (orderPointer.isPresent()) {
            OrderHateoasModel order = orderPointer.get();
            order.add(linkTo(methodOn(OrderControllerHateoas.class).getAllOrders()).withRel("All Orders"));
            order.add(linkTo(methodOn(OrderControllerHateoas.class).getOrderById(id)).withSelfRel());
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    OrderHateoasModel newOrder(@RequestBody OrderHateoasModel newOrder) {
        return orderRepositoryHateoas.save(newOrder);
    }

    @PutMapping("/update/{id}")
    OrderHateoasModel replaceOrder(@PathVariable Long id, @RequestBody OrderHateoasModel newOrder) {
        return orderRepositoryHateoas.findById(id).map(order -> {
            order.setDescription(newOrder.getDescription());
            order.setStatus(newOrder.getStatus());
            return orderRepositoryHateoas.save(newOrder);
        }).orElseGet(() -> {
            newOrder.setId(id);
            return orderRepositoryHateoas.save(newOrder);
        });
    }

    @PutMapping("/{id}/cancel")
    ResponseEntity<?> cancelOrderById(@PathVariable Long id) {
        OrderHateoasModel cancelledOrder = orderRepositoryHateoas.findById(id).orElseThrow(() -> new OrderNotFoundExceptionHateoas(id));
        if (cancelledOrder.getStatus() == Status.IN_PROGRESS) {
            cancelledOrder.setStatus(Status.CANCELLED);
            cancelledOrder.add(linkTo(methodOn(OrderControllerHateoas.class).getOrderById(id)).withSelfRel());
            cancelledOrder.add(linkTo(methodOn(OrderControllerHateoas.class).getAllOrders()).withRel("Order list"));
            orderRepositoryHateoas.save(cancelledOrder);
            return new ResponseEntity<>(cancelledOrder, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body("You can't complete the task, the order has a " +
                        cancelledOrder.getStatus() + " status");
    }

    @PutMapping("/{id}/complete")
    ResponseEntity<?> completeOrderById(@PathVariable Long id) {
        OrderHateoasModel completedOrder = orderRepositoryHateoas.findById(id).orElseThrow(() -> new OrderNotFoundExceptionHateoas(id));
        if (completedOrder.getStatus() == Status.IN_PROGRESS) {
            completedOrder.setStatus(Status.COMPLETED);
            completedOrder.add(linkTo(methodOn(OrderControllerHateoas.class).getOrderById(id)).withSelfRel());
            completedOrder.add(linkTo(methodOn(OrderControllerHateoas.class).getAllOrders()).withRel("Order list"));
            orderRepositoryHateoas.save(completedOrder);
            return new ResponseEntity<>(completedOrder, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body("You can't complete the task, the order has a " +
                        completedOrder.getStatus() + " status");
    }

    @DeleteMapping("/order/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable long id) {
        orderRepositoryHateoas.deleteById(id);
    }


}
