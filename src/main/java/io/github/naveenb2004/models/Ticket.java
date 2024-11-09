package io.github.naveenb2004.models;

public final class Ticket {
    private long id;
    private Event event;
    private Customer customer;
    private int ticketsCount;

    public Ticket(long id, Event event, Customer customer, int ticketsCount) {
        this.id = id;
        this.event = event;
        this.customer = customer;
        this.ticketsCount = ticketsCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getTicketsCount() {
        return ticketsCount;
    }

    public void setTicketsCount(int ticketsCount) {
        this.ticketsCount = ticketsCount;
    }
}
