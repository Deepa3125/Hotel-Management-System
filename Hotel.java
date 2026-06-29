package com.HotelManagementSystem;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private int nextBookingId = 1;

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }

    // Checks if two date ranges overlap
    private boolean isOverlapping(LocalDate existingIn, LocalDate existingOut,
                                   LocalDate newIn, LocalDate newOut) {
        return newIn.isBefore(existingOut) && newOut.isAfter(existingIn);
    }

    // Returns rooms that are free for the given date range
    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) throws InvalidDateRangeException {
        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidDateRangeException("Check-out date must be after check-in date.");
        }

        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            boolean isFree = true;

            for (Booking booking : bookings) {
                if (booking.getRoom().getRoomNumber() == room.getRoomNumber()
                        && isOverlapping(booking.getCheckInDate(), booking.getCheckOutDate(), checkIn, checkOut)) {
                    isFree = false;
                    break;
                }
            }

            if (isFree) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    public Booking bookRoom(Guest guest, int roomNumber, LocalDate checkIn, LocalDate checkOut)
            throws RoomNotAvailableException, InvalidDateRangeException {

        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidDateRangeException("Check-out date must be after check-in date.");
        }

        Room targetRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                targetRoom = room;
                break;
            }
        }

        if (targetRoom == null) {
            throw new RoomNotAvailableException("Room " + roomNumber + " does not exist.");
        }

        // Check overlap against existing bookings for this room
        for (Booking booking : bookings) {
            if (booking.getRoom().getRoomNumber() == roomNumber
                    && isOverlapping(booking.getCheckInDate(), booking.getCheckOutDate(), checkIn, checkOut)) {
                throw new RoomNotAvailableException("Room " + roomNumber + " is already booked for those dates.");
            }
        }

        Booking newBooking = new Booking(nextBookingId++, guest, targetRoom, checkIn, checkOut);
        bookings.add(newBooking);
        return newBooking;
    }

    public void cancelBooking(int bookingId) throws BookingNotFoundException {
        Booking toRemove = findBookingById(bookingId);
        bookings.remove(toRemove);
    }

    public Booking checkOut(int bookingId) throws BookingNotFoundException {
        Booking booking = findBookingById(bookingId);
        bookings.remove(booking);
        return booking; // caller can print this as the final invoice
    }

    private Booking findBookingById(int bookingId) throws BookingNotFoundException {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }
        throw new BookingNotFoundException("No booking found with ID " + bookingId);
    }
}