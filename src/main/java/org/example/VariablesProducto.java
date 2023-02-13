package org.example;

import javax.swing.*;

public class VariablesProducto {
    private JTextField nombreProducto;
    private JTextField cantidadProducto;
    private JTextField precioProducto;

    public VariablesProducto(JTextField nombreProducto, JTextField cantidadProducto, JTextField precioProducto) {
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioProducto = precioProducto;
    }

    public String getNombreProducto() {
        return nombreProducto.getText();
    }

    public int getCantidadProducto() {
        String cantidadStr = cantidadProducto.getText();
        if (cantidadStr.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(cantidadStr);
    }

    public int getPrecioProducto() {
        String precioStr = precioProducto.getText();
        if (precioStr.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(precioStr);
    }
}