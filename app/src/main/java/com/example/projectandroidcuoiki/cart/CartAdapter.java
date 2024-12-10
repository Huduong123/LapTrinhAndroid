package com.example.projectandroidcuoiki.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.model.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context context;
    private final List<Product> productList;
    private final Runnable updateTotalCallback;

    public CartAdapter(Context context, List<Product> productList, Runnable updateTotalCallback) {
        this.context = context;
        this.productList = productList;
        this.updateTotalCallback = updateTotalCallback;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productImage.setImageResource(product.getImageResId());
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("Giá: %,d VNĐ", product.getPrice()));
        holder.productSize.setText(String.format("Size: %s", product.getSize()));
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));
        holder.totalPrice.setText(String.format("Tổng: %,d VNĐ", product.getPrice() * product.getQuantity()));


        holder.increaseButton.setOnClickListener(v -> {
            if (product.getQuantity() < product.getMaxStock()) {
                product.setQuantity(product.getQuantity() + 1);
                CartManager.getInstance().updateQuantity(product, product.getQuantity());
                notifyItemChanged(position);
                updateTotalCallback.run();
            } else {
                Toast.makeText(context, "Hết hàng, không thể tăng thêm!", Toast.LENGTH_SHORT).show();
            }
        });


        holder.decreaseButton.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                CartManager.getInstance().updateQuantity(product, product.getQuantity());
                notifyItemChanged(position);
            } else {
                // Xóa sản phẩm khỏi danh sách nếu số lượng là 0
                productList.remove(position);
                CartManager.getInstance().removeItem(product);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productList.size());
            }
            updateTotalCallback.run();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productSize, productQuantity, totalPrice, tamtinh, phivanchuyen;
        Button increaseButton, decreaseButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.cart_product_image);
            productName = itemView.findViewById(R.id.cart_product_name);
            productPrice = itemView.findViewById(R.id.cart_product_price);
            productSize = itemView.findViewById(R.id.cart_product_size);
            productQuantity = itemView.findViewById(R.id.cart_product_quantity);
            totalPrice = itemView.findViewById(R.id.cart_product_total_price);
            increaseButton = itemView.findViewById(R.id.btn_increase);
            decreaseButton = itemView.findViewById(R.id.btn_decrease);
        }
    }

    public List<Product> getProductList() {
        return productList;
    }
}
