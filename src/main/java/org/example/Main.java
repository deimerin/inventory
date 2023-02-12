package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBC db = new DBC();
        Connection con = db.connect();

        Compra newCompra = new Compra();

        newCompra.agregarProducto(con, "Headphones", 12, 300);


        //MainUI ui = new MainUI();
        //ui.setUI();

    }
}