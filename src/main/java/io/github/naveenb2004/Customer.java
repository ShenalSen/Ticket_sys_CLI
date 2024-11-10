package io.github.naveenb2004;

import java.util.Scanner;

public final class Customer implements Runnable {
    private static long globalCustomerId = 1L;

    private final long customerId;
    private final int retrievalInterval;

    public Customer(long customerId,
                    int retrievalInterval) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
    }

    public long getCustomerId() {
        return customerId;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public static synchronized long getGlobalCustomerId() {
        return globalCustomerId++;
    }

    @Override
    public void run() {
        for (TicketPool pool : Configuration.getPools()) {
            try {
                pool.getTicketsFromThePool(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void customerOperations(Scanner scanner) {
        while (true) {
            System.out.println("""
                    1 - Add new customer
                    0 - Back
                    >\s""");
            char option = scanner.next().charAt(0);
            switch (option) {
                case '1' -> {
                    System.out.println("Enter ticket retrieval interval : ");
                    int interval = scanner.nextInt();
                    if (interval <= 0) {
                        System.out.println("Invalid interval");
                        continue;
                    }

                    Configuration.addCustomer(new Customer(globalCustomerId++, interval));
                    System.out.println("Success!");
                }
                case '0' -> {
                    return;
                }
            }

        }
    }
}
