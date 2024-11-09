public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int producingTickets;
    private final int releaseRate;

    public Vendor(TicketPool ticketPool, int producingTickets, int releaseRate) {
        this.ticketPool = ticketPool;
        this.producingTickets = producingTickets;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        while (ticketPool.isRunning()) {
            try {
                ticketPool.addTickets(producingTickets);
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                System.out.println("Vendor interrupted.");
            }
        }
    }
}
