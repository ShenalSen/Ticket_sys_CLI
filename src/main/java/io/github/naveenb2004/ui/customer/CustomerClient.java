package io.github.naveenb2004.ui.customer;

import io.github.naveenb2004.models.Customer;

import java.net.Socket;

public final class CustomerClient {
    private final Customer customer;

    public CustomerClient(Customer customer) {
        this.customer = customer;
    }
}
