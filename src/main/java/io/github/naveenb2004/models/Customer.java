package io.github.naveenb2004.models;

public final class Customer {
    private long id;
    private String name;
    private String username;
    private String password;
    private boolean vip;

    public Customer() {}

    public Customer(long id, String name, String username, String password, boolean vip) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.vip = vip;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}
