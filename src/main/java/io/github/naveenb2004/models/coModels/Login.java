package io.github.naveenb2004.models.coModels;

import io.github.naveenb2004.helper.Database;
import io.github.naveenb2004.models.Customer;
import io.github.naveenb2004.models.Vendor;

import java.sql.*;

public final class Login {
    private String userType;
    private String userName;
    private String password;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object validateUser() throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(ROWID), * " +
                "FROM " + (userType.equals("CUSTOMER") ? "Customer" : "Vendor") + " " +
                "WHERE username = ? AND password = ?");
        stmt.setString(1, userName);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.getInt(1) == 1) {
            return userType.equals("CUSTOMER") ?
                    new Customer(rs.getLong(2), rs.getString(3), rs.getString(4), null, rs.getBoolean(6)) :
                    new Vendor(rs.getLong(2), rs.getString(3), rs.getString(4), null);
        }
        return userType.equals("CUSTOMER") ? new Customer() : new Vendor();
    }
}
