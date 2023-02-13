package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainUI extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JComboBox selectorClientes;
    private JComboBox selectorProductos;
    private JTextField cantidadVenta;
    private JButton registrarVentaButton;
    private JComboBox productoCompra;
    private JTextField cantidadCompra;
    private JTextField precioCompra;
    private JButton registrarCompraButton;
    private JTextField nombreProducto;
    private JTextField cantidadProducto;
    private JButton agregarProductoButton;
    private JTextField precioProducto;
    private JTable ventasTable;
    private JTable invTable;
    private JTable comTable;
    private JLabel valorUndVenta;

    public void setUI() throws SQLException {

        DBC db = new DBC();
        Connection con = db.connect();

        BaseFactory compraFactory = new CompraFactory();
        Compra newCompra = (Compra) compraFactory.crear();

        BaseFactory ventaFactory = new VentaFactory();
        Venta newVenta = (Venta) ventaFactory.crear();

        setTableVentas(con);
        setTableInven(con);
        setTableCom(con);

        Clientes Clientes = new Clientes();
        Productos Productos = new Productos();
        VariablesProducto varProducto = new VariablesProducto(nombreProducto, cantidadProducto, precioProducto);

        this.setContentPane(this.panel1);
        this.setTitle("Sistema de Inventario");
        this.setBounds(600, 20, 295, 235);
        this.setVisible(true);
        Clientes.setClientes(con, selectorClientes);
        Productos.setProductos(con, selectorProductos);
        Productos.setProductos(con, productoCompra);
        selectorProductos.setSelectedIndex(-1);
        selectorClientes.setSelectedIndex(-1);
        productoCompra.setSelectedIndex(-1);
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
                    setTableInven(con);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        selectorProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectorProductos.getSelectedIndex() == -1) {
                    return;
                }

                String product = selectorProductos.getSelectedItem().toString();

                try {
                    Integer price = Productos.getProductPrice(con, product);
                    DecimalFormat formatoMoneda = new DecimalFormat("#,###");
                    String formatedPrice = formatoMoneda.format(price);
                    valorUndVenta.setText("$ " + formatedPrice);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        registrarVentaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(selectorClientes.getSelectedIndex() == -1){
                    JOptionPane.showMessageDialog(null, "El campo cliente es requerido");
                    return;
                }

                if(selectorProductos.getSelectedIndex() == -1){
                    JOptionPane.showMessageDialog(null, "El campo producto es requerido");
                    return;
                }

                String client = selectorClientes.getSelectedItem().toString();
                String product = selectorProductos.getSelectedItem().toString();

                if(cantidadVenta.getText().isEmpty()){
                    cantidadVenta.setText("0");
                }

                Integer amount = Integer.parseInt(cantidadVenta.getText());

                try {
                    newVenta.registrarVenta(con,client, product, amount);
                    JOptionPane.showMessageDialog(null, "¡Venta realizada con exito!");
                    selectorClientes.setSelectedItem(null);
                    selectorProductos.setSelectedItem(null);
                    valorUndVenta.setText("");
                    cantidadVenta.setText("");
                    setTableVentas(con);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        registrarCompraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(productoCompra.getSelectedIndex() == -1){
                    JOptionPane.showMessageDialog(null, "El campo producto es requerido");
                    return;
                }

                String cantidadStr = cantidadCompra.getText();

                if (cantidadStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo cantidad es requerido");
                    return;
                }

                String precioStr = precioCompra.getText();

                if (precioStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo precio es requerido");
                    return;
                }

                String producto = productoCompra.getSelectedItem().toString();
                Integer cantidadInt = Integer.parseInt(cantidadStr);
                Integer precioInt = Integer.parseInt(precioStr);
                try {
                    newCompra.registrarCompra(con, producto, cantidadInt, precioInt);
                    JOptionPane.showMessageDialog(null, "¡Compra realizada con exito!");
                    productoCompra.setSelectedItem(null);
                    cantidadCompra.setText("");
                    precioCompra.setText("");
                    setTableCom(con);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


    }

    public void setTableVentas(Connection con) throws SQLException {
        // Tabla Ventas
        DefaultTableModel model = new DefaultTableModel();

        ventasTable.setAutoCreateRowSorter(true);
        ventasTable.setFillsViewportHeight(true);
        ventasTable.setPreferredScrollableViewportSize(new Dimension(550, 200));
        model.addColumn("ID");
        model.addColumn("ID Cliente");
        model.addColumn("ID Producto");
        model.addColumn("Unidades");
        model.addColumn("Total");
        model.addColumn("Fecha");
        ventasTable.setModel(model);

        String query = "SELECT * FROM venta";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);

        while(rs.next()){
            model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getInt("producto_id"),
                    rs.getInt("unit"),
                    rs.getInt("total"),
                    rs.getDate("date")}
            );
        }
    }

    public void setTableInven(Connection con) throws SQLException {
        // Tabla Inventory
        DefaultTableModel model = new DefaultTableModel();

        invTable.setAutoCreateRowSorter(true);
        invTable.setFillsViewportHeight(true);
        invTable.setPreferredScrollableViewportSize(new Dimension(550, 200));
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Stock");
        invTable.setModel(model);

        String query = "SELECT * FROM producto";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);

        while(rs.next()){
            model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("stock")}
            );
        }
    }

    public void setTableCom(Connection con) throws SQLException {
        // Tabla compra
        DefaultTableModel model = new DefaultTableModel();

        comTable.setAutoCreateRowSorter(true);
        comTable.setFillsViewportHeight(true);
        comTable.setPreferredScrollableViewportSize(new Dimension(550, 200));
        model.addColumn("ID");
        model.addColumn("ID Producto");
        model.addColumn("Unidades");
        model.addColumn("Precio");
        comTable.setModel(model);

        String query = "SELECT * FROM compra";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(query);

        while(rs.next()){
            model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getInt("producto_id"),
                    rs.getInt("unidades"),
                    rs.getInt("precio")}
            );
        }
    }
}
