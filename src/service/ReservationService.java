package service;

import model.IRoom;
import model.Customer;
import model.Reservation;
import model.RoomClass;

import java.util.*;
import java.util.Date;
import java.util.ArrayList;


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

    public IRoom getARoom(String roomNumber){
        for (IRoom room : roomList){
            if (roomNumber.equals(room.getRoomNumber())){
                return room;
            }
        }
        System.out.println("Room not found. Please verify room  number and try again.");
        return null;
    }

    public void reserveARoom(Customer customer, Date checkInDate, Date checkOutDate, RoomClass room) {
        Reservation reservation = new Reservation(customer, checkInDate, checkOutDate);
        for (RoomClass thisRoom : roomList){
            if (thisRoom.getRoomNumber().equals(room.getRoomNumber())){
                thisRoom.setReservation(reservation);
                System.out.println("Room " + thisRoom.getRoomNumber() + " is Reserved!!!");
            }
        }
    }

    private void getRoomClassByRoomNumber(){
        Map<String, RoomClass> mayKey = new HashMap<>();
        for (RoomClass room: roomList){
            System.out.println(room);
            mayKey.put(room.getRoomNumber(), room);
        }
        //When the user selects a given room number, we can get the by saying mapKey.get(room number)
        //String selectedRoom = userInput.next()
        RoomClass userSelectedRoom = mayKey.get("selectedRoom");

    }

    public static List<RoomClass> findAvailableRooms(Date checkInDate) {

        //Create an empty list
        List<RoomClass> freeRooms = new ArrayList<>();

        //Iterate over roomList to get which rooms are reserved
        for (RoomClass room : roomList){
            if (room.getReservation() == null){
                freeRooms.add(room);
                continue;
            }

            //Get an instant of the reserved room in the RoomClass
            Reservation reservedRoom = room.getReservation();

            if (roomIsAvailable(checkInDate, reservedRoom.getCheckInDate(), reservedRoom.getCheckOutDate())){
                freeRooms.add(room);
            }
        }
        return freeRooms;
    }

    private static boolean roomIsAvailable(Date checkInDate, Date reservedCheckInDate, Date reservedCheckOutDate) {
        return !(checkInDate.before(reservedCheckOutDate) && checkInDate.after(reservedCheckInDate));
    }


    public static RoomClass getCustomerReservation(Customer customer) {
        /**
         * Loop through the roomlist
         * check if a room reservation is not equal to null, get that reservation by
         * Reservation reservation = room.getReservation();
         * if reservation.getCustomer().customerEmail == customer.customerEmail;
         * return the reservedRoom from inside the if statement
         *
         * outside loop return null
         */
        for (RoomClass room : roomList){
            if (room.getReservation() != null){
                Reservation reservation = room.getReservation();
                if (reservation.getCustomer().email == customer.email){
                    return room;
                }
            }
        }
        return null;
    }


    public static List<RoomClass> getAllReservations() {
        /**
         * Create an empty list of the RoomClass type called reservedRoomList
         * Loop through the roomlist
         * check if a room reservation is not equal to null, add that room to the reservedRoomList
         * return reservedRoomList
         *
         **/

        List<RoomClass> allReservation = new ArrayList<>();
        for (RoomClass room : roomList){
            if (room.getReservation() != null){
                allReservation.add(room);
            }
        }
        return allReservation;
    }

    public List<RoomClass> getRooms() {
        return roomList;
    }



    public static void printAllReservations() {

    }

}
