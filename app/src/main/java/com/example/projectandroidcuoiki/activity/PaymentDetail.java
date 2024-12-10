package com.example.projectandroidcuoiki.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.cart.CartManager;
import com.example.projectandroidcuoiki.database.DatabaseUser;
import com.example.projectandroidcuoiki.model.Product;
import com.example.projectandroidcuoiki.product.ProductManager;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentDetail extends AppCompatActivity {

    private EditText nameInput, phoneInput, addressInput;
    private TextView totalAmountTextView;
    private Button orderButton;
    private DatabaseUser databaseUser;
    private ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        nameInput = findViewById(R.id.name_input);
        phoneInput = findViewById(R.id.phone_input);
        addressInput = findViewById(R.id.address_input);
        totalAmountTextView = findViewById(R.id.total_amount);
        orderButton = findViewById(R.id.order_button);
        backButton = findViewById(R.id.back_button);

        databaseUser = new DatabaseUser(this);


        String username = getIntent().getStringExtra("username");

        loadUserData(username);

        int totalAmount = getIntent().getIntExtra("TOTAL_AMOUNT", 0);

        totalAmountTextView.setText(String.format("Tổng tiền: %,d VNĐ", totalAmount));


        backButton.setOnClickListener(v -> {
            finish();
        });
        orderButton.setOnClickListener(v -> {
            if (validateInputs()) {
                showConfirmationDialog(username); // Hiển thị hộp thoại xác nhận
            }
        });

    }
    private void showConfirmationDialog(String username) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Xác nhận đặt hàng")
                .setMessage("Bạn có chắc chắn muốn đặt hàng không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    placeOrder(username); // Gọi phương thức xử lý đặt hàng
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private void placeOrder(String username) {
        Cursor userCursor = databaseUser.getUserInfo(username);
        if (userCursor != null && userCursor.moveToFirst()) {
            int userId = userCursor.getInt(userCursor.getColumnIndexOrThrow("id"));
            List<Product> cartItems = CartManager.getInstance().getCartItems();

            if (!cartItems.isEmpty()) {
                SQLiteDatabase db = databaseUser.getWritableDatabase();
                ContentValues orderValues = new ContentValues();
                orderValues.put("user_id", userId);
                long orderId = db.insert("user_orders", null, orderValues);

                if (orderId != -1) {
                    boolean allOrdersAdded = true;

                    for (Product product : cartItems) {
                        boolean isOrderAdded = databaseUser.insertOrder(
                                product.getName(),
                                product.getPrice(),
                                product.getQuantity(),
                                product.getSize(),
                                (int) orderId,
                                userId
                        );
                        if (!isOrderAdded) {
                            allOrdersAdded = false;
                            break;
                        }
                        ProductManager.getInstance().updateProductQuantity(product.getName(), product.getQuantity());
                    }

                    if (allOrdersAdded) {
                        Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                        CartManager.getInstance().getCartItems().clear();

                        Intent intent = new Intent(PaymentDetail.this, HomeActivity.class);
                        intent.putExtra("purchased_products", (Serializable) cartItems);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Đặt hàng thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Không thể tạo đơn hàng. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Giỏ hàng rỗng. Vui lòng thêm sản phẩm.", Toast.LENGTH_SHORT).show();
            }
        }
        if (userCursor != null) {
            userCursor.close();
        }
    }




    private void loadUserData(String username) {
        if (username == null || username.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cursor = databaseUser.getUserInfo(username);
        if (cursor != null && cursor.moveToFirst()) {

            String hovaten = cursor.getString(cursor.getColumnIndexOrThrow("hovaten"));
            String sodienthoai = cursor.getString(cursor.getColumnIndexOrThrow("sodienthoai"));
            String diachi = cursor.getString(cursor.getColumnIndexOrThrow("diachi"));

            nameInput.setText(hovaten);
            phoneInput.setText(sodienthoai);
            addressInput.setText(diachi);

            cursor.close();
        } else {
            Toast.makeText(this, "Không tìm thấy dữ liệu người dùng", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInputs() {
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();

        if (!isValidName(name)) {
            nameInput.setError("Họ tên không chứa ký tự đặc biệt");
            return false;
        }

        if (!isValidPhoneNumber(phone)) {
            phoneInput.setError("Số điện thoại phải có 10 chữ số");
            return false;
        }

        if (!isValidAddress(address)) {
            addressInput.setError("Địa chỉ không chứa ký tự đặc biệt");
            return false;
        }

        // Nếu tất cả các điều kiện hợp lệ
        return true;
    }

    // Kiểm tra họ tên không chứa ký tự đặc biệt
    private boolean isValidName(String name) {
        // Biểu thức chính quy cho phép chỉ có chữ cái và dấu cách
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    // Kiểm tra số điện thoại gồm 10 chữ số
    private boolean isValidPhoneNumber(String phone) {
        // Biểu thức chính quy kiểm tra số điện thoại đúng 10 chữ số
        return phone.length() == 10 && phone.matches("[0-9]+");
    }

    // Kiểm tra địa chỉ không có ký tự đặc biệt nhưng có thể có dấu chấm phẩy và dấu phẩy
    private boolean isValidAddress(String address) {
        // Biểu thức chính quy cho phép ký tự chữ cái, số, khoảng trắng, dấu chấm, dấu phẩy và dấu chấm phẩy
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s.,;]+$");
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

}
