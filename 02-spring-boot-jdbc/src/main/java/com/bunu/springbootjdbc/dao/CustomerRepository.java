package com.bunu.springbootjdbc.dao;

import com.bunu.springbootjdbc.domain.CreditCard;
import com.bunu.springbootjdbc.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Clears all records from the 'customer' table.
     */
    public void clearDB(){
        Map<String,Object> namedParameters=new HashMap<String,Object>();
        jdbcTemplate.update("DELETE from customer",namedParameters);
    }
    /**
     * Saves a new Customer entity and its associated CreditCard entity into the database.
     *
     * @param customer The Customer entity to be saved.
     */
    public void save(Customer customer){
        Map<String,Object> namedParameters=new HashMap<String,Object>();
        namedParameters.put("customerNumber",customer.getCustomerNumber());
        namedParameters.put("name",customer.getName());
        namedParameters.put("email",customer.getEmail());
        namedParameters.put("phone",customer.getPhone());
        jdbcTemplate.update(
                "INSERT INTO customer VALUES(:customerNumber,:name,:email,:phone)",
                namedParameters);

        Map<String,Object> namedParametersCreditc=new HashMap<String,Object>();
        namedParametersCreditc.put("customerNumber",customer.getCustomerNumber());
        namedParametersCreditc.put("cardNumber",customer.getCreditCard().getCardNumber());
        namedParametersCreditc.put("type",customer.getCreditCard().getType());
        namedParametersCreditc.put("validation",customer.getCreditCard().getValidation());
        jdbcTemplate.update(
                "INSERT INTO creditCard VALUES(:cardNumber,:type,:validation,:customerNumber)",namedParametersCreditc);

    }
    /**
     * Retrieves a Customer entity based on the provided customer number.
     *
     * @param customerNumber The unique identifier for the Customer entity.
     * @return The retrieved Customer entity.
     */

    public Customer getCustomer(int customerNumber){
        Map<String,Object> namedParameters=new HashMap<String,Object>();
        namedParameters.put("customerNumber",customerNumber);
        Customer customer=jdbcTemplate.queryForObject(

                "SELECT * FROM customer WHERE customerNumber=:customerNumber",
                namedParameters,
                (rs,rowNum)->new Customer(rs.getInt("customerNumber"),
                        rs.getString("name"),
                      rs.getString("email"),
                        rs.getString("phone")));
       CreditCard creditCard=getCreditCardForCustomer(customer.getCustomerNumber());
       customer.setCreditCard(creditCard);
        return customer;
    }
    /**
     * Retrieves a CreditCard entity associated with a Customer based on the provided customer number.
     *
     * @param customerNumber The unique identifier for the Customer entity.
     * @return The retrieved CreditCard entity.
     */

    CreditCard getCreditCardForCustomer(int customerNumber){
        Map<String,Object> namedParameters = new HashMap<String,Object>();
        namedParameters.put("customerNumber", customerNumber);
        CreditCard creditCard = jdbcTemplate.queryForObject("SELECT * FROM creditcard WHERE "
                        + "customerNumber =:customerNumber ",
                namedParameters,
                (rs, rowNum) -> new CreditCard( rs.getString("cardNumber"),
                        rs.getString("type"),
                        rs.getString("validation")));

        return creditCard;
    }
    /**
     * Retrieves a list of all Customer entities from the database, including their associated CreditCard entities.
     *
     * @return List of all Customer entities.
     */
    public List<Customer> getAllCustomers(){
        List<Customer> customers=jdbcTemplate.query(
                "SELECT * FROM customer",
                new HashMap<String,Customer>(),
                (rs,rowNum)->new Customer(rs.getInt("customerNumber"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")));
        for (Customer customer: customers){
            CreditCard creditCard = getCreditCardForCustomer(customer.getCustomerNumber());
            customer.setCreditCard(creditCard);
        }
        return customers;
    }

}
