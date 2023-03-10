package org.example;

import java.sql.*;

public class Compra {

    public Integer calcularArqueo(Integer cPrice, Integer cStock, Integer nPrice, Integer nStock){
        Integer nuevoPrecio = ((cPrice*cStock) + (nPrice*nStock) ) / (cStock + nStock);
        return nuevoPrecio;
    }

    public void registrarCompra(Connection con, String nombre, Integer stock, Integer price) throws SQLException {

        Integer cPrice = null;
        Integer cStock = null;
        Integer pID = null;

        Statement stm = con.createStatement();
        String query = "SELECT price, stock, id FROM producto WHERE name = "+ String.format("'%s'", nombre);
        ResultSet rs = stm.executeQuery(query);

        if(rs.next()){
            cPrice = rs.getInt("price");
            cStock = rs.getInt("stock");
            pID = rs.getInt("id");
        }

        PreparedStatement pstmt = null;
        String sql = "UPDATE producto SET stock=?, price = ? WHERE name = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, cStock+stock);
            pstmt.setInt(2, calcularArqueo(cPrice,cStock,price,stock));
            pstmt.setString(3, nombre);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql2 = "INSERT INTO compra(producto_id, unidades, precio) VALUES (?,?,?)";

        try {
            pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, pID);
            pstmt.setInt(2, stock);
            pstmt.setInt(3, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void agregarProducto(Connection con, String nombre, Integer stock, Integer price) throws SQLException {

        PreparedStatement pstmt = null;
        Statement stm = con.createStatement();
        Integer pID = null;

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

        String query = "SELECT id FROM producto WHERE name = "+ String.format("'%s'", nombre);
        ResultSet rs = stm.executeQuery(query);

        if(rs.next()){
            pID = rs.getInt("id");
        }

        String sql2 = "INSERT INTO compra(producto_id, unidades, precio) VALUES (?,?,?)";

        try {
            pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, pID);
            pstmt.setInt(2, stock);
            pstmt.setInt(3, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
