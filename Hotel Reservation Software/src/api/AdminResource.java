package api;

import model.Customer;
import model.Reservation;
import model.RoomClass;
import service.CustomerService;
import service.ReservationService;

import java.util.*;


public class AdminResource {

    public static final ReservationService reservationService = ReservationService.getInstance();
    public static final CustomerService customerService = CustomerService.getInstance();

    public AdminResource(){};

    public static Customer getCustomers(String email){
        return customerService.getCustomer(email);
    }

    public static void addRoom(List<RoomClass> rooms){
        reservationService.addRoom(rooms);
    }

    public static List<RoomClass> getAllRooms(){
        return reservationService.getRooms();
    }

    public static Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public static Collection<Reservation> displayAllReservations(){
        reservationService.printAllReservations();
        return null;
    }

    public List<RoomClass> availableRooms(Date checkInDate){
        return reservationService.findAvailableRooms(checkInDate);
    }

}
