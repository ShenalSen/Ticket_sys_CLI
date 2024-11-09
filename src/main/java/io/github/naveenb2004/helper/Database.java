package io.github.naveenb2004.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handle database connection related operations
 */
public final class Database {
    /**
     * Get SQLite database connection for 'TicketSystem.db'
     * @return Database connection for 'TicketSystem.db'
     * @throws SQLException Database connection exception
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:TicketSystem.db");
    }

    /**
     * Construct database for given connection
     * @throws SQLException Query execution exception
     */
    public static void initializeDatabase() throws SQLException {
        String[] queries = {
                "PRAGMA foreign_keys=ON;",
                "CREATE TABLE Customer (" +
                        "ROWID INTEGER PRIMARY KEY," +
                        "username TEXT NOT NULL," +
                        "name TEXT NOT NULL," +
                        "password TEXT NOT NULL," +
                        "vip INTEGER NOT NULL" +
                        ");",
                "CREATE TABLE Vendor (" +
                        "ROWID INTEGER PRIMARY KEY," +
                        "username TEXT NOT NULL," +
                        "name TEXT NOT NULL," +
                        "password TEXT NOT NULL," +
                        ");",
                "CREATE TABLE Event (" +
                        "ROWID INTEGER PRIMARY KEY," +
                        "vendorId INTEGER NOT NULL," +
                        "totalNumberOfTickets INTEGER NOT NULL," +
                        "maxNumberOfTickets INTEGER NOT NULL," +
                        "FOREIGN KEY vendorId REFERENCES Vendor(ROWID) ON DELETE CASCADE" +
                        ");",
                "CREATE TABLE Ticket (" +
                        "ROWID INTEGER PRIMARY KEY," +
                        "eventId INTEGER NOT NULL," +
                        "customerId INTEGER NOT NULL," +
                        "ticketsCount INTEGER NOT NULL," +
                        "FOREIGN KEY eventId REFERENCES Event(ROWID) ON DELETE CASCADE," +
                        "FOREIGN KEY customerId REFERENCES Customer(ROWID) ON DELETE CASCADE" +
                        ");"
        };

        Connection conn = getConnection();
        for (String query : queries) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }
        conn.close();
    }
}
