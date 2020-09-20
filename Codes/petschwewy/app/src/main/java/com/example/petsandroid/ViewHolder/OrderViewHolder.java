package com.example.petsandroid.ViewHolder;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.petsandroid.Interface.ItemClickListner;
import com.example.petsandroid.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtorderDate, txtOrderStatus,txtOrderAddress;
    private ItemClickListner ClickLis;

    public OrderViewHolder(View itemView)
    {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtorderDate = itemView.findViewById(R.id.order_date);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderAddress=itemView.findViewById(R.id.order_address);
    }

    @Override
    public void onClick(View view)
    {

        ClickLis.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.ClickLis = itemClickListner;
    }


}
