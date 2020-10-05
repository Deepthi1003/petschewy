package com.example.petsandroid.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.petsandroid.Interface.ItemClickListner;
import com.example.petsandroid.R;

public class WishListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductPrice, txtProductDes;
    private ItemClickListner ClickLis;
public ImageView primage;

    public WishListViewHolder(View itemView)
    {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductPrice = itemView.findViewById(R.id.product_price);
        txtProductDes = itemView.findViewById(R.id.product_description);
       primage=itemView.findViewById(R.id.product_image);
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
