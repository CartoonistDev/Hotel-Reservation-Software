package application;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.RoomClass;
import model.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    public static void main(String[] args) {

        //Get user input using the scanner object


        //Getting an instance of the hotelResources class
        HotelResource hotelResource = new HotelResource();
        AdminResource adminResource = new AdminResource();
        HashMap<String, IRoom> roomMap = new HashMap<>();

        showMainMenu();
        callTheFunction(hotelResource, adminResource, roomMap);
    }

    public static void callTheFunction( HotelResource hotelResource, AdminResource adminResource, HashMap<String, IRoom> roomMap) {
        Scanner userInput = new Scanner(System.in);

        try {
            //String selectedInput = userInput.next();
            int selectedInput = Integer.parseInt(userInput.next());
            Customer customer = new Customer();
            //createDefaultRoom();
           // createDefaultCustomer(); // Create dummy users

            switch (selectedInput){
                case 1 :
                    findAndReserve(customer, adminResource, hotelResource, roomMap);
                    runMenuAgain(hotelResource, adminResource, roomMap);
                    break;
                case  2 :
                    //TODO: take user input and pass to the below function
                    seeCustomerReservation(userInput, customer);
                    // HotelResource.getCustomerReservations("user1@gmail.com");
                    runMenuAgain(hotelResource, adminResource, roomMap);
                    break;
                case  3 :
                    createAnAccount(hotelResource);
                    runMenuAgain(hotelResource, adminResource, roomMap);
                    break;
                case 4 :
                    AdminMenu.main(null);
                    break;
                case 5 :
                    printInfo("BYE");
                    break;
                default:
                    printInfo("Please enter a valid input 1 - 5.");
                    runMenuAgain(hotelResource, adminResource, roomMap);
            }


        } catch (NumberFormatException | InputMismatchException e) {
            printInfo("Error, enter a valid input");
            showMainMenu();
            callTheFunction(hotelResource, adminResource, roomMap);
        }
    }

    private static void showMainMenu() {
        printInfo("---------------------------------------");
        printInfo("welcome to hotel menu");
        printInfo("select an option to proceed");
        printInfo("1. Find and Reserve a room");
        printInfo("2. See my Reservations");
        printInfo("3. Create an Account");
        printInfo("4. Admin");
        printInfo("5. Exit");
        printInfo("---------------------------------------");
    }

    private static void printInfo(String message) {
        System.out.println(message);
    }

    private static void findAndReserve(
            Customer customer, 
            AdminResource adminResource, 
            HotelResource hotelResource, 
            HashMap<String, IRoom> roomMap) {

        Scanner userInput = new Scanner(System.in);
        //Declaring variables
        Date checkInDate = null;
        Date checkOutDate;

        try {
            Scanner inputDate = new Scanner(System.in);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            printInfo("Enter CheckIn Date mm/dd/yy example 01/15/2020");
            String selectCheckInDate = inputDate.nextLine();
            if (selectCheckInDate.trim().length() > 0) {
                checkInDate = formatter.parse(selectCheckInDate);
                printInfo("CheckIn Date: " + checkInDate);
            }
            printInfo("Enter CheckOut Date mm/dd/yy example 01/21/2020");
            String selectCheckOutDate = inputDate.nextLine();

            //Throw this error if CheckOut Date entered comes before CheckIn Date entered
            if (selectCheckOutDate.compareTo(selectCheckInDate) == -1) {
                printInfo("Please enter a valid date that comes after CheckIn Date");
                findAndReserve(customer, adminResource, hotelResource, roomMap);
            } else if (selectCheckOutDate.trim().length() > 0) {
                checkOutDate = formatter.parse(selectCheckOutDate);
                printInfo("CheckOut Date: " + checkOutDate);

                Collection<IRoom> availableRoomList = adminResource.availableRooms(checkInDate, checkOutDate);
                if (availableRoomList.isEmpty()) {
                    Collection<IRoom> alternativeRooms = hotelResource.alternativeRooms(checkInDate, checkOutDate);

                    if (alternativeRooms.size() > 0) {
                        final Date alternativeCheckIn = hotelResource.newDate(checkInDate);
                        final Date alternativeCheckOut = hotelResource.newDate(checkOutDate);
                        System.out.println("We've only found rooms on alternative dates:" +
                                "\nCheck-In Date:" + alternativeCheckIn +
                                "\nCheck-Out Date:" + alternativeCheckOut);

                        printInfo(alternativeRooms.toString());
                        bookNewRoom(customer, hotelResource, adminResource, roomMap, alternativeCheckIn, alternativeCheckOut);


                    } else {
                        System.out.println("No rooms found.");
                        }
                } else {
                    printInfo(availableRoomList.toString());
                    for (IRoom room: availableRoomList){
                        roomMap.put(room.getRoomNumber(), room);
                    }
                    bookNewRoom(customer, hotelResource, adminResource, roomMap,  checkInDate, checkOutDate);
                }
            }
        } catch (ParseException e) {
            printInfo("Please enter a valid date in this format mm/dd/yy example 01/15/2020");
            findAndReserve(customer, adminResource, hotelResource, roomMap);
        }
    }

    private static void createAnAccount(HotelResource hotelResource) {
        Scanner userInput = new Scanner(System.in);
        List<Customer> newCustomer = new ArrayList<>();
        Customer customer = new Customer();
        try {
            printInfo("Enter your first name");
            String firstName = userInput.next();
            if (firstName.isEmpty()) {
                printInfo("First name cannot be empty.\n");
                return;
            }
            printInfo("Enter your last name ");
            String lastName = userInput.next();
            if (lastName.isEmpty()) {
                printInfo("Last name cannot be empty.\n");
                return;
            }
            printInfo("Enter your email");
            String email = userInput.next();

            hotelResource.createACustomer(firstName, lastName, email);
            customer.setEmail(email);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            printInfo("Your account with this email : " +email+" was created successfully");
            printInfo("You can now book a new room.");
        } catch (IllegalArgumentException e) {
            printInfo("\nEmail format not valid. Please use a valid email address");
            createAnAccount(hotelResource);
        }

        //Create new Customer Object and add the newly created customers to the list
        Customer newCustomers = new Customer();
        newCustomer.add(newCustomers);

        //Add created customers to resource
        hotelResource.createACustomer(customer.firstName, customer.lastName, customer.email);


    }

    private static void runMenuAgain(HotelResource hotelResource, AdminResource adminResource, HashMap<String, IRoom> roomMap) {
        showMainMenu();
        callTheFunction(hotelResource, adminResource, roomMap);
    }

    private static void bookNewRoom(Customer customer,
                                    HotelResource hotelResource,
                                    AdminResource adminResource,
                                    HashMap<String, IRoom> roomMap,
                                    Date myCheckIn,
                                    Date myCheckOut) {
        Scanner userInput = new Scanner(System.in);
        printInfo("Would you like to book a Room? y/n");
        String books = userInput.next().toUpperCase();
        if (books.equals("Y")) {
            printInfo("Do you have an account with us? y/n");
            String accountHolder = userInput.next().toUpperCase();
            if (accountHolder.equals("Y")) {
                accountHolder(customer, hotelResource, roomMap,  myCheckIn, myCheckOut, adminResource);

            } else if (accountHolder.equals("N")) {
                createNewAccount(customer, hotelResource, adminResource, roomMap, myCheckIn, myCheckOut);
                bookNewRoom(customer, hotelResource, adminResource, roomMap, myCheckIn, myCheckOut);
            }

        } else if (books.equals("N")) {
            runMenuAgain(hotelResource, adminResource, roomMap);
        } else {
            printInfo("Please enter Y(Yes) or N(No)");
        }
    }


    private static void accountHolder(Customer customer,
                                      HotelResource hotelResource,
                                      HashMap<String, IRoom> roomMap,
                                      Date myCheckIn,
                                      Date myCheckOut,
                                      AdminResource adminResource
                                      ) {
        Scanner userInput = new Scanner(System.in);
        printInfo("Enter your email");
        String email = userInput.next();
        customer = AdminResource.getCustomers(email);
        if (customer == null) {
            printInfo("Customer not found");
            printInfo("Please create an account");
            runMenuAgain(hotelResource, adminResource, roomMap);
        } else {
            printInfo("What room would you like to book?");
            printInfo("Please enter room number");
            String roomNumber = userInput.next();
            printInfo("You have selected room " + roomNumber);


            IRoom selectRoom = roomMap.get(roomNumber);
            if (selectRoom != null){
                HotelResource.bookARoom(customer, myCheckIn, myCheckOut, selectRoom);
            }else {
                printInfo("This room does not exist.");
                printInfo("No room is available at this time. Please try again later");
                runMenuAgain(hotelResource, adminResource, roomMap);
            }
        }
    }

    private static void createNewAccount(Customer customer,
                                         HotelResource hotelResource,
                                         AdminResource adminResource,
                                         HashMap<String, IRoom> roomMap,
                                         Date myCheckIn,
                                         Date myCheckOut) {
        createAnAccount(hotelResource);
        bookNewRoom(customer, hotelResource, adminResource, roomMap, myCheckIn, myCheckOut);
    }

    private static void seeCustomerReservation(Scanner userInput, Customer customer){
        printInfo("Please enter your email address");
        String email = userInput.next();
        customer = AdminResource.getCustomers(email);
        if (customer == null){
            printInfo("Customer not found");
            printInfo("Please create an account");
        } else {
            HotelResource.getCustomerReservations(email);
        }
    }

    private static void createDefaultCustomer() {
        for (int x = 0; x < 3; x++) {
            new HotelResource().createACustomer("user" + (x + 1) + "@gmail.com", "user" + (x + 1), "user" + (x + 1));
        }
    }

    private static void createDefaultRoom() {
        for (int x = 0; x < 3; x++) {
            IRoom room = new RoomClass(x + 1 + "", 10.0 * (x + 1), RoomType.SINGLE, null);
            AdminResource.addRoom(List.of(room));
        }
    }

}
