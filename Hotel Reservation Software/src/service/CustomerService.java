package service;

import model.Customer;

import java.util.Collection;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    /**In this service package, we create method headers(Abstract classes)
     * so that our user will not interact with our Services.
     *
     *
     * The implementation of these headers is in the api package,
     * where the implementation body  will override the
     * created abstract class code here
     *
     *
     * Refer to api package for better understanding
     *
     * @description Here we create the business logic
     *
     * Implementing Singleton pattern so there is only on instance of the
     * CustomerService Class so that it can
     * be used for both customer and admin mode
     **/

    //Declaring a static property that will reference the class

    public static CustomerService customerService;
    public static final Map<String, Customer> customers = new HashMap<>();

    public CustomerService() {}

    //Create new Customer Service

    public static CustomerService getInstance(){

        //If customerService == null => create new customerService
        if(Objects.isNull(customerService)){
            customerService = new CustomerService();
        }
        return customerService;
    }



    /**
     * Create a Customer based on their email, firstName and lastName
     * Details previously entered by the user
     * Add created customer to the list of customers
     */


    public void addCustomer(String email, String firstName, String lastName){
        customers.put(email, new Customer(firstName, lastName, email));
    }

    //retrieve customer from map by email => key

    public Customer getCustomer(String customerEmail){
        return customers.get(customerEmail);
    }

    //Display all customer data

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

}
