package model;

public interface IRoom {
    //Abstract Class cannot Instantiate variables

    //Declaring the methods

    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();

}
