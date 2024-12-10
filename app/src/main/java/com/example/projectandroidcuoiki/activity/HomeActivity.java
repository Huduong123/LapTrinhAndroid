package com.example.projectandroidcuoiki.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectandroidcuoiki.Fragment.FragmentCart;
import com.example.projectandroidcuoiki.Fragment.FragmentHome;
import com.example.projectandroidcuoiki.Fragment.FragmentNotify;
import com.example.projectandroidcuoiki.Fragment.FragmentUser;
import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_activity);

        // Lưu username vào SharedPreferences
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", username);
            editor.apply();
        }

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        String navigateToFragment = getIntent().getStringExtra("navigateToFragment");

        @SuppressWarnings("unchecked")
        List<Product> purchasedProducts = (List<Product>) getIntent().getSerializableExtra("purchased_products");


        if (savedInstanceState == null) {
            FragmentHome fragmentHome = new FragmentHome();


            if (purchasedProducts != null && !purchasedProducts.isEmpty()) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("purchased_products", (Serializable) purchasedProducts);
                fragmentHome.setArguments(bundle);
            }

            // Mở FragmentHome hoặc fragment mặc định
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragmentHome)
                    .commit();
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                androidx.fragment.app.Fragment fragment = null;
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "Tên người dùng");

                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    fragment = new FragmentHome();
                } else if (id == R.id.navigation_dashboard) {
                    fragment = new FragmentCart();
                } else if (id == R.id.navigation_notifications) {
                    fragment = new FragmentNotify();
                } else if (id == R.id.navigation_user) {
                    fragment = new FragmentUser();
                }

                if (fragment != null) {
                    // Truyền username qua Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    fragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
                return true;
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}