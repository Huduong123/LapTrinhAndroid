package com.example.projectandroidcuoiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.database.DatabaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateUserInfo extends AppCompatActivity {

    DatabaseUser db;
    private ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        db = new DatabaseUser(this);

        EditText usernameUpdate = findViewById(R.id.user_name_update);
        EditText gmailUpdate = findViewById(R.id.gmail_update);
        EditText nameUpdate = findViewById(R.id.name_update);
        EditText phoneUpdate = findViewById(R.id.phone_update);
        EditText addressUpdate = findViewById(R.id.address_update);
        Button updateButton = findViewById(R.id.update_info_user_button);
        backButton = findViewById(R.id.back_button_update);

        backButton.setOnClickListener(v -> {
            finish();
        });


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String gmail = intent.getStringExtra("gmail");

        if (username != null) {
            usernameUpdate.setText(username);
        } else {
            usernameUpdate.setText("Không có username");
        }
        if (gmail != null) {
            gmailUpdate.setText(gmail);
        } else {
            gmailUpdate.setText("Không có Gmail");
        }
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameUpdate.getText().toString().trim();
                String email = gmailUpdate.getText().toString().trim();
                String hovaten = nameUpdate.getText().toString().trim();
                String sodienthoai = phoneUpdate.getText().toString().trim();
                String diachi = addressUpdate.getText().toString().trim();


                if (username.isEmpty() || email.isEmpty() || hovaten.isEmpty() || sodienthoai.isEmpty() || diachi.isEmpty()) {
                    Toast.makeText(UpdateUserInfo.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidUsername(username)) {
                    Toast.makeText(UpdateUserInfo.this, "Username không được chứa ký tự đặc biệt!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(UpdateUserInfo.this, "Email không đúng định dạng hoặc không phải Gmail!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (db.isEmailTakenByOthers(email, username)) {
                    Toast.makeText(UpdateUserInfo.this, "Email này đã được sử dụng bởi người khác!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidName(hovaten)) {
                    Toast.makeText(UpdateUserInfo.this, "Họ và tên không được chứa số hoặc ký tự đặc biệt!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidPhoneNumber(sodienthoai)) {
                    Toast.makeText(UpdateUserInfo.this, "Số điện thoại phải gồm 10 chữ số!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidAddress(diachi)) {
                    Toast.makeText(UpdateUserInfo.this, "Địa chỉ không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }


                boolean isUpdated = db.updateUser(username, email, hovaten, sodienthoai, diachi);

                if (isUpdated) {
                    Toast.makeText(UpdateUserInfo.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                    // Trả kết quả cho UserInfo
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("username", username); // Truyền username đã cập nhật
                    setResult(RESULT_OK, resultIntent);

                    finish();
                } else {
                    Toast.makeText(UpdateUserInfo.this, "Cập nhật thất bại! Vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_]+$");
    }


    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        Pattern pat = Pattern.compile(emailRegex);
        return email != null && pat.matcher(email).matches();
    }


    private boolean isValidName(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]+$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }


    private boolean isValidPhoneNumber(String phone) {
        return phone.length() == 10 && phone.matches("[0-9]+");
    }

    private boolean isValidAddress(String address) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s.,;]+$");
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }
}
