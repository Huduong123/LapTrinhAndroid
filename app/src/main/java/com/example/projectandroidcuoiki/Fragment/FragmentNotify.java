package com.example.projectandroidcuoiki.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroidcuoiki.model.Order;
import com.example.projectandroidcuoiki.order.OrderAdapter;
import com.example.projectandroidcuoiki.model.Product;
import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.database.DatabaseUser;

import java.util.ArrayList;
import java.util.List;

public class FragmentNotify extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private DatabaseUser databaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container, false);

        recyclerView = view.findViewById(R.id.ordered_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseUser = new DatabaseUser(getContext());
        List<Order> orders = fetchOrdersFromDatabase();

        orderAdapter = new OrderAdapter(getContext(), orders);
        recyclerView.setAdapter(orderAdapter);

        return view;
    }

    private List<Order> fetchOrdersFromDatabase() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = databaseUser.getReadableDatabase();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE);
        int currentUserId = sharedPreferences.getInt("user_id", -1);

        if (currentUserId == -1) {
            Toast.makeText(getContext(), "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            return orders;
        }

        // Truy vấn danh sách đơn hàng theo user_id
        String orderQuery = "SELECT user_orders.id_order AS id_order, user_orders.order_date AS order_date, " +
                "user.hovaten AS hovaten, user.sodienthoai AS sodienthoai, user.diachi AS diachi " +
                "FROM user_orders " +
                "INNER JOIN user ON user_orders.user_id = user.id " +
                "WHERE user_orders.user_id = ?";
        Cursor orderCursor = db.rawQuery(orderQuery, new String[]{String.valueOf(currentUserId)});

        if (orderCursor != null && orderCursor.moveToFirst()) {
            do {
                int orderIdIndex = orderCursor.getColumnIndex("id_order");
                int orderDateIndex = orderCursor.getColumnIndex("order_date");
                int customerNameIndex = orderCursor.getColumnIndex("hovaten");
                int phoneNumberIndex = orderCursor.getColumnIndex("sodienthoai");
                int addressIndex = orderCursor.getColumnIndex("diachi");

                if (orderIdIndex == -1 || orderDateIndex == -1 || customerNameIndex == -1 ||
                        phoneNumberIndex == -1 || addressIndex == -1) {
                    continue;
                }

                int orderId = orderCursor.getInt(orderIdIndex);
                String orderDate = orderCursor.getString(orderDateIndex);
                String customerName = orderCursor.getString(customerNameIndex);
                String phoneNumber = orderCursor.getString(phoneNumberIndex);
                String address = orderCursor.getString(addressIndex);


                List<Product> productList = fetchProductsForOrder(orderId);


                orders.add(new Order(String.valueOf(orderId), orderDate, customerName, phoneNumber, address, productList));
            } while (orderCursor.moveToNext());
            orderCursor.close();
        }

        return orders;
    }




    private List<Product> fetchProductsForOrder(int orderId) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = databaseUser.getReadableDatabase();


        String productQuery = "SELECT product_name, price, quantity, size " +
                "FROM orders " +
                "WHERE order_id = ?";
        Cursor productCursor = db.rawQuery(productQuery, new String[]{String.valueOf(orderId)});

        if (productCursor != null && productCursor.moveToFirst()) {
            do {

                int productNameIndex = productCursor.getColumnIndex("product_name");
                int priceIndex = productCursor.getColumnIndex("price");
                int quantityIndex = productCursor.getColumnIndex("quantity");
                int sizeIndex = productCursor.getColumnIndex("size");

                if (productNameIndex == -1 || priceIndex == -1 || quantityIndex == -1 || sizeIndex == -1) {
                    continue;
                }

                String productName = productCursor.getString(productNameIndex);
                double price = productCursor.getDouble(priceIndex);
                int quantity = productCursor.getInt(quantityIndex);
                String size = productCursor.getString(sizeIndex);


                products.add(new Product(productName, (int) price, quantity, size));
            } while (productCursor.moveToNext());
            productCursor.close();
        }

        return products;
    }



}
