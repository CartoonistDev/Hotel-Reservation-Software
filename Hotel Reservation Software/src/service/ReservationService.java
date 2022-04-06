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
    public static Map<String, IRoom> roomMap = new HashMap<>();
    public static List<Reservation> reservations = new ArrayList<>();

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
    public void addRooms(Collection<RoomClass> rooms){
        roomList.addAll(rooms);
        System.out.println("Room size = " + roomList.size());
    }

   /**public IRoom getARoom(String roomNumber){
        for (IRoom room : roomList){
            if (roomNumber.equals(room.getRoomNumber())){
                return room;
            }
        }
        System.out.println("Room not found. Please verify room  number and try again.");
        return null;
    }*/

    public void reserveARoom(Customer customer, Date checkInDate, Date checkOutDate, RoomClass room) {
        Reservation reservation = new Reservation(customer, checkInDate, checkOutDate);
        for (RoomClass thisRoom : roomList){
            if (thisRoom.getRoomNumber() == room.getRoomNumber()){
                thisRoom.setReservation(reservation);
                System.out.println("Room " + thisRoom.getRoomNumber() + " is Reserved!!!");
            }
        }
    }


    /**public static List<RoomClass> findAvailableRooms(Date checkInDate, Date checkOutDate) {

        //Create an empty list
        List<RoomClass> freeRooms = new ArrayList<>();

        try {
            //Iterate over roomList to get which rooms are reserved
            for (RoomClass room : roomList){
                if (room.getReservation() == null){
                    freeRooms.add(room);
                }

                //Get an instant of the reserved room in the RoomClass
                Reservation reservedRoom = room.getReservation();

                if (roomIsAvailable(checkInDate, checkOutDate, reservedRoom.getCheckInDate(), reservedRoom.getCheckOutDate())){
                   freeRooms.add(room);
                }
                else {
                    freeRooms.remove(room);
                    System.out.println("There is no room available at this time.");
                }
            }
            return freeRooms;
        }
        catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    private static boolean roomIsAvailable(Date checkInDate, Date checkOutDate, Date reservedCheckInDate, Date reservedCheckOutDate) {
        return (checkInDate.before(reservedCheckInDate) && checkOutDate.before(reservedCheckInDate)
                && (checkInDate.after(reservedCheckOutDate) && checkOutDate.after(reservedCheckOutDate)));
    }*/


    public static RoomClass getCustomerReservation(Customer customer) {
        /**
         * Loop through the roomList
         * check if a room reservation is not equal to null, get that reservation by
         * Reservation reservation = room.getReservation();
         * if reservation.getCustomer().customerEmail == customer.customerEmail;
         * return the reservedRoom from inside the if statement
         *
         * outside loop return null
         */
        for (RoomClass room : roomList){
            if (!room.getReservation().equals(null)){
                Reservation reservation = room.getReservation();
                if (reservation.getCustomer().getEmail() == customer.email){
                    System.out.println(room);
                    return room;
                }
            }
        }
        return null;
    }


    public static List<RoomClass> getAllReservations() {
        /**
         * Create an empty list of the RoomClass type called reservedRoomList
         * Loop through the roomList
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
        if (getAllReservations().isEmpty()){
            System.out.println("There is no reservation at this time");
        }
        else {
            System.out.println("'\n'*****Available Reservations*****");
            System.out.println(getAllReservations());
        }

    }


    public Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<>(); //store rooms that are available, i.e. not reserved
        for (IRoom room : roomList){
            if (!isRoomReserved(room, checkInDate, checkOutDate)){
                availableRooms.add(room);
            } else if (isRoomReserved(room, checkInDate, checkOutDate)){
                System.out.println("There is no room available at this time.");
            }
            else {
                availableRooms.remove(room);
                System.out.println("There is no room available at this time.");
            }
        }
        return availableRooms;
    }

    private boolean isRoomReserved(IRoom room, Date checkInDate, Date checkOutDate){
        if (reservations == null) return false; //if no reservation has been made, then all rooms are free
        else {
            for (Reservation reservation : reservations) {
                Reservation reservedRoom = reservation.getReservation();
                if (reservedRoom.getReservation().equals(room.getRoomNumber())) {
                    System.out.println("Room is reserved..checking if room will be free by date...");
                    // if the room has been reserved but the new date is not within the reserved room's date, then it is free.
                    if (isDateWithinRange(checkInDate, checkOutDate, reservation)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    public boolean isDateWithinRange (Date checkInDate, Date checkOutDate, Reservation reservation) {
        return !(checkOutDate.before(reservation.getCheckInDate()) || checkInDate.after(reservation.getCheckOutDate()));
    }

}
