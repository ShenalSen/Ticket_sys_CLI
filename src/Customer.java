public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievingTickets;
    private final int retrievalRate;

    public Customer(TicketPool ticketPool, int retrievalRate, int retrievingTickets) {
        this.ticketPool = ticketPool;
        this.retrievingTickets = retrievingTickets;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        while (ticketPool.isRunning()) {
            try {
                ticketPool.removeTickets(retrievingTickets);
                Thread.sleep(retrievalRate);
            } catch (InterruptedException e) {
                System.out.println("Customer interrupted.");
            }
        }
    }
}
