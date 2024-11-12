package io.github.naveenb2004;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public record Configuration(int totalNumberOfTickets,
                            int ticketReleaseRate,
                            int ticketRetrievalRate,
                            int maximumTicketsCapacity) {

    public void save() throws IOException {
        System.out.println("Saving configuration...");
        Gson gson = new Gson();
        Path path = Paths.get("TicketSystemConfiguration.json");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Files.writeString(path, gson.toJson(this), StandardOpenOption.WRITE);
        System.out.println("Configuration saved!");
    }
}
