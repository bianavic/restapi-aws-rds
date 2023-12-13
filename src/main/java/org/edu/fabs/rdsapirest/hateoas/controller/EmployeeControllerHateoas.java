package org.edu.fabs.rdsapirest.hateoas.controller;

import org.edu.fabs.rdsapirest.hateoas.entity.EmployeeHateoas;
import org.edu.fabs.rdsapirest.hateoas.exceptions.EmployeeNotFoundExceptionHateoas;
import org.edu.fabs.rdsapirest.hateoas.repository.EmployeeRepositoryHateoas;
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
@RequestMapping("employees")
public class EmployeeControllerHateoas {

    private final EmployeeRepositoryHateoas employeeRepositoryHateoas;

    public EmployeeControllerHateoas(EmployeeRepositoryHateoas employeeRepositoryHateoas) {
        this.employeeRepositoryHateoas = employeeRepositoryHateoas;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeHateoas>> getAllEmployee() {
        Long id;
        Link linkUri;

        List<EmployeeHateoas> employeeHateoasList = employeeRepositoryHateoas.findAll();

        if (employeeHateoasList.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        for (EmployeeHateoas employeeHateoas: employeeHateoasList) {
            id = employeeHateoas.getId();
            linkUri = linkTo(methodOn(EmployeeControllerHateoas.class).getEmployeeById(id)).withSelfRel();
            employeeHateoas.add(linkUri);
        }
        return new ResponseEntity<List<EmployeeHateoas>>(employeeHateoasList,HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeHateoas> getEmployeeById(@PathVariable Long id) {
        Link linkUri;

        Optional<EmployeeHateoas> employeeHateoasOptional = employeeRepositoryHateoas.findById(id);

        if (employeeHateoasOptional.isPresent()) {
            EmployeeHateoas employeeHateoas = employeeHateoasOptional.get();
            employeeHateoas.add(linkTo(methodOn(EmployeeControllerHateoas.class).getAllEmployee()).withRel("Employee list"));
            employeeHateoas.add(linkTo(methodOn(EmployeeControllerHateoas.class).getEmployeeById(id)).withSelfRel());
            return new ResponseEntity<>(employeeHateoas, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public EmployeeHateoas addEmployee(@RequestBody EmployeeHateoas newEmployee) {
        return employeeRepositoryHateoas.save(newEmployee);
    }

    // modificacao partial
    @PutMapping("/add")
    public EmployeeHateoas replaceEmployee(@RequestBody EmployeeHateoas newEmployee, Long id) {
        return employeeRepositoryHateoas.findById(id).map(
                employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setAddress(newEmployee.getAddress());
                    employee.setRole(newEmployee.getRole());
                    return employeeRepositoryHateoas.save(newEmployee);
                }).orElseGet(() -> {
            newEmployee.setId(id);
            return employeeRepositoryHateoas.save(newEmployee);
                });
    }

    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepositoryHateoas.deleteById(id);
    }

}
