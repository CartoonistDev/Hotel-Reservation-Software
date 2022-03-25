package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import static service.ReservationService.findRooms;
import static service.ReservationService.getCustomerReservation;


public class HotelResource {

    /**
     * In this HotelResources section, we are providing implementation for
     * the already created method headers(abstract classes) in CustomerServices
     * and ResourceServices, to avoid the users to interact with our
     * Services
     *
     *
     * Refer to service package to get better understanding
     */

    //Static reference
    public static HotelResource hotelResource;
    public static Collection<String> newReservation = new HashSet<>();

    //Calling Reservation and Customer services here
    ReservationService reservationService = ReservationService.getInstance();
    CustomerService customerService = CustomerService.getInstance();

    public HotelResource(){}

    //Get a customer by email
    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    //Create new customer

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(Customer customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        return reservationService.reserveARoom(customerEmail, room, checkInDate, checkOutDate);

    }

    //Get all Reserved a room of a customer

    public Collection<Reservation> getCustomersReservation(Customer customer){
        return getCustomerReservation(customer);
    }

    //Find a room based on dates that are free

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return findRooms(checkIn, checkOut);
    }

}
