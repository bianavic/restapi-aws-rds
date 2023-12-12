package org.edu.fabs.rdsapirest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadDB implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LoadDB.class);

    private final EmployeeRepository employeeRepository;

    public LoadDB(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("Log event save user 1: " + employeeRepository.save(new Employee("Gonzales", "One", "Address One, 11")));
        logger.info("Log event save user 2: " + employeeRepository.save(new Employee("Lugano", "Two", "Address Two, 22")));
        logger.info("Log event save user 3: " + employeeRepository.save(new Employee("Nero", "Three", "Address Three, 33")));

    }

}
