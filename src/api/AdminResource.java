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
import java.util.List;



public class AdminResource {
    private static AdminResource adminResource;
    public Collection<String> newCustomer = new HashSet<>();

    public static final ReservationService reservationService = ReservationService.getInstance();
    public static final CustomerService customerService = CustomerService.getInstance();

    public Customer getCustomer(String email){
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

    public List<RoomClass> avaliableRooms(Date checkInDate){
        return reservationService.findAvailableRooms(checkInDate);
    }

   // public static Date allDays(Date date, int number){
      // return reservationService.addDays(date, number);
    //}
}
