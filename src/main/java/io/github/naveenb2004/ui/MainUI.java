package io.github.naveenb2004.ui;

import io.github.naveenb2004.helper.Statics;
import io.github.naveenb2004.models.Customer;
import io.github.naveenb2004.models.Vendor;
import io.github.naveenb2004.models.coModels.Login;
import io.github.naveenb2004.ui.customer.CustomerClient;
import io.github.naveenb2004.ui.vendor.VendorClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainUI {
    public static void login() throws IOException {
        Login login = new Login();

        userTypeLoop:
        while (true) {
            System.out.print("Enter user type (C - Customer / V - Vendor) : ");
            char type = Statics.getScanner().next().charAt(0);
            switch (type) {
                case 'C', 'c' -> {
                    login.setUserType("CUSTOMER");
                    break userTypeLoop;
                }
                case 'V', 'v' -> {
                    login.setUserType("VENDOR");
                    break userTypeLoop;
                }
                default -> System.out.println("Invalid user type");
            }
        }

        System.out.print("Enter user name : ");
        login.setUserName(Statics.getScanner().next());

        System.out.print("Enter password : ");
        login.setPassword(Statics.getScanner().next());

        String jsonLogin = Statics.getGson().toJson(login, Login.class) + "\n";
        Statics.getSocket().getOutputStream().write(jsonLogin.getBytes(StandardCharsets.UTF_8));

        BufferedReader reader = new BufferedReader(new InputStreamReader(Statics.getSocket().getInputStream()));
        if (login.getUserType().equals("CUSTOMER")) {
            Customer customer = Statics.getGson().fromJson(reader.readLine(), Customer.class);
            if (customer.getId() == 0L) {
                System.out.println("Customer not found");
                return;
            }
            new CustomerClient(customer);
        } else {
            Vendor vendor = Statics.getGson().fromJson(reader.readLine(), Vendor.class);
            if (vendor.getId() == 0L) {
                System.out.println("Vendor not found");
                return;
            }
            new VendorClient(vendor);
        }
    }
}
