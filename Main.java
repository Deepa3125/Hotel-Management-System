package com.HotelManagementSystem;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Hotel hotel = new Hotel();
    static Scanner scanner = new Scanner(System.in);
    static int nextGuestId = 1;

    public static void main(String[] args) {
        // Pre-load a few rooms so there's data to test with
        hotel.addRoom(new Room(101, "SINGLE", 1500));
        hotel.addRoom(new Room(102, "DOUBLE", 2500));
        hotel.addRoom(new Room(201, "SUITE", 5000));

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewAllRooms();
                    break;
                case "2":
                    searchAvailableRooms();
                    break;
                case "3":
                    bookRoom();
                    break;
                case "4":
                    cancelBooking();
                    break;
                case "5":
                    checkOut();
                    break;
                case "6":
                    viewAllBookings();
                    break;
                case "7":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void printMenu() {
        System.out.println("\n===== HOTEL MANAGEMENT SYSTEM =====");
        System.out.println("1. View All Rooms");
        System.out.println("2. Search Available Rooms");
        System.out.println("3. Book a Room");
        System.out.println("4. Cancel a Booking");
        System.out.println("5. Check-out & Generate Bill");
        System.out.println("6. View All Bookings");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");
    }

    static void viewAllRooms() {
        System.out.println("\n--- All Rooms ---");
        for (Room room : hotel.getAllRooms()) {
            System.out.println(room);
        }
    }

    static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
    }

    static void searchAvailableRooms() {
        LocalDate checkIn = readDate("Check-in date");
        LocalDate checkOut = readDate("Check-out date");

        try {
            List<Room> available = hotel.getAvailableRooms(checkIn, checkOut);
            System.out.println("\n--- Available Rooms ---");
            if (available.isEmpty()) {
                System.out.println("No rooms available for those dates.");
            } else {
                for (Room room : available) {
                    System.out.println(room);
                }
            }
        } catch (InvalidDateRangeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void bookRoom() {
        System.out.print("Enter guest name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        Guest guest = new Guest(nextGuestId++, name, phone, email);

        System.out.print("Enter room number: ");
        int roomNumber = Integer.parseInt(scanner.nextLine().trim());

        LocalDate checkIn = readDate("Check-in date");
        LocalDate checkOut = readDate("Check-out date");

        try {
            Booking booking = hotel.bookRoom(guest, roomNumber, checkIn, checkOut);
            System.out.println("Booking confirmed: " + booking);
        } catch (RoomNotAvailableException | InvalidDateRangeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void cancelBooking() {
        System.out.print("Enter booking ID to cancel: ");
        int bookingId = Integer.parseInt(scanner.nextLine().trim());

        try {
            hotel.cancelBooking(bookingId);
            System.out.println("Booking " + bookingId + " cancelled.");
        } catch (BookingNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void checkOut() {
        System.out.print("Enter booking ID to check out: ");
        int bookingId = Integer.parseInt(scanner.nextLine().trim());

        try {
            Booking booking = hotel.checkOut(bookingId);
            System.out.println("\n--- INVOICE ---");
            System.out.println(booking);
            System.out.println("Thank you for staying with us, " + booking.getGuest().getName() + "!");
        } catch (BookingNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewAllBookings() {
        System.out.println("\n--- All Bookings ---");
        List<Booking> bookings = hotel.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No active bookings.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }
}