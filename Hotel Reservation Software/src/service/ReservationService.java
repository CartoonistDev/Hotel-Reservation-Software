package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.IRoom;

import java.util.*;


public class ReservationService {

    /**
     * Implementing Singleton pattern so there is only on instance of the
     * ReservationService Class so that it can
     * be used for both customer and admin mode
     */

    //Collection to store and retrieve Reservation

    public static List<IRoom> roomList = new ArrayList<>();
    public static Map<String, IRoom> roomMap = new HashMap<>();
    public static List<Reservation> reservations = new ArrayList<>();

    //Static reference
    public static ReservationService reservationService;

    //Create new Customer Service

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    /**
     * public static List<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
     * <p>
     * //Create an empty list
     * List<IRoom> freeRooms = new ArrayList<>();
     * <p>
     * try {
     * //Iterate over roomList to get which rooms are reserved
     * for (IRoom room : roomList){
     * if (room.getReservation() == null){
     * freeRooms.add(room);
     * }
     * <p>
     * //Get an instant of the reserved room in the IRoom
     * Reservation reservedRoom = room.getReservation();
     * <p>
     * if (roomIsAvailable(checkInDate, checkOutDate, reservedRoom.getCheckInDate(), reservedRoom.getCheckOutDate())){
     * freeRooms.add(room);
     * }
     * else {
     * freeRooms.remove(room);
     * System.out.println("There is no room available at this time.");
     * }
     * }
     * return freeRooms;
     * }
     * catch (Exception e){
     * System.out.println(e.getLocalizedMessage());
     * }
     * return null;
     * }
     * <p>
     * private static boolean roomIsAvailable(Date checkInDate, Date checkOutDate, Date reservedCheckInDate, Date reservedCheckOutDate) {
     * return (checkInDate.before(reservedCheckInDate) && checkOutDate.before(reservedCheckInDate)
     * && (checkInDate.after(reservedCheckOutDate) && checkOutDate.after(reservedCheckOutDate)));
     * }
     */


    public static IRoom getCustomerReservation(String customerEmail) {
        /**
         * Loop through the roomList
         * check if a room reservation is not equal to null, get that reservation by
         * Reservation reservation = room.getReservation();
         * if reservation.getCustomer().customerEmail == customer.customerEmail;
         * return the reservedRoom from inside the if statement
         *
         * outside loop return null
         */
        for (IRoom room : roomList) {
            if (room.isReserved()){
                Reservation reservation = room.getReservation();
                if (reservation.getCustomer().getEmail().equals(customerEmail)) {
                    printInfo(room);
                    return room;
                }
            }
        }
        printInfo("No reservation found");
        return null;
    }

    public static List<IRoom> getAllReservations() {
        /**
         * Create an empty list of the IRoom type called reservedRoomList
         * Loop through the roomList
         * check if a room reservation is not equal to null, add that room to the reservedRoomList
         * return reservedRoomList
         *
         **/

        List<IRoom> allReservation = new ArrayList<>();
        for (IRoom room : roomList) {
            if (room.getReservation() != null) {
                allReservation.add(room);
            }
        }
        return allReservation;
    }

    public static void printAllReservations() {
        if (getAllReservations().isEmpty()) {
            System.out.println("There is no reservation at this time");
        } else {
            System.out.println("'\n'*****Available Reservations*****");
            System.out.println(getAllReservations());
        }

    }

    /**
     * Create a Room having a room number and an ID
     *
     * @param rooms
     */
    public void addRooms(Collection<IRoom> rooms) {
        roomList.addAll(rooms);
        System.out.println("Room size = " + roomList.size());
    }

    /**
     * public IRoom getARoom(String roomNumber){
     * for (IRoom room : roomList){
     * if (roomNumber.equals(room.getRoomNumber())){
     * return room;
     * }
     * }
     * System.out.println("Room not found. Please verify room  number and try again.");
     * return null;
     * }
     */

    public void reserveARoom(Customer customer, Date checkInDate, Date checkOutDate, IRoom room) {
        Reservation reservation = new Reservation(customer, checkInDate, checkOutDate);
        printInfo("reservation == "+reservation);
        try {
            for (IRoom thisRoom : roomList) {
                if (thisRoom.getRoomNumber().equals(room.getRoomNumber())) {
                    thisRoom.setReservation(reservation);
                    //roomList.remove(thisRoom);
                    printInfo("thisRoom == "+thisRoom);
                    System.out.println("Room " + thisRoom.getRoomNumber() + " is Reserved!!!");
                }
            }
        } catch (ConcurrentModificationException e) {
            e.getLocalizedMessage();
        }
    }

    private static void printInfo(Object message){
        System.out.println(message.toString());
    }

    public List<IRoom> getRooms() {
        return roomList;
    }

    public Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<>(); //store rooms that are available, i.e. not reserved
        for (IRoom room : roomList) {
            if (!isRoomReserved(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    private boolean isRoomReserved(IRoom room, Date checkInDate, Date checkOutDate) {
        if (room.isReserved()) {
            Reservation reservedRoom = room.getReservation();
            if (isDateWithinRange(checkInDate, checkOutDate, reservedRoom)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDateWithinRange(Date checkInDate, Date checkOutDate, Reservation reservation) {
        return !(checkOutDate.before(reservation.getCheckInDate()) || checkInDate.after(reservation.getCheckOutDate()));
    }

}
