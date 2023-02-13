package org.example;

import javax.swing.*;
import java.sql.*;

public class Productos {

    public void setProductos(Connection con, JComboBox selectorProductos) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM producto");
        DefaultComboBoxModel<String> products = new DefaultComboBoxModel<>();

        while (rs.next()) {
            String nombre = rs.getString("name");
            products.addElement(nombre);
        }

        selectorProductos.setModel(products);
    }

    public int getProductPrice(Connection con, String productName) throws SQLException {
        PreparedStatement pstmt = null;
        String sql ="SELECT price FROM producto WHERE name = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Integer precio = rs.getInt("price");

                return precio;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró este producto en nuestra base de datos");
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
