package com.HotelManagementSystem;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {
    private int bookingId;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalAmount;

    public Booking(int bookingId, Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = calculateTotal();
    }

    public double calculateTotal() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return nights * room.getPricePerNight();
    }

    public int getBookingId() {
        return bookingId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Booking #" + bookingId + " | " + guest.getName() + " | Room " + room.getRoomNumber() +
                " | " + checkInDate + " to " + checkOutDate + " | Total: Rs." + totalAmount;
    }
}