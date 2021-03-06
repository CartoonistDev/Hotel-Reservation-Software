package application;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.*;

public class AdminMenu {

    public static void main(String[] args){

        //Getting an instance of the hotelResources and adminResources class
        HotelResource hotelResource = new HotelResource();
        AdminResource adminResource = new AdminResource();

        //Get user input using the scanner object

        showAdminMenu();
        Scanner userInput = new Scanner(System.in);

        adminFunction(userInput, hotelResource, adminResource);
    }

    public static void adminFunction(Scanner userInput, HotelResource hotelResource, AdminResource adminResource) {
        try {
            int selectedValue = Integer.parseInt(userInput.next());
            if (selectedValue == 1){
                Collection<Customer> customers = adminResource.getAllCustomers();
                for (Customer customerList: customers){
                    System.out.println(customerList.toString());
                }
                runAdminAgain(userInput, hotelResource, adminResource);
            }
            else if (selectedValue == 2){
                seeAllRooms(adminResource);
                runAdminAgain(userInput, hotelResource, adminResource);
            } else if (selectedValue == 3){
                adminResource.displayAllReservations();
                runAdminAgain(userInput, hotelResource, adminResource);
            }
            else if (selectedValue == 4){
                createRoom(userInput, adminResource, hotelResource);
                runAdminAgain(userInput, hotelResource, adminResource);
            }
            else if (selectedValue == 5){
                //exit
                MainMenu.main(null);
            }
        } catch (NumberFormatException e){
            printInfo("Error, enter a valid input");
            showAdminMenu();
            adminFunction(userInput, hotelResource, adminResource);
        }
    }

    private static void showAdminMenu() {
        printInfo("---------------------------------------");
        printInfo("welcome to hotel admin");
        printInfo("select an option to proceed");
        printInfo("1. See all customer");
        printInfo("2. See all Rooms");
        printInfo("3. See all Reservations");
        printInfo("4. Add a Room");
        printInfo("5. Exit");
        printInfo("---------------------------------------");
    }

    private static void printInfo(String message) {
        System.out.println(message);
    }

    private static void createRoom(Scanner userInput, AdminResource adminResource, HotelResource hotelResource) {
        List<RoomClass> newRooms = new ArrayList<>();

        //Declaring variables
        String roomNumber;
        Double roomPrice;
        RoomType roomType = null;

        printInfo("What type of Room would you like to create.");
        printInfo("Enter 1 for Single");
        printInfo("Enter 2 for Double");
        try{
            //Convert entered String value to an Integer
            int selectedRoomType = Integer.parseInt(userInput.next());
            if (selectedRoomType == 1){
                roomType = RoomType.SINGLE;
            } else if(selectedRoomType == 2){
                roomType = RoomType.DOUBLE;
            } else {
                printInfo("Invalid input. Try 1 or 2.");
                createRoom(userInput, adminResource, hotelResource);
            }
        } catch (NumberFormatException e){
            printInfo("Invalid input. Try 1 or 2.");
            createRoom(userInput, adminResource, hotelResource);
        }

        //Run this functions
        roomPrice = createRoomPrice(userInput);
        roomNumber = assignRoomNumber(userInput, adminResource, hotelResource);

        //Create new room object
        if (!roomNumber.equals("")){
            RoomClass room = new RoomClass(roomNumber, roomPrice, roomType);
            newRooms.add(room);
            printInfo("A new room has been created\n" + newRooms);

            //add created rooms to the resource
            adminResource.addRoom(newRooms);
        }

        createNewRoom(userInput, adminResource, hotelResource);

    }

    private static void createNewRoom(Scanner userInput, AdminResource adminResource, HotelResource hotelResource){
        printInfo("---------------------------------------");
        printInfo("Do you wish to add another room (Y/N)?");
        String continueFlag = userInput.next().toUpperCase();
        if (continueFlag.equals("N")) {
            runAdminAgain(userInput, hotelResource, adminResource);
        } else if (continueFlag.equals("Y")) {
            createRoom(userInput, adminResource, hotelResource);
        } else {
            printInfo("Please enter Y(Yes) or N(No)");
            createNewRoom(userInput, adminResource, hotelResource);
        }
    }

    private static String assignRoomNumber(Scanner userInput, AdminResource adminResource, HotelResource hotelResource){
        //Assign a value to roomNumber
        String roomNumber = "";
       try {
           printInfo("Enter a Room Number");

           //Convert a numeric String to an Integer
           String adminRoomNumber = String.valueOf(Integer.parseInt(userInput.next()));

           Collection<RoomClass> allRooms = adminResource.getAllRooms();
           for (IRoom room: allRooms){

               //If new room number entered equals a previously picked number throw that
               if (room.getRoomNumber().equals(adminRoomNumber)){
                   printInfo("This room already exist.");
                   return "";
               }
           }
           //Assign the new room number to roomNumber
           roomNumber = adminRoomNumber;

           //If a different character that is not numeric is entered throw this NFE catch
       } catch (NumberFormatException e){
           printInfo("Invalid input. Only numbers greater than 0.");
           createNewRoom(userInput, adminResource, hotelResource);
       }
        return roomNumber;
    }

    private static Double createRoomPrice(Scanner userInput){
        Double roomPrice = 0.0;
        try {
            printInfo("How much would this room cost. Values must be numbers greater than $0");

            //Convert a numeric String to a Double
            Double adminRoomPrice = Double.parseDouble(userInput.next());

            if(adminRoomPrice>0.0){
                roomPrice = adminRoomPrice;
            } else {
                printInfo("Enter a valid price. Values must be numbers greater than $0");
                createRoomPrice(userInput);
            }
        } catch (Exception e){
           printInfo("Invalid price format. Please put in a number greater than 0");
            createRoomPrice(userInput);
        }

        return roomPrice;
    }

    public static void seeAllRooms(AdminResource adminResource){
        Collection<RoomClass> rooms =  adminResource.getAllRooms();
        for (IRoom roomList: rooms){
            printInfo(roomList.toString());
        }
    }

    private static void runAdminAgain(Scanner userInput, HotelResource hotelResource, AdminResource adminResource){
        showAdminMenu();
        adminFunction(userInput, hotelResource, adminResource);
    }

}

