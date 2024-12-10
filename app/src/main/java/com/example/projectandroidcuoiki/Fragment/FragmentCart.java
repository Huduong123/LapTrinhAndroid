package com.example.projectandroidcuoiki.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroidcuoiki.cart.CartAdapter;
import com.example.projectandroidcuoiki.cart.CartManager;
import com.example.projectandroidcuoiki.activity.PaymentDetail;
import com.example.projectandroidcuoiki.model.Product;
import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.activity.UserInfo;
import com.example.projectandroidcuoiki.database.DatabaseUser;

import java.util.List;

public class FragmentCart extends Fragment {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private TextView subtotalAmountTextView, deliveryFeeTextView, totalAmountTextView;
    private Button checkoutButton;
    String username = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);

        subtotalAmountTextView = view.findViewById(R.id.subtotal);
        deliveryFeeTextView = view.findViewById(R.id.delivery_fee);
        totalAmountTextView = view.findViewById(R.id.total);
        checkoutButton = view.findViewById(R.id.checkout_button);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Product> cartItems = CartManager.getInstance().getCartItems();


        // Tạo adapter và hiển thị danh sách
        cartAdapter = new CartAdapter(getContext(), cartItems, this::updateTotalAmount);
        cartRecyclerView.setAdapter(cartAdapter);

        if (cartItems.isEmpty()) {
            checkoutButton.setEnabled(false);
            Toast.makeText(getContext(), "Giỏ hàng trống, vui lòng thêm sản phẩm.", Toast.LENGTH_SHORT).show();
        } else {
            checkoutButton.setEnabled(true);
            updateTotalAmount();
        }


        // Nhận username từ Bundle
        Bundle args = getArguments();
        if (args != null) {
            username = args.getString("username");
        }

        checkoutButton.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                if (isUserInfoComplete(username)) {

                    int totalAmount = CartManager.getInstance().calculateTotal() + CartManager.getInstance().calculateDeliveryFee();

                    Intent intent = new Intent(getContext(), PaymentDetail.class);
                    intent.putExtra("TOTAL_AMOUNT", totalAmount);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {

                    Toast.makeText(getContext(), "Thông tin cá nhân chưa đầy đủ. Vui lòng cập nhật trong Hồ sơ.", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getContext(), UserInfo.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
    private void updateTotalAmount() {
        int subtotal = CartManager.getInstance().calculateTotal();
        int deliveryFee = CartManager.getInstance().calculateDeliveryFee();
        int total = subtotal + deliveryFee;

        subtotalAmountTextView.setText(String.format("Tạm tính: %,d VNĐ", subtotal));
        deliveryFeeTextView.setText(String.format("Phí vận chuyển: %,d VNĐ", deliveryFee));
        totalAmountTextView.setText(String.format("Tổng cộng: %,d VNĐ", total));
    }


    private boolean isUserInfoComplete(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        DatabaseUser databaseUser = new DatabaseUser(getContext());
        Cursor cursor = databaseUser.getUserInfo(username);

        if (cursor != null && cursor.moveToFirst()) {

            String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("hovaten"));
            String soDienThoai = cursor.getString(cursor.getColumnIndexOrThrow("sodienthoai"));
            String diaChi = cursor.getString(cursor.getColumnIndexOrThrow("diachi"));

            cursor.close();


            return hoTen != null && !hoTen.isEmpty()
                    && soDienThoai != null && !soDienThoai.isEmpty()
                    && diaChi != null && !diaChi.isEmpty();
        }

        if (cursor != null) {
            cursor.close();
        }

        return false;
    }

}
