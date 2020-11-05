package com.example.petsandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsandroid.Model.AdminOrders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final ArrayList<AdminOrders> list;

    public OrderAdapter(ArrayList<AdminOrders> list) {
        this.list = list;
    }

//    public OrderAdapter(Object value) {
//    }


    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_orders_layout,parent,false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {

        //Map<String,Object> map = (Map<String, Object>) list.get(position);
        AdminOrders orders = new AdminOrders(list.get(position));
        //Log.d("name", map.get()e());
        holder.userName.setText(orders.getName());
        holder.userDateTime.setText(orders.getDate()+" "+orders.getTime());
        holder.userPhoneNumber.setText(orders.getPhone());
        holder.userTotalPrice.setText(orders.getTotalAmount());
        holder.userShippingAddress.setText(orders.getAddress()+" "+orders.getCity()+" "+orders.getState());

//        holder.userName.setText("sdfgh");
//        holder.userDateTime.setText("dfghj");
//        holder.userPhoneNumber.setText("dfghjk");
//        holder.userTotalPrice.setText("wertyu");
//        holder.userShippingAddress.setText("sdfgh");
//        System.out.println("size: "+getItemCount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{

        public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
        public Button ShowOrdersBtn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userShippingAddress = itemView.findViewById(R.id.order_address_city);
            ShowOrdersBtn = itemView.findViewById(R.id.show_all_products_btn);
        }
    }
}
