package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBC db = new DBC();
        Connection con = db.connect();
        Compra newCompra = new Compra();

        newCompra.agregarProducto(con, "Notebook", 25, 15);

        //Venta newVenta = new Venta();
        //newVenta.registrarVenta(con,"Joyce", "Headphones", 3);
        //newCompra.registrarCompra(con,"Headphones",15,255);



        //MainUI ui = new MainUI();
        //ui.setUI();

    }
}