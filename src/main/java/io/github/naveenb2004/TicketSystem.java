package io.github.naveenb2004;

import java.io.IOException;
import java.util.Scanner;

public final class TicketSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TicketSystem ticketSystem = new TicketSystem();
    private static int userThreadStatus;
    private static TicketPool ticketsPool;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("""
                *************************
                ***   Ticket System   ***
                *************************
                
                # Configuration""");

        int totalNumberOfTickets;
        int ticketReleaseRate;
        int ticketRetrievalRate;
        int maximumTicketsCapacity;

        while (true) {
            System.out.print("Enter maximum capacity of the tickets pool : ");
            maximumTicketsCapacity = scanner.nextInt();
            if (maximumTicketsCapacity <= 0) {
                System.out.println("Invalid pool capacity");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Enter total number of tickets in the pool : ");
            totalNumberOfTickets = scanner.nextInt();
            if (totalNumberOfTickets < 0 || maximumTicketsCapacity < totalNumberOfTickets) {
                System.out.println("Invalid tickets count");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Enter ticket release rate of the pool : ");
            ticketReleaseRate = scanner.nextInt();
            if (ticketReleaseRate <= 0) {
                System.out.println("Invalid ticket release rate");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Enter ticket retrieval rate of the pool : ");
            ticketRetrievalRate = scanner.nextInt();
            if (ticketRetrievalRate <= 0) {
                System.out.println("Invalid ticket retrieval rate");
                continue;
            }
            break;
        }

        Configuration configuration = new Configuration(totalNumberOfTickets, ticketReleaseRate,
                ticketRetrievalRate, maximumTicketsCapacity);
        ticketsPool = new TicketPool(configuration);

        while (true) {
            System.out.print("""
                    \n# Main Menu
                    1 - Save Configuration
                    2 - Add Vendors
                    3 - Add Customers
                    4 - Simulate System
                    5 - Show Tickets Pool status
                    0 - Exit
                    >\s""");
            char option = scanner.next().charAt(0);
            switch (option) {
                case '1' -> configuration.save();
                case '2' -> {
                    while (true) {
                        System.out.print("""
                                \n# Add Vendors
                                Enter the count of vendors to be added :\s""");
                        int vendorCount = scanner.nextInt();
                        if (vendorCount <= 0) {
                            System.out.println("Invalid vendors count");
                            continue;
                        }
                        Vendor.addVendors(vendorCount);
                        System.out.println("Success!");
                        break;
                    }
                }
                case '3' -> {
                    while (true) {
                        System.out.print("""
                                \n# Add Customers
                                Enter the count of customers to be added :\s""");
                        int customerCount = scanner.nextInt();
                        if (customerCount <= 0) {
                            System.out.println("Invalid customers count");
                            continue;
                        }
                        Customer.addCustomers(customerCount);
                        System.out.println("Success!");
                        break;
                    }
                }
                case '4' -> ticketSystem.simulateSystem();
                case '5' -> showTicketPoolStatus();
                case '0' -> System.exit(0);
            }
        }
    }

    private synchronized void simulateSystem() throws InterruptedException {
        Thread vendorThread = Thread.ofVirtual().start(() -> {
            for (Vendor vendor : Vendor.getVendors()) {
                Thread.ofVirtual().start(vendor);
            }
        });

        Thread customerThread = Thread.ofVirtual().start(() -> {
            for (Customer customer : Customer.getCustomers()) {
                Thread.ofVirtual().start(customer);
            }
        });

        vendorThread.join();
        customerThread.join();

        wait();
    }

    private static void showTicketPoolStatus() {
        System.out.printf("""
                        # Tickets Pool Status
                        Total number of tickets :\t%s
                        Tickets release rate :\t%s
                        Tickets retrieval rate :\t%s
                        Maximum tickets capacity :\t%s
                        """,
                ticketsPool.getTotalNumberOfTickets(),
                ticketsPool.getTicketReleaseRate(),
                ticketsPool.getTicketRetrievalRate(),
                ticketsPool.getMaximumTicketsCapacity());
    }

    public static TicketSystem getTicketSystem() {
        return ticketSystem;
    }

    public static TicketPool getTicketsPool() {
        return ticketsPool;
    }

    public synchronized void resumeMainThread() {
        userThreadStatus++;
        if (userThreadStatus == (Vendor.getVendors().size() + Customer.getCustomers().size())) {
            notify();
        }
    }
}
