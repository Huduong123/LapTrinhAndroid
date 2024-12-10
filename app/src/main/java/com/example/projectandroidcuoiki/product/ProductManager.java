package com.example.projectandroidcuoiki.product;

import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private static ProductManager instance;
    private List<Product> productList;

    private ProductManager() {
        productList = new ArrayList<>();
        productList.add(new Product("Air Jordan Low", 2100000, "Nike Running Shoe", 10,10, R.drawable.nike_jordan_low, "Nike"));
        productList.add(new Product("Air Jordan 4 RM", 3000000, "Nike Casual Shoe", 10,10, R.drawable.nike_jordan4_rm, "Nike"));
        productList.add(new Product("Jordan Spizike Low PSG", 4999000, "Nike Athletic Shoe", 10,10, R.drawable.nike_jordan_psg, "Nike"));
        productList.add(new Product("Jordan Max Aura 6", 2549000, "Nike Durable Shoe", 10,10, R.drawable.nike_jordan_max6, "Nike"));
        productList.add(new Product("Jordan Stay Loyal 3", 3369000, "Nike Running Shoe", 10,10, R.drawable.nike_jordan_stayloyal3, "Nike"));
        productList.add(new Product("Air Jordan XXXIX PF", 5869000, "Nike Casual Shoe", 10,10, R.drawable.nike_jordan_pf, "Nike"));
        productList.add(new Product("Air Jordan MVP", 4500000, "Nike Athletic Shoe", 10,10, R.drawable.nike_jordan_mvp, "Nike"));
        productList.add(new Product("Air Jordan 1 MID", 3890000, "Nike Durable Shoe", 10,10, R.drawable.nike_jordan_mid, "Nike"));
        productList.add(new Product("Luka 3 PF", 3829000, "Nike Basketball Shoe", 10,10, R.drawable.nike_jordan_luka3pf, "Nike"));
        productList.add(new Product("Zion 3 Rising PF", 300000, "Nike Basketball Shoe", 10,10, R.drawable.nike_jordan_zion3, "Nike"));

        //adidas
        productList.add(new Product("Adidas Zero Red", 2000000, "Adidas Running Shoe", 10,10, R.drawable.adidas_zero_red, "Adidas"));
        productList.add(new Product("Campus 00s", 2600000, "Adidas Casual Shoe", 10,10, R.drawable.adidas_campus, "Adidas"));
        productList.add(new Product("Forum Low CL", 2600000, "Adidas Originals Shoe", 10,10, R.drawable.adidas_forumxanh, "Adidas"));
        productList.add(new Product("Gazelle", 2900000, "Adidas Originals Shoe", 10,10, R.drawable.adidas_gazelle, "Adidas"));
        productList.add(new Product("Handball Spezial", 2500000, "Adidas Casual Shoe", 10,10, R.drawable.adidas_handball, "Adidas"));
        productList.add(new Product("Jabbar Low", 2400000, "Adidas Casual Shoe", 10,10, R.drawable.adidas_jabbarlow, "Adidas"));
        productList.add(new Product("Response CL", 2300000, "Adidas Running Shoe", 10,10, R.drawable.adidas_reponse, "Adidas"));
        productList.add(new Product("SL 72 OG", 2000000, "Adidas Casual Shoe", 10,10, R.drawable.adidas_sl72og, "Adidas"));
        productList.add(new Product("Stan Smith", 2900000, "Adidas Duralble Shoe", 10,10, R.drawable.adidas_stansmith, "Adidas"));
        productList.add(new Product("Super Star", 1500000, "Adidas Durable Shoe", 10,10, R.drawable.adidas_supperstar, "Adidas"));

        productList.add(new Product("480", 2100000, "New Balance Originals Shoe", 10,10, R.drawable.newbalance_480, "New Balance"));
        productList.add(new Product("530", 3000000, "New Balance Durable Shoe", 10,10, R.drawable.newbalance_530, "New Balance"));
        productList.add(new Product("990", 2200000, "New Balance Casual Shoe", 10,10, R.drawable.newbalance_990, "New Balance"));
        productList.add(new Product("1080", 3900000, "New Balance Durable Shoe", 10,10, R.drawable.newbalance_1080, "New Balance"));
        productList.add(new Product("1906", 21900000, "New Balance Casual Shoe", 10,10, R.drawable.newbalance_1906, "New Balance"));
        productList.add(new Product("3000", 2700000, "New Balance Originals Shoe", 10,10, R.drawable.newbalance_3000, "New Balance"));
        productList.add(new Product("9060", 2500000, "New Balance Durable Shoe", 10,10, R.drawable.newbalance_9060, "New Balance"));
        productList.add(new Product("FB1", 1800000, "New Balance Casual Shoe", 10,10, R.drawable.newbalance_fb1, "New Balance"));
        productList.add(new Product("Fresh Foam", 1700000, "New Balance Running Shoe", 10,10, R.drawable.newbalance_freshfoam, "New Balance"));
        productList.add(new Product("FuelCell3", 3100000, "New Balance Running Shoe", 10,10, R.drawable.newbalance_fuelcell3, "New Balance"));

        productList.add(new Product("BSM 001100", 2100000, "Bitis Running Shoe", 10,10, R.drawable.bitis_bsm001100, "Bitis"));
        productList.add(new Product("Football DSMH", 1500000, "Bitis Football Shoe", 10,10, R.drawable.bitis_dsmh, "Bitis"));
        productList.add(new Product("Football Fulsal DSMH", 1600000, "Bitis Football Shoe", 10,10, R.drawable.bitis_dsmhstar, "Bitis"));
        productList.add(new Product("Hunter Football HSM", 1300000, "Bitis Football Shoe", 10,10, R.drawable.bitis_hfhsm, "Bitis"));
        productList.add(new Product("HLD", 600000, "Bitis Running Shoe", 10,10, R.drawable.bitis_hld, "Bitis"));
        productList.add(new Product("Hunter LD University", 1200000, "Bitis Running Shoe", 10,10, R.drawable.bitis_hlu, "Bitis"));
        productList.add(new Product("Hunter Street Festive Feria", 1100000, "Bitis Running Shoe", 10,10, R.drawable.bitis_hsffxanh, "Bitis"));
        productList.add(new Product("Hunter Street Festive Feria 2", 1300000, "Bitis Running Shoe", 10,10, R.drawable.bitis_hsff, "Bitis"));
        productList.add(new Product("HRLD", 1400000, "Bitis Casual Shoe", 10,10, R.drawable.bitis_hrld, "Bitis"));
        productList.add(new Product("Hunter Street", 2100000, "Bitis Running Shoe", 10,10, R.drawable.bitis_hunterstreet, "Bitis"));
    }

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void updateProductQuantity(String productName, int purchasedQuantity) {
        for (Product product : productList) {
            if (product.getName().equals(productName)) {
                product.setQuantity(Math.max(product.getQuantity() - purchasedQuantity, 0));
                break;
            }
        }
    }
}
