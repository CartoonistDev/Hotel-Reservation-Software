package application;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import model.RoomClass;

import java.text.SimpleDateFormat;
import java.util.*;

/**public class HotelApplication {
    public static void main(String[] args){
        //Getting an instance of the hotelResources and adminResources class
        HotelResource hotelResource = new HotelResource();
        AdminResource adminResource = new AdminResource();

        //Get user input using the scanner object
        Scanner userInput = new Scanner(System.in);

        public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate, boolean isFree) {
            try {
                Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate, isFree);
                String mapKey = (customer.getFirstName() + " " + customer.getLastName());
                if (!reservations.containsKey(mapKey)) {
                    reservations.putIfAbsent(mapKey, new ArrayList<>());
                }
                reservations.get(mapKey).add(reservation);
                System.out.println(reservation);
            }
            catch (NullPointerException e) {
                System.out.println("Customer information invalid.\n");
            }
            return null;
        }

        public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
            Collection<IRoom> availableRooms = new ArrayList<>(); //store rooms that are available, i.e. not reserved
            for (IRoom room : roomList) {
                if (!isRoomReserved(room, checkInDate, checkOutDate)) {
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        }

        public void getCustomerReservation(String customer) {
            ArrayList<ArrayList<Reservation>> customerReservations = new ArrayList<>();
            for (String reservation : reservations.keySet()) {
                if (reservation.equalsIgnoreCase(customer)) {
                    ArrayList<Reservation> allReservations = reservations.get(reservation);
                    for (ArrayList<Reservation> value : allReservations) {
                        customerReservations.add(value);
                    }
                }
            }
            for (ArrayList res : customerReservations) {
                System.out.println(res);
            }
        }

        public List<RoomClass> getRooms() {
            return roomList;
        }


        public static boolean isRoomReserved(IRoom room, Date checkInDate, Date checkOutDate) {
            if (reservations.isEmpty()) return false; //if no reservation has been made, then all rooms are free
            for (ArrayList<Reservation> reservations : reservations.values()) {
                for (Reservation reservation : reservations) {
                    IRoom reservedRoom = reservation.getRoom();
                    if (reservedRoom.getRoomNumber().equals(room.getRoomNumber())) {
                        // if the room has been reserved but the new date is not within the reserved room's date, then it is free.
                        if (isDateWithinRange(checkInDate, checkOutDate, reservation)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        static boolean isDateWithinRange(Date checkInDate, Date checkOutDate, Reservation reservation) {
            return !(checkOutDate.before(reservation.getCheckInDate()) || checkInDate.after(reservation.getCheckOutDate()));
        }

        public static void printAllReservations() {
            if (reservations.isEmpty()) {
                System.out.println("No reservations in database.");
            }
            else {
                for (String reservation : reservations.keySet()) {
                    System.out.println(reservations.get(reservation));
                }
            }
        }

        public static Date addDays(Date date, int days) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days); //minus number would decrement the days
            return cal.getTime();
        }


    }

    public static void findAndReserveRoom(AdminResource adminResource) {
        Collection<String> roomNumbers = new HashSet<>();
        Date checkIn = null;
        Date checkOut = null;
        var isFree = false;
        try {
            Scanner dates = new Scanner(System.in);
            printInfo("Enter check-in date: mm-dd-yyyy");
            String checkInDate = dates.nextLine();
            checkIn = new SimpleDateFormat("MM-dd-yyyy").parse(checkInDate);
            boolean in = true;
            String checkOutDate = null;
            while (in) {
                try {
                    printInfo("Enter check-out date: mm-dd-yyyy");
                    checkOutDate = dates.nextLine();
                    checkOut = new SimpleDateFormat("MM-dd-yyyy").parse(checkOutDate);
                    if (checkOutDate.equals(checkInDate) || (checkOut.before(checkIn))) {
                        printInfo("Checkout date must be after checkin date.");
                    }
                    else {
                        in = false;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            printInfo("Please use the correct date format.");
        }
        printInfo("Book a room? Y or N");
        Scanner book = new Scanner(System.in);
        String reserve = book.nextLine();
        try {
            switch (reserve.toUpperCase()) {
                case "Y":
                    if (adminResource.getAllRooms().isEmpty()) {
                        printInfo("There are no rooms in the database. Please create rooms.\n");
                        break;
                    }
                    else {
                        Collection<IRoom> rooms = new ArrayList<>(HotelResource.findARoom(checkIn, checkOut));
                        if (!rooms.isEmpty()){
                            printInfo("Here are all available hotel rooms");
                            for (IRoom room : rooms) {
                                System.out.println(room);
                                roomNumbers.add(room.getRoomNumber());
                            }
                        }
                        else {
                            boolean run = true;
                            while(run) {
                                try {
                                    printInfo("No rooms available for the selected dates. Search for rooms? Y/N");
                                    String search = book.nextLine();
                                    switch (search.toUpperCase()) {
                                        case "Y":
                                            Collection<IRoom> newRooms = new ArrayList<>(HotelResource.findARoom(checkIn, checkOut));
                                            if (newRooms.isEmpty()) {
                                                boolean keepRunning = true;
                                                while (keepRunning) {
                                                    checkIn = adminResource.allDays(checkIn, 7);
                                                    checkOut = adminResource.allDays(checkOut, 7);
                                                    newRooms = new ArrayList<>(HotelResource.findARoom(checkIn, checkOut));
                                                    if (!newRooms.isEmpty()) {
                                                        keepRunning = false;
                                                    }
                                                }
                                                printInfo("Here are all available hotel rooms");
                                                printInfo("Dates: " + checkIn + "-" + checkOut + "\n");
                                                for (IRoom room : newRooms) {
                                                    System.out.println(room);
                                                    roomNumbers.add(room.getRoomNumber());
                                                }
                                                run = false;
                                            } else {
                                                printInfo("Here are all available hotel rooms");
                                                printInfo("Dates: " + checkIn + "-" + checkOut + "\n");
                                                for (IRoom room : newRooms) {
                                                    System.out.println(room);
                                                    roomNumbers.add(room.getRoomNumber());
                                                }
                                            }
                                            break;
                                        case "N":
                                            return;
                                        default:
                                            printInfo("Input incorrect. Please enter Y or N.");

                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                            boolean running = true;
                            while (running) {
                                try {
                                    printInfo("\nBook room?");
                                    String confirm = book.nextLine();
                                    switch (confirm.toUpperCase()) {
                                        case "Y":
                                            running = false;
                                            break;
                                        case "N":
                                            return;
                                        default:
                                            printInfo("Input incorrect. Please enter Y or N.");
                                    }
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                }
                            }
                        }
                    }
                    printInfo("\nEnter room number");
                    String roomNumber = null;
                    while(true) {
                        try {
                            roomNumber = book.nextLine();
                            if (!roomNumbers.contains(roomNumber)) {
                                printInfo("Room unavailable. Please select an available room from the list.");
                            }
                            else {
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                            printInfo("Invalid input");
                        }
                    }
                    printInfo("Enter customer email");
                    String customerEmail = book.nextLine();
                    IRoom room = HotelResource.getRoom(roomNumber);
                    HotelResource.bookARoom(customerEmail, room, checkIn, checkOut, isFree);
                    printInfo("******************************");
                    printInfo("THANK YOU FOR YOUR RESERVATION");
                    printInfo("******************************");
                    break;
                case "N":
                    break;
                default:
                    printInfo("Input incorrect. \nPlease enter Y or N");
            }
        } catch (Exception e) {
            System.out.println(e);
            printInfo("Input incorrect. \nPlease enter Y or N");
        }
    }
    public static void getCustomersReservation() {
        try {
            Scanner scanner = new Scanner(System.in);
            printInfo("Enter customer Email:");
            String email = scanner.next();
            String customer = HotelResource.getCustomer(email).getFullName();
            HotelResource.getCustomerReservations(customer);
        }
        catch (Exception e) {
            printInfo("");
        }
    }

}*/
