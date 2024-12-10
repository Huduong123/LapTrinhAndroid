package com.example.projectandroidcuoiki.model;

import java.io.Serializable;

// Product.java
public class Product implements Serializable {
    private String name;
    private int price;
    private String description;
    private int quantity;
    private int maxStock; // Số lượng tối đa trong kho
    private int imageResId;
    private String brand;
    private String size; // Thêm thuộc tính size


    public Product(String name, int price, String description, int quantity, int maxStock, int imageResId, String brand) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.maxStock = maxStock;
        this.imageResId = imageResId;
        this.brand = brand;
    }

    public Product(String name, int price, int imageResId, String size, int quantity, int maxStock) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.size = size;
        this.quantity = quantity;
        this.maxStock = maxStock;
    }

    public Product(String name, int price, int quantity, String size) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImageResId() {
        return imageResId;
    }
    public String getBrand() {
        return brand;
    }


    public String getSize() {
        return size;
    }
    public int getMaxStock() {
        return maxStock;
    }

}
