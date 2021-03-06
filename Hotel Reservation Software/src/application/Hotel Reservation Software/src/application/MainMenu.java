package application;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.RoomClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    public static void main(String[] args){

        //Get user input using the scanner object


        //Getting an instance of the hotelResources class
        HotelResource hotelResource = new HotelResource();
        AdminResource adminResource = new AdminResource();
        Date myCheckIn = null;
        Date myCheckOut = null;
        HashMap<String, RoomClass> roomMap = new HashMap<>();

        showMainMenu();
        Scanner userInput = new Scanner(System.in);

        callTheFunction(userInput,  hotelResource, adminResource, roomMap, myCheckIn, myCheckOut);
    }

    public static void callTheFunction(Scanner userInput,
                                       HotelResource hotelResource,
                                       AdminResource adminResource,
                                       HashMap<String, RoomClass> roomMap,
                                       Date myCheckIn,
                                       Date myCheckOut) {
        try {
           //String selectedInput = userInput.next();
            int selectedInput = Integer.parseInt(userInput.next());
            Customer customer = new Customer();
            if (selectedInput == 1){
                //findAndReserveRoom(adminResource);
                findAndReserve(customer, adminResource, hotelResource, userInput, roomMap, myCheckIn, myCheckOut);
                runMenuAgain(userInput, hotelResource, adminResource,roomMap, myCheckIn, myCheckOut);
            } else if(selectedInput == 2){
                HotelResource.getCustomerReservations(customer);
                runMenuAgain( userInput, hotelResource, adminResource,roomMap, myCheckIn, myCheckOut);
            } else if (selectedInput == 3){
                createAnAccount(userInput, customer, hotelResource);
                runMenuAgain( userInput, hotelResource, adminResource,roomMap, myCheckIn, myCheckOut);
            } else if(selectedInput == 4){
                AdminMenu.main(null);
            } else if(selectedInput == 5){
                printInfo("BYE");
            }
        } catch (NumberFormatException e){
            printInfo("Error, enter a valid input");
            showMainMenu();
            callTheFunction(userInput,  hotelResource, adminResource, roomMap, myCheckIn, myCheckOut);
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

    private static void findAndReserve(Customer customer,
                                       AdminResource adminResource,
                                       HotelResource hotelResource,
                                       Scanner userInput,
                                       HashMap<String, RoomClass> roomMap,
                                       Date myCheckIn,
                                       Date myCheckOut){
        //Declaring variables
        Date checkInDate = null;
        Date checkOutDate = null;

        try {
            Scanner inputDate = new Scanner(System.in);
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            printInfo("Enter CheckIn Date mm/dd/yy example 01/15/2020");
            String selectCheckInDate = inputDate.nextLine();
            if (selectCheckInDate.trim().length() > 0){
                checkInDate = formatter.parse(selectCheckInDate);
                printInfo("CheckIn Date: " + checkInDate);
            }
            printInfo("Enter CheckOut Date mm/dd/yy example 01/21/2020");
            String selectCheckOutDate = inputDate.nextLine();

            //Throw this error if CheckOut Date entered comes before CheckIn Date entered
            if (selectCheckOutDate.compareTo(selectCheckInDate) == -1){
                printInfo("Please enter a valid date that comes after CheckIn Date");
                findAndReserve(customer, adminResource, hotelResource, userInput, roomMap, myCheckIn, myCheckOut);
            } else if (selectCheckOutDate.trim().length() > 0){
                checkOutDate = formatter.parse(selectCheckOutDate);
                printInfo("CheckOut Date: " + checkOutDate);

                Collection<IRoom> availableRoomList = adminResource.availableRooms(checkInDate, checkOutDate);
                for (IRoom room : availableRoomList){
                    if (availableRoomList.isEmpty()) {
                        printInfo("No room is available at this time. Please try again later.");
                        showMainMenu();
                    } else if (!availableRoomList.isEmpty()){
                        printInfo(room.toString());
                        roomMap.putIfAbsent(room.getRoomNumber(), (RoomClass) room);
                        bookNewRoom(userInput, customer, hotelResource, adminResource,roomMap, myCheckIn, myCheckOut);
                    }
                }
            }
        } catch (ParseException e){
            printInfo("Please enter a valid date in this format mm/dd/yy example 01/15/2020");
            findAndReserve(customer, adminResource, hotelResource, userInput, roomMap, myCheckIn, myCheckOut);
        }
        //bookNewRoom(userInput, newSelectedValued, customer, hotelResource, adminResource);
    }

    private static void createAnAccount(Scanner userInput,
                                        Customer customer,
                                        HotelResource hotelResource
                                      ){
        List<Customer> newCustomer = new ArrayList<>();
        try {
            printInfo("Enter your email");
            String email = userInput.next();
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

            hotelResource.createACustomer(email, firstName, lastName);
            customer.setEmail(email);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            printInfo(email + " was created successfully");
        } catch (IllegalArgumentException e) {
            printInfo("\nEmail format not valid. Please use a valid email address");
            createAnAccount(userInput, customer, hotelResource);
        }

        //Create new Customer Object and add the newly created customers to the list
        Customer newCustomers = new Customer();
        newCustomer.add(newCustomers);

        //Add created customers to resource
        hotelResource.addNewCustomer(customer.email, customer.firstName, customer.lastName);


    }

    private static void runMenuAgain(Scanner userInput,
                                     HotelResource hotelResource,
                                     AdminResource adminResource,
                                     HashMap<String, RoomClass> roomMap,
                                     Date myCheckIn,
                                     Date myCheckOut){
        showMainMenu();
        int newSelectedInput =  userInput.nextInt();
        callTheFunction(userInput, hotelResource, adminResource, roomMap, myCheckIn, myCheckOut);
    }

    private static void bookNewRoom(
            Scanner userInput,
            Customer customer,
            HotelResource hotelResource,
            AdminResource adminResource,
            HashMap<String, RoomClass> roomMap,
            Date myCheckIn,
            Date myCheckOut
    ){
        printInfo("Would you like to book a Room? y/n");
        String books = userInput.next().toUpperCase();
        if (books.equals("Y")){
            printInfo("Do you have an account with us? y/n");
            String accountHolder = userInput.next().toUpperCase();
            if (accountHolder.equals("Y")){
                accountHolder(userInput, customer, hotelResource, adminResource, roomMap, myCheckIn, myCheckOut);

            } else if (accountHolder.equals("N")){
                createNewAccount(userInput, customer, hotelResource, adminResource, roomMap, myCheckIn, myCheckOut);
                bookNewRoom(userInput, customer, hotelResource, adminResource,roomMap, myCheckIn, myCheckOut);
            }

        } else if (books.equals("N")){
            runMenuAgain(userInput, hotelResource, adminResource,roomMap, myCheckIn, myCheckOut);
        } else {
            printInfo("Please enter Y(Yes) or N(No)");
        }
    }

    private static void accountHolder(Scanner userInput,
                                       Customer customer,
                                       HotelResource hotelResource,
                                       AdminResource adminResource,
                                       HashMap<String, RoomClass> roomMap,
                                       Date myCheckIn,
                                       Date myCheckOut){
        printInfo("Enter your email");
        String email = userInput.next();
        if(adminResource.getCustomers(email) == null){
            printInfo("Customer not found");
            printInfo("Please create an account");
            createAnAccount(userInput, customer, hotelResource);
        }
        else {
            printInfo("What room would you like to book?");
            printInfo("Please enter room number");
            String roomNumber = userInput.next();
            RoomClass selectRoom = roomMap.get(roomNumber);
            hotelResource.bookARoom(customer, selectRoom, myCheckIn, myCheckOut);
        }

    }

    private static void createNewAccount(
            Scanner userInput,
            Customer customer,
            HotelResource hotelResource,
            AdminResource adminResource,
            HashMap<String, RoomClass> roomMap,
            Date myCheckIn,
            Date myCheckOut
    ){
        createAnAccount(userInput, customer, hotelResource);
        bookNewRoom(userInput, customer, hotelResource, adminResource,roomMap, myCheckIn, myCheckOut);
    }


}
