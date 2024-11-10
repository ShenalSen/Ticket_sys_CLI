package io.github.naveenb2004;

import java.util.ArrayList;

public final class Configuration {
    private static final ArrayList<Vendor> vendors = new ArrayList<>();
    private static final ArrayList<Customer> customers = new ArrayList<>();
    private static final ArrayList<TicketPool> pools = new ArrayList<>();

    public static ArrayList<Vendor> getVendors() {
        return vendors;
    }

    public static synchronized void addVendor(Vendor vendors) {
        Configuration.vendors.add(vendors);
    }

    public static synchronized boolean removeVendor(long vendorId) {
        if (!vendorExists(vendorId)) {
            return false;
        }
        Configuration.vendors.removeIf(vendor -> vendor.getVendorId() == vendorId);
        Configuration.pools.removeIf(pool -> pool.getVendorId() == vendorId);
        return true;
    }

    public static boolean vendorExists(long vendorId) {
        for (Vendor vendor : Configuration.vendors) {
            if (vendor.getVendorId() == vendorId) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    public static synchronized void addCustomer(Customer customers) {
        Configuration.customers.add(customers);
    }

    public static ArrayList<TicketPool> getPools() {
        return pools;
    }

    public static synchronized void addPool(TicketPool pools) {
        Configuration.pools.add(pools);
    }

    public static synchronized boolean removePool(long poolId) {
        if (!poolExists(poolId)) {
            return false;
        }
        Configuration.pools.removeIf(pool -> pool.getPoolId() == poolId);
        return true;
    }

    public static boolean poolExists(long poolId) {
        for (TicketPool pool : Configuration.pools) {
            if (pool.getPoolId() == poolId) {
                return true;
            }
        }
        return false;
    }
}
