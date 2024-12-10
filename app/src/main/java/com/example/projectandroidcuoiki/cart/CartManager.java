package com.example.projectandroidcuoiki.cart;

import com.example.projectandroidcuoiki.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<Product> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product) {
        for (Product item : cartItems) {
            if (item.getName().equals(product.getName()) && item.getSize().equals(product.getSize())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cartItems.add(product);
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    // Cập nhật số lượng sản phẩm
    public void updateQuantity(Product product, int quantity) {
        for (Product item : cartItems) {
            if (item.getName().equals(product.getName()) && item.getSize().equals(product.getSize())) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    cartItems.remove(item);
                }
                return;
            }
        }
    }


    public void removeItem(Product product) {
        cartItems.removeIf(item -> item.getName().equals(product.getName()) && item.getSize().equals(product.getSize()));
    }

    // Kiểm tra giỏ hàng có rỗng không
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    // Tính tổng giá trị sản phẩm trong giỏ hàng
    public int calculateTotal() {
        int total = 0;
        for (Product item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public int calculateDeliveryFee() {
        int deliveryFeePerItem = 20000;
        int totalItems = 0;
        for (Product item : cartItems) {
            totalItems += item.getQuantity();
        }
        return totalItems * deliveryFeePerItem;
    }


    public int getCurrentQuantity(String productName, String size) {
        for (Product item : cartItems) {
            if (item.getName().equals(productName) && item.getSize().equals(size)) {
                return item.getQuantity();
            }
        }
        return 0; // Nếu sản phẩm chưa có trong giỏ hàng
    }

}

