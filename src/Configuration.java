import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Configuration implements Serializable {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private static final long serialVersionUID = 1L;

    public void configure(Scanner scanner) {
        System.out.println("Configuring Ticket Pool...");
        System.out.print("Enter total tickets: ");
        this.totalTickets = scanner.nextInt();
        System.out.print("Enter ticket release rate: ");
        this.ticketReleaseRate = scanner.nextInt();
        System.out.print("Enter customer retrieval rate: ");
        this.customerRetrievalRate = scanner.nextInt();
        System.out.print("Enter max ticket capacity: ");
        this.maxTicketCapacity = scanner.nextInt();
    }

    public void saveFile(String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("Total Tickets: " + totalTickets + "\nTicket Release Rate: " + ticketReleaseRate +
                    "\nCustomer Retrieval Rate: " + customerRetrievalRate +
                    "\nMaximum Ticket Capacity: " + maxTicketCapacity);
            System.out.println("Configuration settings saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error while saving file");
            e.printStackTrace();
        }
    }

    // Getters
    public int getTotalTickets() { return totalTickets; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
}
