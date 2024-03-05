package com.bunu.springjpa;

import domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import repo.CustomerRepository;

import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories("repo")
@EntityScan("domain")
public class SpringJpaApplication implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        customerRepository.save(new Customer("Johm","Doe","jd@gmai;.com"));
        customerRepository.save(new Customer("Michelle", "Dssler", "mich@mail.com"));
        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer by ID
        Optional<Customer> custOpt = customerRepository.findById(1L);
        Customer customer = custOpt.get();
        System.out.println("Customer found with findOne(1L):");
        System.out.println("--------------------------------");
        System.out.println(customer);
        System.out.println();
    }
}
