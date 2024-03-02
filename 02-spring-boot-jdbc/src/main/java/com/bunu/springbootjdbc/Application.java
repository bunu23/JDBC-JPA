package com.bunu.springbootjdbc;

import com.bunu.springbootjdbc.dao.CustomerRepository;
import com.bunu.springbootjdbc.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerDAO;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        customerDAO.clearDB();
        Customer customer=new Customer(1,"John","john@g","423525");
        customerDAO.save(customer);
        customer=new Customer(2,"Josh","23@g","74635");
        customerDAO.save(customer);
        System.out.println(customerDAO.getCustomer(2));
        System.out.println(customerDAO.getAllCustomers());
    }
}
