package model;

public class RoomClass implements IRoom {
    final String roomNumber;
    final Double roomPrice;
    final RoomType roomType;

    public RoomClass(String roomNumber, Double roomPrice, RoomType roomType){
        super();
        this.roomNumber = roomNumber;
        this.roomPrice =  roomPrice;
        this.roomType = roomType;
    }


    @Override
    public String getRoomNumber(){
        return roomNumber;
    }

    @Override
    public Double getRoomPrice(){
        return  roomPrice;
    }

    @Override
    public RoomType getRoomType(){
        return roomType;
    }

    @Override
    public boolean isFree(){
        return  roomPrice == 0.0;
    }

    @Override
    public String toString(){
        return "Room Number: " + roomNumber + '\n' +
                "Room Type: " + roomType + '\n' +
                "Room Price: " +  roomPrice;
    }
}
