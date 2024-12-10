package com.example.projectandroidcuoiki.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectandroidcuoiki.activity.InfoOrder;
import com.example.projectandroidcuoiki.activity.LoginActivity;
import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.activity.UserInfo;
import com.example.projectandroidcuoiki.database.DatabaseUser;


public class FragmentUser extends Fragment {
    private TextView userNameTextView;
    private Button buttonLogout, buttonProfile, buttonOrder,buttonChangePassword;
    String username = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userNameTextView = view.findViewById(R.id.user_name);
        buttonLogout= view.findViewById(R.id.logout_button);
        buttonProfile = view.findViewById(R.id.my_profile_button);
        buttonOrder = view.findViewById(R.id.orders_button);

        // Lấy username từ Bundle hoặc SharedPreferences

        if (getArguments() != null) {
            username = getArguments().getString("username");
        }
        if (username == null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("username", "Tên người dùng");
        }
        userNameTextView.setText(username);



        buttonLogout.setOnClickListener(v -> {
            // Xóa thông tin người dùng khỏi SharedPreferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("username");
            editor.remove("user_id");
            editor.apply();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        });



        buttonProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UserInfo.class);
            intent.putExtra("username", userNameTextView.getText().toString());
            startActivity(intent);
        });


        buttonOrder.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("user_id", -1);

            Intent intent = new Intent(getActivity(), InfoOrder.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        buttonChangePassword = view.findViewById(R.id.change_pass_button);
        buttonChangePassword.setOnClickListener(v -> showChangePasswordDialog(username));
        return view;
    }

    private void showChangePasswordDialog(String username) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_change_password, null);

        EditText editTextCurrentPassword = dialogView.findViewById(R.id.editTextCurrentPassword);
        EditText editTextNewPassword = dialogView.findViewById(R.id.editTextNewPassword);
        EditText editTextConfirmPassword = dialogView.findViewById(R.id.editTextConfirmPassword);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView)
                .setTitle("Đổi mật khẩu")
                .setPositiveButton("Xác nhận", (dialog, which) -> {
                    String currentPassword = editTextCurrentPassword.getText().toString();
                    String newPassword = editTextNewPassword.getText().toString();
                    String confirmPassword = editTextConfirmPassword.getText().toString();

                    if (newPassword.equals(confirmPassword)) {
                        DatabaseUser databaseUser = new DatabaseUser(getContext());

                        // Gọi changePassword để kiểm tra và cập nhật
                        boolean isUpdated = databaseUser.changePassword(username, currentPassword, newPassword);

                        if (isUpdated) {
                            AlertDialog.Builder logoutDialog = new AlertDialog.Builder(getContext());
                            logoutDialog.setTitle("Thông báo")
                                    .setMessage("Đổi mật khẩu thành công! Vui lòng đăng nhập lại.")
                                    .setPositiveButton("OK", (logoutDialogInterface, i) -> {

                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.remove("username");
                                        editor.remove("user_id");
                                        editor.apply();

                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        getActivity().finish();
                                    })
                                    .setCancelable(false)
                                    .create()
                                    .show();
                        } else if (currentPassword.equals(newPassword)) {
                            // Hiển thị thông báo nếu mật khẩu mới trùng với mật khẩu hiện tại
                            Toast.makeText(getContext(), "Mật khẩu mới không được trùng với mật khẩu hiện tại.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Mật khẩu hiện tại không đúng.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Mật khẩu mới không khớp!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .create()
                .show();
    }



}