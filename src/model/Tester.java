package model;

import service.CustomerService;

import static service.CustomerService.customerService;

public class Tester {
    public static void main (String[] args){

        //Creating an object Customer and passing the variables as new objects
        //Customer customer = new Customer("first", "last", "j@email.com");
        //System.out.println(customer);

        CustomerService customerService = CustomerService.getInstance();
        customerService.addCustomer("asdf@gmail.com", "first", "last");

        System.out.println(customerService.getCustomer("asdf@gmail.com").toString());
    }

}

