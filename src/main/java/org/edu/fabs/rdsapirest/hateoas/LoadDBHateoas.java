package org.edu.fabs.rdsapirest.hateoas;

import org.edu.fabs.rdsapirest.hateoas.entity.EmployeeHateoas;
import org.edu.fabs.rdsapirest.hateoas.entity.OrderHateoasModel;
import org.edu.fabs.rdsapirest.hateoas.entity.Status;
import org.edu.fabs.rdsapirest.hateoas.repository.EmployeeRepositoryHateoas;
import org.edu.fabs.rdsapirest.hateoas.repository.OrderRepositoryHateoas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LoadDBHateoas {

    private static final Logger log = LoggerFactory.getLogger(LoadDBHateoas.class);

    @Bean
    CommandLineRunner loaddata(EmployeeRepositoryHateoas employeeRepositoryHateoas, OrderRepositoryHateoas orderRepositoryHateoas) {

        return args -> {
            log.info("Log of save event: " + employeeRepositoryHateoas.save(new EmployeeHateoas("Maria Silva", "Chef",
                    "avenida silveira dutra 1002")));
            log.info("log of save event: " + employeeRepositoryHateoas.save(new EmployeeHateoas("John Dutra", "Mecanico",
                    "rua joao freire 231")));
            log.info("Log of save event: " + employeeRepositoryHateoas.save(new EmployeeHateoas("Bilbo Baggins", "thief",
                    "The shine")));

            orderRepositoryHateoas.save(new OrderHateoasModel(Status.COMPLETED, "review"));
            orderRepositoryHateoas.save(new OrderHateoasModel(Status.IN_PROGRESS, "travel"));
            orderRepositoryHateoas.save(new OrderHateoasModel(Status.IN_PROGRESS, "sale"));
            orderRepositoryHateoas.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }

}
