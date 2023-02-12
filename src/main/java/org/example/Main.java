package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBC db = new DBC();
        Connection con = db.connect();

        PreparedStatement pstmt = null;

        String sql = "INSERT INTO (column1, column2) VALUES (?, ?)";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, "value1");
        pstmt.setInt(2, 123);
        pstmt.executeUpdate();


        con.

        //MainUI ui = new MainUI();
        //ui.setUI();

    }
}