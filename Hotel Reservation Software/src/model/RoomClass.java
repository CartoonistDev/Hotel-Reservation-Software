package model;

import java.util.Objects;

public class RoomClass implements IRoom {
    private String roomNumber;
    private Double roomPrice;
    private RoomType roomType;
    Reservation reservation;


    public RoomClass(String roomNumber, Double roomPrice, RoomType roomType, Reservation reservation){
        this.roomNumber = roomNumber;
        this.roomPrice =  roomPrice;
        this.roomType = roomType;
        this.reservation = reservation;
    }

    @Override
    public String getRoomNumber(){
        return roomNumber;
    }

    @Override
    public void setRoomNumber(String roomNumber) {
       this.roomNumber = roomNumber;
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
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public Reservation getReservation() {
        return reservation;
    }

    @Override
    public boolean isReserved() {
        return reservation != null;
    }

    @Override
    public String toString(){
        return "Room Number: " + roomNumber + '\n' +
                "Room Type: " + roomType + '\n' +
                "Room Price: " + roomPrice + '\n';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof RoomClass)) {
            return false;
        }

        final RoomClass room = (RoomClass) obj;
        return Objects.equals(this.roomNumber, room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
