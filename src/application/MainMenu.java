package application;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.RoomClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import static api.HotelResource.hotelResource;

public class MainMenu {

    public static void main(String[] args){

        //Get user input using the scanner object
        Scanner userInput = new Scanner(System.in);

        //Getting an instance of the hotelResources class
        HotelResource hotelResource = new HotelResource();
        AdminResource adminResource = new AdminResource();
        Customer customer = new Customer();

        showMainMenu();

        int selectedInput = userInput.nextInt();
        Scanner newSelectedValued = new Scanner(System.in);
        callTheFunction(selectedInput, userInput, newSelectedValued, hotelResource, adminResource);
    }

    public static void callTheFunction(int selectedInput, Scanner userInput, Scanner newSelectedValued, HotelResource hotelResource, AdminResource adminResource) {
        Customer customer = new Customer();
        if (selectedInput == 1){
            findAndReserve(newSelectedValued, customer, adminResource, hotelResource, userInput);
            runMenuAgain(newSelectedValued, userInput, hotelResource, adminResource);
        } else if(selectedInput == 2){
            Collection<Customer> customerList2 = adminResource.getAllCustomers();
            for (Customer customer1 : customerList2) {
                printInfo(customer1.toString());
            }
            runMenuAgain(newSelectedValued, userInput, hotelResource, adminResource);
        } else if (selectedInput == 3){
            createAnAccount(userInput, newSelectedValued, customer, hotelResource, adminResource);
            runMenuAgain(newSelectedValued, userInput, hotelResource, adminResource);
        } else if(selectedInput == 4){
            AdminMenu.main(null);
        } else if(selectedInput == 5){
            return;
        } else {

            //default for catching errors
            printInfo("Error, enter a valid input");
            runMenuAgain(newSelectedValued, userInput, hotelResource, adminResource);
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

    private static void findAndReserve(Scanner newSelectedValued, Customer customer, AdminResource adminResource, HotelResource hotelResource, Scanner userInput){
        //Declaring variables
        Date checkInDate;
        Date checkOutDate;
        String roomNumber = "";

        try {
            Scanner inputDate = new Scanner(System.in);
            SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yy");
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
                findAndReserve(newSelectedValued, customer, adminResource, hotelResource, userInput);
            } else if (selectCheckOutDate.trim().length() > 0){
                checkOutDate = formatter.parse(selectCheckOutDate);
                printInfo("CheckIn Date: " + checkOutDate);
                AdminMenu.seeAllRooms(adminResource);
            }
        } catch (ParseException e){
            printInfo("Please enter a valid date in this format mm/dd/yy example 01/15/2020");
            findAndReserve(newSelectedValued, customer, adminResource, hotelResource, userInput);
        }
        bookNewRoom(userInput, newSelectedValued, customer, hotelResource, adminResource);
    }

    private static void createAnAccount(Scanner userInput, Scanner newSelectedValued, Customer customer, HotelResource hotelResource, AdminResource adminResource){
        printInfo("Enter your email");
        String email = userInput.next();
        printInfo("Enter your first name");
        String firstName = userInput.next();
        printInfo("Enter your last name ");
        String lastName = userInput.next();
        hotelResource.createACustomer(email, firstName, lastName);
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        printInfo(email + " was created successfully");
    }

    private static void runMenuAgain(Scanner newSelectedValued, Scanner userInput, HotelResource hotelResource, AdminResource adminResource){
        showMainMenu();
        int newSelectedInput =  userInput.nextInt();
        callTheFunction(newSelectedInput, userInput, newSelectedValued, hotelResource, adminResource);
    }

    private static void bookNewRoom(Scanner newSelectedValued, Scanner userInput, Customer customer, HotelResource hotelResource, AdminResource adminResource){
        //Declaring variables
        String bookRoom = "";
        printInfo("Would you like to book a Room? y/n");
        String books = userInput.next().toUpperCase();
        if (books.equals("Y")){
            printInfo("Do you have an account with us?");
            String accountHolder = userInput.next().toUpperCase();
            if (accountHolder.equals("Y")){
                printInfo("Enter your email");
                String email = userInput.next();
                if(email == customer.getEmail()){
                    printInfo("What room would you like to book?");
                    printInfo("Please enter room number");
                    String roomNumber = userInput.next();
                    for (IRoom room : roomNumber){

                    }
                }
                /////printInfo("-----------------------------------------------");

            } else if (accountHolder.equals("N")){
                createAnAccount(userInput, newSelectedValued, customer, hotelResource, adminResource);
                bookNewRoom(userInput, newSelectedValued, customer, hotelResource, adminResource);
            }

        } else if (books.equals("N")){
            runMenuAgain(newSelectedValued, userInput, hotelResource, adminResource);
        } else {
            printInfo("Please enter Y(Yes) or N(No)");
        }
    }
}
