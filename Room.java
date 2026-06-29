package com.HotelManagementSystem;


public class Room {
    private int roomNumber;
    private String roomType;   // SINGLE, DOUBLE, SUITE
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room #" + roomNumber + " [" + roomType + "] - Rs." + pricePerNight + "/night - " +
                (isAvailable ? "Available" : "Booked");
    }
}