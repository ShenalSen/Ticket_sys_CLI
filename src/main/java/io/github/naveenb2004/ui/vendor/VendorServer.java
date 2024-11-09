package io.github.naveenb2004.ui.vendor;

import io.github.naveenb2004.models.Vendor;

import java.net.Socket;

public final class VendorServer {
    private final Vendor vendor;
    private final Socket socket;

    public VendorServer(Vendor vendor, Socket socket) {
        this.vendor = vendor;
        this.socket = socket;
    }


}
