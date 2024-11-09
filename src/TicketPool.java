public class TicketPool {
    private int currentTickets;
    private final int maxTicketCapacity;
    private boolean running = false;

    public TicketPool(Configuration config) {
        this.currentTickets = config.getTotalTickets();
        this.maxTicketCapacity = config.getMaxTicketCapacity();
    }

    public synchronized void startSystem() {
        running = true;
        System.out.println("Ticket system started.");
    }

    public synchronized void stopSystem() {
        running = false;
        System.out.println("Ticket system stopped.");
        notifyAll();
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void addTickets(int tickets) throws InterruptedException {
        if (!running) {
            System.out.println("System is not running. Try again later.");
            return;
        }
        while (currentTickets + tickets > maxTicketCapacity) {
            System.out.println("Maximum number of tickets exceeded. Waiting...");
            wait();
        }
        currentTickets += tickets;
        logTransactions("Added " + tickets + " tickets");
        showCurrentTickets();
        notifyAll();
    }

    public synchronized void removeTickets(int tickets) throws InterruptedException {
        if (!running) {
            System.out.println("System is not running. Try again later.");
            return;
        }
        while (currentTickets < tickets) {
            System.out.println("Not enough tickets to remove. Waiting...");
            wait();
        }
        currentTickets -= tickets;
        logTransactions("Removed " + tickets + " tickets");
        showCurrentTickets();
        notifyAll();
    }

    public synchronized void showCurrentTickets() {
        System.out.println("Current tickets: " + currentTickets);
    }

    private void logTransactions(String message) {
        System.out.println("LOG: " + message + " | Current tickets: " + currentTickets);
    }
}
