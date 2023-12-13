//package org.edu.fabs.rdsapirest.simpleapi;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LoadPersonDB implements CommandLineRunner {
//
//    private static final Logger logger = LoggerFactory.getLogger(LoadPersonDB.class);
//
//    private final PersonRepository personRepository;
//
//    public LoadPersonDB(PersonRepository personRepository) {
//        this.personRepository = personRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        personRepository.save(new Person("Gonzales", "One"));
//        personRepository.save(new Person("Lugano", "Two"));
//        personRepository.save(new Person("Nero", "Three"));
//
//        personRepository.findAll().forEach((person) -> {
//            logger.info("{}", person);
//        });
//
//    }
//
//}
