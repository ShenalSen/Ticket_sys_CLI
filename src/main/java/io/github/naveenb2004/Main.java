package io.github.naveenb2004;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("""
                *****************************
                ***   Ticket System CLI   ***
                *****************************
                """);

        while (true) {
            System.out.print("""
                    Please select an option to continue :
                    1 - Vendor Operations
                    2 - Customer Operations
                    3 - Simulate
                    0 - Exit
                    >\s""");

            char option = scanner.nextLine().charAt(0);
            switch (option) {
                case '1' -> Vendor.vendorOperations(scanner);
                case '2' -> Customer.customerOperations(scanner);
                case '3' -> simulate();
                case '0' -> System.exit(0);
                default -> System.out.println("Invalid option\n");
            }
        }
    }

    private static void simulate() throws InterruptedException {
        System.out.println("Adding vendors & customers...");
        Thread thread0 = Thread.ofVirtual().start(() -> {
            for (int i = 0; i < 5; i++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Thread ").append(Thread.currentThread().threadId()).append(":");
                long vendorId = Vendor.getGlobalVendorId();
                Configuration.addVendor(new Vendor(vendorId, 2));
                stringBuilder.append("\tAdded vendor : ").append(vendorId);
                for (int j = 0; j < 5; j++) {
                    long poolId = TicketPool.getGlobalPoolId();
                    Configuration.addPool(new TicketPool(poolId, vendorId, 10, 50));
                    stringBuilder.append("\tAdded pool : ").append(poolId);
                }
                long customerId = Customer.getGlobalCustomerId();
                Configuration.addCustomer(new Customer(customerId, 3));
                stringBuilder.append("\tAdded customer : ").append(customerId);
                System.out.println(stringBuilder);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread1 = Thread.ofVirtual().start(() -> {
            for (int i = 0; i < 5; i++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Thread ").append(Thread.currentThread().threadId()).append(":");
                long vendorId = Vendor.getGlobalVendorId();
                Configuration.addVendor(new Vendor(vendorId, 3));
                stringBuilder.append("\tAdded vendor : ").append(vendorId);
                for (int j = 0; j < 5; j++) {
                    long poolId = TicketPool.getGlobalPoolId();
                    Configuration.addPool(new TicketPool(poolId, vendorId, 10, 50));
                    stringBuilder.append("\tAdded pool : ").append(poolId);
                }
                long customerId = Customer.getGlobalCustomerId();
                Configuration.addCustomer(new Customer(customerId, 2));
                stringBuilder.append("\tAdded customer : ").append(customerId);
                System.out.println(stringBuilder);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread0.join();
        thread1.join();
        System.out.println("Added vendors & customers!\n");

        System.out.println("Beginning transaction...");
        thread0 = Thread.ofVirtual().start(() -> {
            for (Vendor vendor : Configuration.getVendors()) {
                Thread.ofVirtual().start(vendor);
            }
        });

        thread1 = Thread.ofVirtual().start(() -> {
            for (Customer customer : Configuration.getCustomers()) {
                Thread.ofVirtual().start(customer);
            }
        });

        thread0.join();
        thread1.join();
        System.out.println("Transaction completed!");
    }
}