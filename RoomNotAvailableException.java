package com.HotelManagementSystem;

public class RoomNotAvailableException extends Exception {
    public RoomNotAvailableException(String message) {
        super(message);
    }
}