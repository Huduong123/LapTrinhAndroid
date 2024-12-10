package com.example.projectandroidcuoiki.model;

import java.util.List;

public class Order {


    private String orderId;
    private String orderDate;
    private String customerName;
    private String phoneNumber;
    private String address;
    private List<Product> productList;
    private double totalOrder;

    public Order(String orderId, String orderDate, String customerName, String phoneNumber, String address, List<Product> productList) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.productList = productList;

        // Tính tổng tiền đơn hàng
        this.totalOrder = calculateTotalOrder(productList);
    }

    private double calculateTotalOrder(List<Product> productList) {
        double total = 0;
        for (Product product : productList) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }
    public String getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getTotalOrder() {
        return totalOrder;
    }

    public List<Product> getProductList() {
        return productList;
    }


}
