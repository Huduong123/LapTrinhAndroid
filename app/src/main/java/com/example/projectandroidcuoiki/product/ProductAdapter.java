package com.example.projectandroidcuoiki.product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroidcuoiki.activity.ProductDetailActivity;
import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shoe, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("%,d VNĐ", product.getPrice()));
        holder.productDescription.setText(product.getDescription());
        holder.productQuantity.setText("Số lượng: " + product.getQuantity());
        holder.productImage.setImageResource(product.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("selected_product", product);
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Lớp ViewHolder
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription, productQuantity;
        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productDescription = itemView.findViewById(R.id.product_description);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }

    public void updateData(List<Product> newProductList) {
        productList.clear(); // Xóa danh sách hiện tại
        productList.addAll(newProductList); // Thêm danh sách mới
        notifyDataSetChanged(); // Thông báo thay đổi dữ liệu
    }
}
