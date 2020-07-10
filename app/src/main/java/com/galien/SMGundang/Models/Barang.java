package com.galien.SMGundang.Models;

public class Barang {
    private String name;
    private String id;
    private int stock;

    public Barang(String name, String id, int stock) {
        this.name = name;
        this.id = id;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
