package model;

import java.util.Date;

public class Reservation {

    //Instantiates variables

    public Customer customer;
    public IRoom room;
    public Date checkInDate;
    public Date checkOutDate;

    //Constructors
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    //Methods
    public Customer getCustomer(){
        return customer;
    }
    public IRoom getRoom(){
        return room;
    }
    public Date getCheckInDate(){
        return checkInDate;
    }
    public Date getCheckOutDate(){
        return checkOutDate;
    }

    @Override
    public String toString(){
        return "Customer's Name: " + customer + '\'' +
                "Room Number: " + room + '\'' +
                "Check in date: " + checkInDate + '\'' +
                "Check out date: " + checkOutDate;
    }

}
