package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.RoomClass;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;



public class AdminResource {
    private static AdminResource adminResource;
    public Collection<String> newCustomer = new HashSet<>();

    ReservationService reservationService = ReservationService.getInstance();
    CustomerService customerService = CustomerService.getInstance();

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void addRoom(List<RoomClass> rooms){
        reservationService.addRoom(rooms);
    }

    public List<RoomClass> getAllRooms(){
        return reservationService.getRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public Collection<Reservation> displayAllReservations(){
        reservationService.printAllReservations();
        return null;
    }
}
