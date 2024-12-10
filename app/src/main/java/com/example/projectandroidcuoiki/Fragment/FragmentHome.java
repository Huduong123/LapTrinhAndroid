package com.example.projectandroidcuoiki.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.projectandroidcuoiki.model.Product;
import com.example.projectandroidcuoiki.product.ProductAdapter;
import com.example.projectandroidcuoiki.product.ProductManager;
import com.example.projectandroidcuoiki.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment {


    private RecyclerView shoesRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private List<Product> allProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        shoesRecyclerView = view.findViewById(R.id.shoes_recycler_view);
        shoesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        allProducts = ProductManager.getInstance().getProductList();
        productList = new ArrayList<>(allProducts);

        productAdapter = new ProductAdapter(getContext(), productList);
        shoesRecyclerView.setAdapter(productAdapter);

        Bundle args = getArguments();
        if (args != null) {
            @SuppressWarnings("unchecked")
            List<Product> purchasedProducts = (List<Product>) args.getSerializable("purchased_products");
            if (purchasedProducts != null && !purchasedProducts.isEmpty()) {
                for (Product purchasedProduct : purchasedProducts) {
                    ProductManager.getInstance().updateProductQuantity(purchasedProduct.getName(), purchasedProduct.getQuantity());
                }
            }
        }


        EditText searchBar = view.findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProductsBySearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ImageView allBrand = view.findViewById(R.id.brand_all);
        ImageView nikeBrand = view.findViewById(R.id.brand_nike);
        ImageView adidasBrand = view.findViewById(R.id.brand_adidas);
        ImageView newBalanceBrand = view.findViewById(R.id.brand_new_balance);
        ImageView bitisBrand = view.findViewById(R.id.brand_bitis);



        allBrand.setOnClickListener(v -> showAllProducts());
        nikeBrand.setOnClickListener(v -> filterProducts("Nike"));
        adidasBrand.setOnClickListener(v -> filterProducts("Adidas"));
        newBalanceBrand.setOnClickListener(v -> filterProducts("New Balance"));
        bitisBrand.setOnClickListener(v -> filterProducts("Bitis"));


        return view;
    }

    private void filterProductsBySearch(String query) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        productAdapter.updateData(filteredProducts);
    }





    private void filterProducts(String brand) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getBrand().equals(brand)) {
                filteredProducts.add(product);
            }
        }
        productAdapter.updateData(filteredProducts);
    }

    private void showAllProducts() {
        productAdapter.updateData(allProducts);
    }


}