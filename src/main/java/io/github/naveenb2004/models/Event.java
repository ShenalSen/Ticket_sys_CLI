package io.github.naveenb2004.models;

public final class Event {
    private long id;
    private String name;
    private Vendor vendor;
    private int totalNumberOfTickets;
    private int maxNumberOfTickets;

    public Event(long id, String name, Vendor vendor, int totalNumberOfTickets, int maxNumberOfTickets) {
        this.id = id;
        this.name = name;
        this.vendor = vendor;
        this.totalNumberOfTickets = totalNumberOfTickets;
        this.maxNumberOfTickets = maxNumberOfTickets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public int getTotalNumberOfTickets() {
        return totalNumberOfTickets;
    }

    public void setTotalNumberOfTickets(int totalNumberOfTickets) {
        this.totalNumberOfTickets = totalNumberOfTickets;
    }

    public int getMaxNumberOfTickets() {
        return maxNumberOfTickets;
    }

    public void setMaxNumberOfTickets(int maxNumberOfTickets) {
        this.maxNumberOfTickets = maxNumberOfTickets;
    }
}
