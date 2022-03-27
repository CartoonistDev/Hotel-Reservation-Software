package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.RoomClass;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import static service.ReservationService.findAvailableRooms;


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

    //Calling Reservation and Customer services here
    public static final ReservationService reservationService = ReservationService.getInstance();
    public static final CustomerService customerService = CustomerService.getInstance();

    public HotelResource(){}

    //Get a customer by email
    public static Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    //Create new customer

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email, firstName, lastName);
    }

    public static IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public static void bookARoom(Customer customerEmail, RoomClass room, Date checkInDate, Date checkOutDate){
        reservationService.reserveARoom(customerEmail, checkInDate, checkOutDate,  room);

    }

    //Get all Reserved a room of a customer

    public static void getCustomerReservations(Customer customer){
        reservationService.getCustomerReservation(customer);
    }

    public static void getReservations(){
        reservationService.getAllReservations();
    }



}
