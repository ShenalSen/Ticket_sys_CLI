package io.github.naveenb2004.helper;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public final class Statics {
    private static Scanner scanner;
    private static Socket socket;
    private static Gson gson;

    /**
     * Get scanner for command line
     * @return Scanner
     */
    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    /**
     * Initialize client socket for current session
     * @return Initialized socket
     * @throws IOException Socket exception
     */
    public static Socket getSocket() throws IOException {
        if (socket == null) {
            socket = new Socket("localhost", 2004);
        }
        return socket;
    }

    /**
     * Get Gson json instance
     * @return Gson instance
     */
    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
