package api;

import model.*;

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

    public static void addRoom(List<IRoom> rooms){
        reservationService.addRooms(rooms);
    }

    public static List<IRoom> getAllRooms(){
        return reservationService.getRooms();
    }

    public static Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public static Collection<Reservation> displayAllReservations(){
        reservationService.printAllReservations();
        return null;
    }

    public Collection<IRoom> availableRooms(Date checkInDate, Date checkOutDate){
        return reservationService.findAvailableRooms(checkInDate, checkOutDate);
    }

}
