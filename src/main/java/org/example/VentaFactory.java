package org.example;

public class VentaFactory implements BaseFactory {
    public Object crear() {
        return new Venta();
    }
}
