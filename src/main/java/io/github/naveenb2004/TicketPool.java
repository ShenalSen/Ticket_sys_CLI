package io.github.naveenb2004;

public final class TicketPool {
    private static long globalPoolId = 1L;

    private final long poolId;
    private final long vendorId;
    private int totalNumberOfTickets;
    private final int maximumTicketCapacity;

    public TicketPool(long poolId,
                      long vendorId,
                      int totalNumberOfTickets,
                      int maximumTicketCapacity) {
        this.poolId = poolId;
        this.vendorId = vendorId;
        this.totalNumberOfTickets = totalNumberOfTickets;
        this.maximumTicketCapacity = maximumTicketCapacity;
    }

    public long getPoolId() {
        return poolId;
    }

    public long getVendorId() {
        return vendorId;
    }

    public int getTotalNumberOfTickets() {
        return totalNumberOfTickets;
    }

    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    public synchronized void putTicketsToThePool(int ticketsCount) throws InterruptedException {
        for (int i = 0; i < ticketsCount; i++) {
            if (totalNumberOfTickets + 1 > maximumTicketCapacity) {
                System.out.println("Pool-" + poolId + " : Too many tickets");
                wait();
            }
            totalNumberOfTickets++;
            System.out.println("Pool-" + poolId + " : Ticket Added");
            notify();
        }
    }

    public synchronized void getTicketsFromThePool(int ticketsCount) throws InterruptedException {
        for (int i = 0; i < ticketsCount; i++) {
            if (totalNumberOfTickets - 1 == 0) {
                System.out.println("Pool-" + poolId + " : No Tickets");
                wait();
            }
            totalNumberOfTickets--;
            System.out.println("Pool-" + poolId + " : Ticket Removed");
            notify();
        }
    }

    public static synchronized long getGlobalPoolId() {
        return globalPoolId++;
    }
}
