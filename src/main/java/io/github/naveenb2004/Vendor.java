package io.github.naveenb2004;

import java.util.Scanner;

public final class Vendor implements Runnable {
    private static long globalVendorId = 1L;

    private final long vendorId;
    private final int releaseInterval;

    public Vendor(long vendorId,
                  int releaseInterval) {
        this.vendorId = vendorId;
        this.releaseInterval = releaseInterval;
    }

    public long getVendorId() {
        return vendorId;
    }

    public int getReleaseInterval() {
        return releaseInterval;
    }

    public static synchronized long getGlobalVendorId() {
        return globalVendorId++;
    }

    @Override
    public void run() {
        for (TicketPool pool : Configuration.getPools()) {
            if (pool.getVendorId() == vendorId) {
                try {
                    pool.putTicketsToThePool(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void vendorOperations(Scanner scanner) {
        while (true) {
            System.out.println("""
                    \n# Vendor Operations
                    1 - Add vendor
                    2 - Remove vendor
                    3 - Manage ticket pool
                    0 - Back
                    >\s""");

            char option = scanner.next().charAt(0);
            switch (option) {
                case '1' -> addVendor(scanner);
                case '2' -> removeVendor(scanner);
                case '3' -> {
                    long vendorId;
                    while (true) {
                        System.out.println("""
                                \n# Manage ticket pool
                                Registered vendors :
                                """);
                        for (Vendor vendor : Configuration.getVendors()) {
                            System.out.println(vendor.getVendorId());
                        }
                        System.out.print("\nEnter vendor id to manage pools : ");
                        vendorId = scanner.nextLong();
                        if (!Configuration.vendorExists(vendorId)) {
                            System.out.println("Vendor id not found");
                            continue;
                        }

                        System.out.println("\nTicket pools for vendor " + vendorId + " :");
                        System.out.println("Pool Id\tTotal Tickets\tMaximum Capacity");
                        for (TicketPool pool : Configuration.getPools()) {
                            if (pool.getVendorId() == vendorId) {
                                System.out.println(pool.getPoolId() + "\t" + pool.getTotalNumberOfTickets()
                                        + "\t" + pool.getMaximumTicketCapacity());
                            }
                        }
                        break;
                    }

                    System.out.println("""
                            \n1 - Add new pool
                            2 - Remove existing pool
                            >\s""");
                    char manageOption = scanner.next().charAt(0);
                    switch (manageOption) {
                        case '1' -> {
                            while (true) {
                                System.out.print("Enter maximum tickets capacity : ");
                                int maxCap = scanner.nextInt();
                                if (maxCap < 0) {
                                    System.out.println("Invalid ticket capacity");
                                    continue;
                                }

                                System.out.println("Enter total number of tickets : ");
                                int totalTickets = scanner.nextInt();
                                if (totalTickets < 0 || totalTickets > maxCap) {
                                    System.out.println("Invalid total number of tickets");
                                    continue;
                                }

                                Configuration.addPool(
                                        new TicketPool(TicketPool.getGlobalPoolId(), vendorId, totalTickets, maxCap));
                                System.out.println("Success!");
                                break;
                            }
                        }
                        case '2' -> {
                            while (true) {
                                System.out.print("Enter ticket pool id to remove : ");
                                long poolId = scanner.nextLong();
                                if (!Configuration.poolExists(poolId)) {
                                    System.out.println("Pool id not found");
                                    continue;
                                }

                                Configuration.removePool(poolId);
                                System.out.println("Success!");
                                break;
                            }
                        }
                    }
                }
                case '0' -> {
                    return;
                }
            }
        }
    }

    public static void addVendor(Scanner scanner) {
        System.out.println("""
                \n# Add vendor
                Enter ticket release interval :\s""");
        int interval = scanner.nextInt();
        if (interval < 0) {
            System.out.println("Invalid ticket release interval");
            return;
        }

        Configuration.addVendor(new Vendor(globalVendorId++, interval));
        System.out.println("Success!");
    }

    public static void removeVendor(Scanner scanner) {
        System.out.println("""
                \n# Remove vendor
                Registered vendors :
                """);
        for (Vendor vendor : Configuration.getVendors()) {
            System.out.println(vendor.getVendorId());
        }
        System.out.print("\nEnter vendor id to remove : ");
        long vendorId = scanner.nextLong();
        if (Configuration.removeVendor(vendorId)) {
            System.out.println("Success!");
        } else {
            System.out.println("Vendor id not found");
        }
    }
}
