package model;

public interface IRoom {
    //Abstract Class cannot Instantiate variables

    //Declaring the methods

    String getRoomNumber();
    void setRoomNumber(String roomNumber);
    Double getRoomPrice();
    RoomType getRoomType();
    boolean isFree();
    void setReservation(Reservation reservation);
    Reservation getReservation();
    boolean isReserved();
}
