package org.example;

import javax.swing.*;
import java.sql.*;

public class Clientes {

    public void setClientes(Connection con, JComboBox selectorClientes) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM cliente");

        DefaultComboBoxModel<String> clients = new DefaultComboBoxModel<>();

        while (rs.next()) {
            String nombre = rs.getString("name");
            clients.addElement(nombre);
        }

        selectorClientes.setModel(clients);
    }

}
