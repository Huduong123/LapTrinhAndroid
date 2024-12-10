package com.example.projectandroidcuoiki.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroidcuoiki.R;
import com.example.projectandroidcuoiki.model.Order;
import com.example.projectandroidcuoiki.model.Product;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private List<Order> orders;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        // Set thông tin cơ bản của đơn hàng
        holder.tvOrderId.setText("Mã đơn: " + order.getOrderId());
        holder.tvOrderDate.setText("Ngày đặt hàng: " + order.getOrderDate());
        holder.tvCustomerName.setText("Họ và Tên: " + order.getCustomerName());
        holder.tvPhoneNumber.setText("SĐT: " + order.getPhoneNumber());
        holder.tvAddress.setText("Địa chỉ: " + order.getAddress());
        holder.tvTotalOrder.setText("Tiền đơn hàng: " + String.format("%,.0f VNĐ", order.getTotalOrder()));


        // Ẩn container sản phẩm ban đầu
        holder.productDetailsContainer.setVisibility(View.GONE);

        // Xử lý sự kiện nhấn vào nút mở rộng
        holder.ivExpand.setOnClickListener(v -> {
            if (holder.productDetailsContainer.getVisibility() == View.GONE) {
                holder.productDetailsContainer.setVisibility(View.VISIBLE);
                populateProducts(holder.productDetailsContainer, order.getProductList());
            } else {
                holder.productDetailsContainer.setVisibility(View.GONE);
                holder.productDetailsContainer.removeAllViews(); // Xóa các view cũ
            }
        });
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    /**
     * Hàm thêm danh sách sản phẩm vào container
     */
    private void populateProducts(LinearLayout container, List<Product> products) {
        container.removeAllViews();

        for (Product product : products) {

            View productView = LayoutInflater.from(context).inflate(R.layout.item_order_product, container, false);

            TextView productName = productView.findViewById(R.id.product_name);
            TextView productPrice = productView.findViewById(R.id.product_price);
            TextView productQuantity = productView.findViewById(R.id.product_quantity);
            TextView productSize = productView.findViewById(R.id.product_sizes);

            productName.setText("Sản phẩm: " + product.getName());
            productPrice.setText("Đơn giá: " + String.format("%,.0f VNĐ", (double) product.getPrice()));

            productQuantity.setText("Số lượng: " + product.getQuantity());
            productSize.setText("Size: " + product.getSize());

            container.addView(productView);
        }
    }

    /**
     * ViewHolder cho OrderAdapter
     */
    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate, tvCustomerName, tvPhoneNumber, tvAddress, tvTotalOrder;
        LinearLayout productDetailsContainer;
        ImageView ivExpand;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvOrderDate = itemView.findViewById(R.id.tv_time);
            tvCustomerName = itemView.findViewById(R.id.tv_customer_name);
            tvPhoneNumber = itemView.findViewById(R.id.tv_phone_number);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvTotalOrder = itemView.findViewById(R.id.tv_total_order); // TextView hiển thị tổng tiền
            productDetailsContainer = itemView.findViewById(R.id.product_details_container);
            ivExpand = itemView.findViewById(R.id.iv_expand);
        }
    }
}
