package org.sid.customer_service;

import org.sid.customer_service.entities.Customer;
import org.sid.customer_service.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, RepositoryRestConfiguration restConfiguration){
        restConfiguration.exposeIdsFor(Customer.class);
        return args -> {
            customerRepository.save(new Customer(null,"arhsai","arhsaiimane@gmail.com"));
            customerRepository.save(new Customer(null,"x","x@gmail.com"));
            customerRepository.save(new Customer(null,"y","y@gmail.com"));
            customerRepository.findAll().forEach(c->{
                System.out.println(c.toString());
            });
        };
    }
}
