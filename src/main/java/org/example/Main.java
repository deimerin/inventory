package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBC db = new DBC();
        Connection con = db.connect();
        Compra newCompra = new Compra();

        newCompra.registrarCompra(con,"Headphones",25,250);

//        String nombre = "Headphones";
//
//        Statement pstmt = con.createStatement();
//        String query = "SELECT price, stock FROM producto WHERE name = "+ String.format("'%s'", nombre);
//
//        ResultSet rs = pstmt.executeQuery(query);
//        if (rs.next()) {
//            System.out.println(rs.getInt("price"));
//            System.out.println(rs.getInt("stock"));
//        }

//        Integer wer = newCompra.calcularArqueo(222,15,250,25);
//        System.out.println(wer);








        //MainUI ui = new MainUI();
        //ui.setUI();

    }
}