package io.github.naveenb2004.ui.customer;

import io.github.naveenb2004.models.Customer;

import java.net.Socket;

public final class CustomerServer {
    private final Customer customer;
    private final Socket socket;

    public CustomerServer(Customer customer, Socket socket) {
        this.customer = customer;
        this.socket = socket;
    }
}