package model;

import java.util.Date;

public class Reservation{

    //Instantiates variables

    public Customer customer;
    public Date checkInDate;
    public Date checkOutDate;

    //Constructors
    public Reservation(Customer customer, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

    }

    //Methods
    public Customer getCustomer(){
        return customer;
    }

    public Date getCheckInDate(){
        return checkInDate;
    }
    public Date getCheckOutDate(){
        return checkOutDate;
    }

    @Override
    public String toString(){
        return "Customer's Name: " + customer + '\n' +
                "Check in date: " + checkInDate + '\n' +
                "Check out date: " + checkOutDate;
    }

}