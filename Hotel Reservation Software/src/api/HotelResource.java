package api;

import model.Customer;
import model.IRoom;
import model.RoomClass;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;


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

    //Create new customer

//    public void createACustomer(String email, String firstName, String lastName){
//        customerService.addCustomer(email, firstName, lastName);
//    }

    public static void createACustomer(String firstName , String lastName , String email){
        CustomerService.getInstance().addCustomer(firstName, lastName, email);
    }

    public Collection<IRoom> alternativeRooms(Date checkInDate, Date checkOutDate){
        return reservationService.findAlternativeRooms(checkInDate, checkOutDate);
    }

    public Date newDate(Date checkOutDate){
        return reservationService.newDate(checkOutDate);
    }


    //Add new customer
    public void addNewCustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email, firstName, lastName);
    }


    public static void bookARoom(Customer customerEmail, Date checkInDate, Date checkOutDate, IRoom room){
        reservationService.reserveARoom(customerEmail, checkInDate, checkOutDate,  room);

    }

    //Get all Reserved a room of a customer

    public static void getCustomerReservations(String customerEmail){
        reservationService.getCustomerReservation(customerEmail);
    }

    public static void getReservations(){
        reservationService.getAllReservations();
    }



}
