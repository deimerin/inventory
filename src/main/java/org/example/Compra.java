package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Compra {

    public void registrarCompra(Connection con, String nombre, Integer stock, Integer price){

        PreparedStatement pstmt = null;

        String sql = "INSERT INTO producto(name, price, stock) VALUES (?, ?, ?)";

    }

    public void agregarProducto(Connection con, String nombre, Integer stock, Integer price){

        PreparedStatement pstmt = null;

        String sql = "INSERT INTO producto(name, price, stock) VALUES (?, ?, ?)";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setInt(2, price);
            pstmt.setInt(3, stock);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
