package io.github.naveenb2004.helper;

import io.github.naveenb2004.models.Customer;
import io.github.naveenb2004.models.Vendor;
import io.github.naveenb2004.models.coModels.Login;
import io.github.naveenb2004.ui.customer.CustomerServer;
import io.github.naveenb2004.ui.vendor.VendorServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handle server socket related operations
 */
public final class Server {
    private static ExecutorService executor;
    private static Thread serverThread;
    private static ServerSocket serverSocket;

    /**
     * Start the server
     */
    public static void startServer() {
        executor = Executors.newFixedThreadPool(100);
        serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(8080);
                while (serverThread.isAlive()) {
                    executor.execute(new ConnectionManager(serverSocket.accept()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Stop the server
     */
    public static void stopServer() throws IOException, InterruptedException {
        executor.shutdownNow();
        serverSocket.close();
        Thread.sleep(1000);
        if (serverThread.isAlive()) {
            serverThread.interrupt();
        }
    }

    /**
     * Validate and filter-out users
     */
    public static final class ConnectionManager implements Runnable {
        private final Socket socket;

        public ConnectionManager(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Login login = Statics.getGson().fromJson(reader.readLine(), Login.class);
                Object model = login.validateUser();

                if (model instanceof Customer customer) {
                    socket.getOutputStream().write((Statics.getGson().toJson(customer) + "\n")
                            .getBytes(StandardCharsets.UTF_8));
                    if (customer.getId() != 0L) {
                        new CustomerServer(customer, socket);
                    }
                }
                if (model instanceof Vendor vendor) {
                    socket.getOutputStream().write((Statics.getGson().toJson(vendor) + "\n")
                            .getBytes(StandardCharsets.UTF_8));
                    if (vendor.getId() != 0L) {
                        new VendorServer(vendor, socket);
                    }
                }
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
