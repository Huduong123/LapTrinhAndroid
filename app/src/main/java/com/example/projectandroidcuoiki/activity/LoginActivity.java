package com.example.projectandroidcuoiki.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.database.DatabaseUser;
import com.example.projectandroidcuoiki.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding binding;
    DatabaseUser databaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        databaseUser = new DatabaseUser(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.editTextUsername.getText().toString();
                String password = binding.editTextPassword.getText().toString();

                if(username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(password.length() < 6){
                    binding.editTextPassword.setError("Mật khẩu phải có ít nhất 6 kí tự");
                }
                else{
                    Boolean checkUserPass = databaseUser.checkUsernamePassword(username,password);
                    if (checkUserPass) {
                        int userId = databaseUser.getUserId(username); // Thêm phương thức để lấy user_id từ username
                        if (userId != -1) {
                            // Lưu user_id và username vào SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("user_id", userId);
                            editor.putString("username", username);
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("username", username); // Truyền thêm thông tin nếu cần
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });






        // Xử lý nút Quên mật khẩu
        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Vui lòng liên hệ 0763554775 để lấy lại mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });



        binding.btnRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
            startActivity(intent);
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }




}