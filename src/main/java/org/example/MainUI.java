package org.example;

import javax.swing.*;

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
    private JTextField textField4;
    private JTextField textField5;
    private JButton agregarProductoButton;
    private JTextField textField6;

    public void setUI(){
        this.setContentPane(this.panel1);
        this.setTitle("Sistema de Inventario");
        this.setBounds(600, 20, 295, 235);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



}
