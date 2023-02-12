package org.example;

import java.sql.*;

public class Venta {


    public void registrarVenta(Connection con, String cName, String pName, Integer cantidad) throws SQLException {

        // Client id
        Integer cID = null;

        // Product info
        Integer pID = null;
        Integer pPrice = null;
        Integer pStock = null;

        Statement stm = con.createStatement();
        String query1 = "SELECT id, price, stock from producto WHERE name = "+ String.format("'%s'", pName);
        String query2 = "SELECT id from cliente WHERE name = "+ String.format("'%s'", cName);

        ResultSet rs1 = stm.executeQuery(query1);

        if(rs1.next()){
            pID = rs1.getInt("id");
            pPrice = rs1.getInt("price");
            pStock = rs1.getInt("stock");
        }


        ResultSet rs2 = stm.executeQuery(query2);

        if(rs2.next()){
            cID = rs2.getInt("id");
        }

        PreparedStatement pstmt1 = null;
        String sql1 = "UPDATE producto SET stock = ? WHERE id = ?";

        try {
            pstmt1 = con.prepareStatement(sql1);
            pstmt1.setInt(1, pStock - cantidad);
            pstmt1.setInt(2, pID);
            pstmt1.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PreparedStatement pstmt2 = null;
        String sql2 = "INSERT INTO venta(unit, total, cliente_id, producto_id) VALUES (?, ?, ?, ?)";

        try {
            pstmt2 = con.prepareStatement(sql2);
            pstmt2.setInt(1, cantidad);
            // Sin margen de ganancia
            pstmt2.setInt(2, cantidad*pPrice);
            pstmt2.setInt(3, cID);
            pstmt2.setInt(4, pID);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
