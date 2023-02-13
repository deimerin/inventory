package org.example;

public class CompraFactory implements BaseFactory {
    public Object crear() {
        return new Compra();
    }
}
