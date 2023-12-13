package org.edu.fabs.rdsapirest.hateoas.controller;

import org.edu.fabs.rdsapirest.hateoas.entity.CustomerHateoas;
import org.edu.fabs.rdsapirest.hateoas.repository.CustomerRepositoryHateoas;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("customers")
public class CustomerControllerHateoas {

    private final CustomerRepositoryHateoas customerRepositoryHateoas;

    public CustomerControllerHateoas(CustomerRepositoryHateoas customerRepositoryHateoas) {
        this.customerRepositoryHateoas = customerRepositoryHateoas;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerHateoas>> getAllCustomers() {
        Long id;
        Link linkUri;

        List<CustomerHateoas> customerHateoasList = customerRepositoryHateoas.findAll();

        if (customerHateoasList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        for (CustomerHateoas customerHateoas : customerHateoasList) {
            id = customerHateoas.getId();
            linkUri = linkTo(methodOn(CustomerControllerHateoas.class).getCustomerById(id)).withSelfRel();
            customerHateoas.add(linkUri);
        }
        return new ResponseEntity<List<CustomerHateoas>>(customerHateoasList,HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerHateoas> getCustomerById(@PathVariable Long id) {
        Link linkUri;

        Optional<CustomerHateoas> customersHateoasOptional = customerRepositoryHateoas.findById(id);

        if (customersHateoasOptional.isPresent()) {
            CustomerHateoas customerHateoas = customersHateoasOptional.get();
            customerHateoas.add(linkTo(methodOn(CustomerControllerHateoas.class).getAllCustomers()).withRel("Customers list"));
            customerHateoas.add(linkTo(methodOn(CustomerControllerHateoas.class).getCustomerById(id)).withSelfRel());
            return new ResponseEntity<>(customerHateoas, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomerHateoas addCustomer(@RequestBody CustomerHateoas newCustomer) {
        return customerRepositoryHateoas.save(newCustomer);
    }

    @PutMapping("/update/{id}")
    public CustomerHateoas replaceCustomer(@PathVariable Long id, @RequestBody CustomerHateoas newCustomer) {
        return customerRepositoryHateoas.findById(id).map(
                customer -> {
                    customer.setName(newCustomer.getName());
                    customer.setAddress(newCustomer.getAddress());
                    customer.setOccupation(newCustomer.getOccupation());
                    return customerRepositoryHateoas.save(newCustomer);
                }).orElseGet(() -> {
            newCustomer.setId(id);
            return customerRepositoryHateoas.save(newCustomer);
                });
    }

    @DeleteMapping("/customer/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        customerRepositoryHateoas.deleteById(id);
    }

}
