package org.edu.fabs.rdsapirest.hateoas.controller;

import org.edu.fabs.rdsapirest.hateoas.entity.OrderHateoasModel;
import org.edu.fabs.rdsapirest.hateoas.repository.OrderRepositoryHateoas;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    OrderHateoasModel newOrder(@RequestBody OrderHateoasModel newOrder) {
        return orderRepositoryHateoas.save(newOrder);
    }

    @PutMapping("/{id}")
    OrderHateoasModel replaceOrder(@RequestBody OrderHateoasModel newOrder, Long id) {
        return orderRepositoryHateoas.findById(id).map(order -> {
            order.setDescription(newOrder.getDescription());
            order.setStatus(newOrder.getStatus());
            return orderRepositoryHateoas.save(newOrder);
        }).orElseGet(() -> {
            newOrder.setId(id);
            return orderRepositoryHateoas.save(newOrder);
        });
    }

    @DeleteMapping("/order/{id}")
    void deleteOrder(@PathVariable long id) {
        orderRepositoryHateoas.deleteById(id);
    }


}
