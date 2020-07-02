package com.example.petsandroid;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<FlowerViewHolder> {

    private Context mContext;
    private List<ProductData> mFlowerList;

    public MyAdapter(Context mContext, List<ProductData> mFlowerList) {
        this.mContext = mContext;
        this.mFlowerList = mFlowerList;
    }

    @Override
    public FlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row_item, parent, false);
        return new FlowerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final FlowerViewHolder holder, int position) {
        holder.mImage.setImageResource(mFlowerList.get(position).getProductImage());
        holder.mTitle.setText(mFlowerList.get(position).getProductName());
        holder.price.setText(String.valueOf(mFlowerList.get(position).getProductPrice()));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, DetailActivity.class);
                mIntent.putExtra("Title", mFlowerList.get(holder.getAdapterPosition()).getProductName());
                mIntent.putExtra("Description", mFlowerList.get(holder.getAdapterPosition()).getProductDescription());
                mIntent.putExtra("Image", mFlowerList.get(holder.getAdapterPosition()).getProductImage());
                mIntent.putExtra("price", mFlowerList.get(holder.getAdapterPosition()).getProductPrice());

                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFlowerList.size();
    }
}

class FlowerViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle,price;
    CardView mCardView;

    FlowerViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        price=itemView.findViewById(R.id.price);
        mCardView = itemView.findViewById(R.id.cardview);
    }
}
