package com.example.projectandroidcuoiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.database.DatabaseUser;
import com.example.projectandroidcuoiki.databinding.ActivityRegisterBinding;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {





    ActivityRegisterBinding binding;
    DatabaseUser databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityRegisterBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());





        databaseUser = new DatabaseUser(this);

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String username = binding.editTextUsername.getText().toString();
                    String email = binding.editTextEmail.getText().toString();
                    String password = binding.editTextPassword.getText().toString();
                    String confirmPass = binding.editTextConfirmPass.getText().toString();

                    // Kiểm tra các trường hợp nhập liệu
                    if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else if (!isValidEmail(email)) {
                        binding.editTextEmail.setError("Email không hợp lệ");
                    } else if (databaseUser.checkEmailExists(email)) {
                        binding.editTextEmail.setError("Email đã tồn tại trong hệ thống");
                    } else if (!isValidUsername(username)) {
                        binding.editTextUsername.setError("Tên đăng nhập chỉ chứa chữ cái và số, không có ký tự đặc biệt");
                    } else if (databaseUser.checkUsername(username)) {
                        binding.editTextUsername.setError("Tên đăng nhập đã tồn tại");
                    } else if (!isValidPassword(password)) {
                        binding.editTextPassword.setError("Mật khẩu phải có chữ cái đầu viết hoa, bao gồm chữ, số và ít nhất 1 ký tự đặc biệt");
                    } else if (!password.equals(confirmPass)) {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean insert = databaseUser.insertUser(username, email, password);
                        if (insert) {
                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    // Catch any unexpected exceptions to prevent the app from crashing
                    Toast.makeText(RegisterActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace(); // Log the error for debugging purposes
                }
            }
        });








        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // Kiểm tra định dạng email
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return email != null && pat.matcher(email).matches();
    }
    // Kiểm tra tên đăng nhập chỉ chứa chữ cái và số
    public static boolean isValidUsername(String username) {
        return Pattern.matches("^[a-zA-Z0-9]+$", username);
    }

    // Kiểm tra mật khẩu có chữ cái đầu viết hoa, bao gồm chữ, số và ít nhất 1 ký tự đặc biệt
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return Pattern.matches(passwordRegex, password);
    }


}