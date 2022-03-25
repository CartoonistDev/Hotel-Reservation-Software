package service;

import model.IRoom;
import model.Customer;
import model.Reservation;
import model.RoomClass;

import java.util.*;


public class ReservationService {

    /**
     * Implementing Singleton pattern so there is only on instance of the
     * ReservationService Class so that it can
     *  be used for both customer and admin mode
     */

    //Collection to store and retrieve Reservation

    public static List<RoomClass> roomList = new ArrayList<>();

    //Static reference
    public static ReservationService reservationService;

    //Create new Customer Service

    public static ReservationService getInstance(){
        if(reservationService == null){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    /**Create a Room having a room number and an ID
     * @param rooms
     */
    public void addRoom(List<RoomClass> rooms){
        roomList.addAll(rooms);
        System.out.println("Room size = " + roomList.size());
    }

    public IRoom getARoom(String roomID){
        for (IRoom room : roomList){
            if (roomID.equals(room.getRoomNumber())){
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        return null;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return null;
    }

    public static Collection<Reservation> getCustomerReservation(Customer customer) {
        return null;
    }

    public List<RoomClass> getRooms() {
        return roomList;
    }

    public void printAllReservations() {
    }

}
