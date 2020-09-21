package com.example.petsandroid;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsandroid.Admin.AdminMaintainProductsActivity;
import com.example.petsandroid.Model.Cart;
import com.example.petsandroid.Model.Products;
import com.example.petsandroid.Model.WishList;
import com.example.petsandroid.Prevalent.Prevalent;
import com.example.petsandroid.ViewHolder.CartViewHolder;
import com.example.petsandroid.ViewHolder.WishListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class WishListActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);


        recyclerView = findViewById(R.id.wish_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }


    @Override
    protected void onStart()
    {
        super.onStart();



        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Wish List");

        FirebaseRecyclerOptions<WishList> options =
                new FirebaseRecyclerOptions.Builder<WishList>()
                        .setQuery(cartListRef.child("User View")
                                .child(Prevalent.currentOnlineUser.getPhone())
                                .child("Products"), WishList.class)
                        .build();

        FirebaseRecyclerAdapter<WishList, WishListViewHolder> adapter
                = new FirebaseRecyclerAdapter<WishList, WishListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull WishListViewHolder holder, int position, @NonNull final WishList model)
            {

               String pid=model.getPid();
            Log.d("wishlist act pid value",pid);

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();


                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.child("Products").child(pid).exists())
                        {
                            Products usersData = dataSnapshot.child("Products").child(pid).getValue(Products.class);

                            Picasso.get().load(usersData.getImage()).into(holder.primage);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(WishListActivity.this, " Error with your code", Toast.LENGTH_SHORT).show();

                    }
                });

                holder.txtProductDes.setText("Desc = " + model.getDescrip());
                holder.txtProductPrice.setText("Price " + model.getPrice() + "$");
                holder.txtProductName.setText(model.getPname());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "View Product Details",
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(WishListActivity.this);
                        builder.setTitle("WishList Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if (i == 0)
                                {
                                    Intent intent = new Intent(WishListActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                                if (i == 1)
                                {
                                    cartListRef.child("User View")
                                            .child(Prevalent.currentOnlineUser.getPhone())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(WishListActivity.this, "Item removed successfully.", Toast.LENGTH_SHORT).show();

//                                                        Intent intent = new Intent(WishListActivity.this, HomeActivity.class);
//                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });

//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view)
//                    {
//
//
//                            Intent intent = new Intent(WishListActivity.this, ProductDetailsActivity.class);
//                            intent.putExtra("pid", model.getPid());
//                            startActivity(intent);
//
//                    }
//                });


            }

            @NonNull
            @Override
            public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wish_items_layout, parent, false);
                WishListViewHolder holder = new WishListViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }




}
