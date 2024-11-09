import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configure the ticket system
        Configuration configuration = new Configuration();
        configuration.configure(scanner);

        // Save the configuration if needed
        System.out.println("Do you want to save the configuration? (Y/N)");
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("Enter the file name (.txt):");
            String filename = scanner.next();
            configuration.saveFile(filename);
        } else {
            System.out.println("Configuration not saved.");
        }

        // Create the ticket pool with the configured settings
        TicketPool ticketPool = new TicketPool(configuration);

        // Start user interaction loop
        while (true) {
            System.out.println("Enter order (1: Start, 2: Stop, 3: Exit):");
            int order = scanner.nextInt();

            switch (order) {
                case 1 -> {
                    ticketPool.startSystem();
                    ticketOperations(scanner, ticketPool);
                }
                case 2 -> ticketPool.stopSystem();
                case 3 -> {
                    ticketPool.stopSystem();
                    System.out.println("System Closed");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid order");
            }
        }
    }

    private static void ticketOperations(Scanner scanner, TicketPool ticketPool) {
        while (ticketPool.isRunning()) {
            System.out.println("1: Add ticket, 2: Remove ticket, 3: Show Current Tickets, 4: Stop");
            int operation = scanner.nextInt();

            try {
                switch (operation) {
                    case 1 -> {
                        System.out.println("Enter the amount of tickets to add:");
                        int amount = scanner.nextInt();
                        ticketPool.addTickets(amount);
                    }
                    case 2 -> {
                        System.out.println("Enter the amount of tickets to remove:");
                        int amount = scanner.nextInt();
                        ticketPool.removeTickets(amount);
                    }
                    case 3 -> ticketPool.showCurrentTickets();
                    case 4 -> ticketPool.stopSystem();
                    default -> System.out.println("Invalid operation");
                }
            } catch (InterruptedException e) {
                System.out.println("Operation interrupted.");
            }
        }
    }
}
