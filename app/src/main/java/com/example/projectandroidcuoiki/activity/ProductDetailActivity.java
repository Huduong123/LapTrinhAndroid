package com.example.projectandroidcuoiki.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.cart.CartManager;
import com.example.projectandroidcuoiki.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productDescription, productQuantity;
    private RadioGroup sizeRadioGroup;
    private Button orderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);
        productQuantity = findViewById(R.id.product_quantity);
        sizeRadioGroup = findViewById(R.id.size_radio_group);
        orderButton = findViewById(R.id.order_button);

        // Nhận dữ liệu sản phẩm từ Intent
        Product product = (Product) getIntent().getSerializableExtra("selected_product");

        if (product != null) {
            productImage.setImageResource(product.getImageResId());
            productName.setText(product.getName());
            productPrice.setText(String.format("%,d VNĐ", product.getPrice()));
            productDescription.setText(product.getDescription());
            productQuantity.setText("Số lượng: " + product.getQuantity());
        }

        orderButton.setOnClickListener(v -> {
            int selectedSizeId = sizeRadioGroup.getCheckedRadioButtonId();
            if (selectedSizeId == -1) {
                Toast.makeText(this, "Vui lòng chọn size trước khi đặt hàng!", Toast.LENGTH_SHORT).show();
            } else {
                String size = ((TextView) findViewById(selectedSizeId)).getText().toString();
                if (product != null) {
                    // Lấy số lượng hiện tại trong giỏ hàng
                    int currentQuantity = CartManager.getInstance().getCurrentQuantity(product.getName(), size);

                    // Kiểm tra nếu số lượng đã đạt tối đa
                    if (currentQuantity >= product.getMaxStock()) {
                        Toast.makeText(this, "Hết hàng, không thể đặt thêm sản phẩm này!", Toast.LENGTH_SHORT).show();
                    } else {

                        Product selectedProduct = new Product(
                                product.getName(),
                                product.getPrice(),
                                product.getImageResId(),
                                size,
                                1,
                                product.getMaxStock()
                        );
                        CartManager.getInstance().addToCart(selectedProduct);
                        Toast.makeText(this, "Đã thêm " + product.getName()+" vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Nút quay lại
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }
}
