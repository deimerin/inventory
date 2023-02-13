package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainUI extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JButton registrarVentaButton;
    private JComboBox comboBox3;
    private JTextField textField2;
    private JTextField textField3;
    private JButton registrarCompraButton;
    private JTextField nombreProducto;
    private JTextField cantidadProducto;
    private JButton agregarProductoButton;
    private JTextField precioProducto;

    public void setUI() throws SQLException {

        DBC db = new DBC();
        Connection con = db.connect();
        Compra newCompra = new Compra();

        VariablesProducto varProducto = new VariablesProducto(nombreProducto, cantidadProducto, precioProducto);

        //Venta newVenta = new Venta();
        //newVenta.registrarVenta(con,"Joyce", "Headphones", 3);
        //newCompra.registrarCompra(con,"Headphones",15,255);

        Statement stm = con.createStatement();
        String sql = "SELECT * FROM producto";
        ResultSet resultset = stm.executeQuery(sql);
        while(resultset.next()) {
            String columna1 = resultset.getString("name");
            int columna2 = resultset.getInt("price");
            int columna3 = resultset.getInt("stock");
            System.out.println(columna1 + ", " + columna2 + ", " + columna3);
        }

        this.setContentPane(this.panel1);
        this.setTitle("Sistema de Inventario");
        this.setBounds(600, 20, 295, 235);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        agregarProductoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre_producto = varProducto.getNombreProducto();
                int cantidad_producto = varProducto.getCantidadProducto();
                int precio_producto = varProducto.getPrecioProducto();

                if(nombre_producto.isEmpty()){
                    JOptionPane.showMessageDialog(null, "El nombre del producto es requerido");
                    return;
                }

                if (cantidad_producto == 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad del producto es requerida");
                    return;
                }

                if (precio_producto == 0) {
                    JOptionPane.showMessageDialog(null, "El precio del producto es requerido");
                    return;
                }

                try {
                    newCompra.agregarProducto(con, nombre_producto, cantidad_producto, precio_producto);
                    JOptionPane.showMessageDialog(null, "¡Producto agregado con exito!");
                    nombreProducto.setText("");
                    cantidadProducto.setText("");
                    precioProducto.setText("");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }


}
