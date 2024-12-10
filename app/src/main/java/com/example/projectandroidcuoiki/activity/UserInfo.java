package com.example.projectandroidcuoiki.activity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.database.DatabaseUser;

public class UserInfo extends AppCompatActivity {

    private ImageView backButton;
    private Button updateInfoButton;
    private TextView userNameTextView, gmailTextView, hoTenTextView, userPhoneTextView, userAddressTextView;
    private DatabaseUser databaseUser;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info);


        backButton = findViewById(R.id.back_button_info);
        updateInfoButton = findViewById(R.id.update_info_button);
        userNameTextView = findViewById(R.id.user_name);
        gmailTextView = findViewById(R.id.gmail);
        hoTenTextView = findViewById(R.id.ho_ten);
        userPhoneTextView = findViewById(R.id.user_phone);
        userAddressTextView = findViewById(R.id.user_address);

        databaseUser = new DatabaseUser(this);

        Intent intent = getIntent();
         username = intent.getStringExtra("username");

        if (username != null) {
            userNameTextView.setText(username);
            loadUserInfo(username);
        } else {
            userNameTextView.setText("Không có username");
        }



        updateInfoButton.setOnClickListener(v -> {
            Intent updateIntent = new Intent(UserInfo.this, UpdateUserInfo.class);
            updateIntent.putExtra("username", username);
            String gmail = gmailTextView.getText().toString();
            updateIntent.putExtra("gmail", gmail);
            startActivityForResult(updateIntent, 1);
        });




        backButton.setOnClickListener(v -> finish());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadUserInfo(String username) {
        Cursor cursor = databaseUser.getUserInfo(username);
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy index của các cột
            int emailIndex = cursor.getColumnIndex("email");
            int nameIndex = cursor.getColumnIndex("hovaten");
            int phoneIndex = cursor.getColumnIndex("sodienthoai");
            int addressIndex = cursor.getColumnIndex("diachi");


            if (emailIndex != -1) {
                String email = cursor.getString(emailIndex);
                gmailTextView.setText(email != null && !email.isEmpty() ? email : "Chưa có email");
            }

            if (nameIndex != -1) {
                String hoVaTen = cursor.getString(nameIndex);
                hoTenTextView.setText(hoVaTen != null && !hoVaTen.isEmpty() ? hoVaTen : "Chưa có họ và tên");
            }

            if (phoneIndex != -1) {
                String soDienThoai = cursor.getString(phoneIndex);
                userPhoneTextView.setText(soDienThoai != null && !soDienThoai.isEmpty() ? soDienThoai : "Chưa có số điện thoại");
            }

            if (addressIndex != -1) {
                String diaChi = cursor.getString(addressIndex);
                userAddressTextView.setText(diaChi != null && !diaChi.isEmpty() ? diaChi : "Chưa có địa chỉ");
            }
        } else {
            // Nếu không có dữ liệu cho username
            Log.e("UserInfo", "Không tìm thấy dữ liệu cho username: " + username);
            gmailTextView.setText("Chưa có email");
            hoTenTextView.setText("Chưa có họ và tên");
            userPhoneTextView.setText("Chưa có số điện thoại");
            userAddressTextView.setText("Chưa có địa chỉ");
        }

        if (cursor != null) {
            cursor.close();
        }else {
                Log.e("UserInfo", "Không tìm thấy dữ liệu trong bảng user cho username: " + username);
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) { // Kiểm tra mã yêu cầu và kết quả thành công
            if (data != null) {
                String updatedUsername = data.getStringExtra("username"); // Lấy username từ Intent
                if (updatedUsername != null) {
                    username = updatedUsername; // Cập nhật username nếu cần
                    loadUserInfo(username); // Tải lại dữ liệu từ cơ sở dữ liệu
                }
            }
        }
    }

}
