package io.github.naveenb2004;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public final class TicketPool {
    private int totalNumberOfTickets;
    private final int ticketReleaseRate;
    private final int ticketRetrievalRate;
    private final int maximumTicketsCapacity;

    public TicketPool(Configuration configuration) {
        totalNumberOfTickets = configuration.totalNumberOfTickets();
        ticketReleaseRate = configuration.ticketReleaseRate();
        ticketRetrievalRate = configuration.ticketRetrievalRate();
        maximumTicketsCapacity = configuration.maximumTicketsCapacity();
    }

    public int getTotalNumberOfTickets() {
        return totalNumberOfTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getTicketRetrievalRate() {
        return ticketRetrievalRate;
    }

    public int getMaximumTicketsCapacity() {
        return maximumTicketsCapacity;
    }

    public synchronized void addTickets(Vendor vendor, int count) throws InterruptedException {
        String logLine;
        if (count > ticketReleaseRate) {
            logLine = getTimestamp() + "\t" + vendor.getVendorId() + " :\tRelease rate exceeded (" +
                    count + "); ignoring...";
            System.out.println(logLine);
            writeToLog(logLine);
            return;
        }

        for (int i = 0; i < count; i++) {
            if (totalNumberOfTickets + 1 > maximumTicketsCapacity) {
                logLine = getTimestamp() + "\t" + vendor.getVendorId() + " :\tMaximum tickets capacity reached (" +
                        totalNumberOfTickets + "/" + maximumTicketsCapacity + "); waiting...";
                System.out.println(logLine);
                writeToLog(logLine);
                wait();
            }
            totalNumberOfTickets++;
            logLine = getTimestamp() + "\t" + vendor.getVendorId() + " :\tTicket added to the pool (" +
                    totalNumberOfTickets + "/" + maximumTicketsCapacity + ").";
            System.out.println(logLine);
            writeToLog(logLine);
            notify();
        }
    }

    public synchronized void removeTickets(Customer customer, int count) throws InterruptedException {
        String logLine;
        if (count > ticketRetrievalRate) {
            logLine = getTimestamp() + "\t" + customer.getCustomerId() + " :\tRetrieval rate exceeded (" +
                    count + "); ignoring...";
            System.out.println(logLine);
            writeToLog(logLine);
            return;
        }

        for (int i = 0; i < count; i++) {
            if (totalNumberOfTickets - 1 < 0) {
                logLine = getTimestamp() + "\t" + customer.getCustomerId() + " :\tNo more tickets to remove (" +
                        totalNumberOfTickets + "/" + maximumTicketsCapacity + "); waiting...";
                System.out.println(logLine);
                writeToLog(logLine);
                wait();
            }
            totalNumberOfTickets--;
            logLine = getTimestamp() + "\t" + customer.getCustomerId() + " :\tTicket removed from the pool (" +
                    totalNumberOfTickets + "/" + maximumTicketsCapacity + ").";
            System.out.println(logLine);
            writeToLog(logLine);
            notify();
        }
    }

    public static synchronized void writeToLog(String logLine) {
        try (FileWriter writer = new FileWriter("TicketSystemSimulation.log")) {
            writer.append(logLine).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getTimestamp() {
        return LocalDateTime.now().toString();
    }
}
