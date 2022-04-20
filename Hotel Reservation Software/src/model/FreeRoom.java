package model;

public class FreeRoom extends RoomClass {

    //Exdend the RoomClass with its defined variable nto change the price

    public FreeRoom(String roomNumber, Double price, RoomType roomType){
        super("0", 0.0, RoomType.SINGLE, null);
    }

    @Override
    public String toString(){
        return "Room Number: " + getRoomNumber() + '\'' +
                "Room Type: " + getRoomType() + '\'' +
                "Room Price: " + 0.0;
    }

}
