package com.example.petsandroid;



import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsandroid.Admin.AdminCategoryActivity;
import com.example.petsandroid.Model.Cart;
import com.example.petsandroid.Model.Products;
import com.example.petsandroid.Model.Users;
import com.example.petsandroid.Prevalent.Prevalent;
import com.example.petsandroid.R;
import com.example.petsandroid.ViewHolder.CartViewHolder;
import com.example.petsandroid.ViewHolder.OrderProdViewHolder;
import com.example.petsandroid.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderProductsActivity extends AppCompatActivity
{
    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef,prod;

    private String userID = "";
    ArrayList<String> category;

     ArrayList<String> alarmList;
    public String productName,productPrice,productDes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);


        userID = getIntent().getStringExtra("uid");


        productsList = findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);
           // category=new ArrayList<String>();
            cartListRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone()).child(userID).child("Products");


        prod = FirebaseDatabase.getInstance().getReference()
                .child("Products");


        getAlarmLlist();


    }

    private void getAlarmLlist() {
        cartListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    alarmList=new ArrayList();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String alarm= postSnapshot.getValue(String.class);
                        alarmList.add(alarm);

                        Log.d("product is confirm", String.valueOf(alarmList));
                        System.out.println(alarmList);


//                        Query query = prod.equalTo(alarm);
//
//                        options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(query, Products.class).build();


                            FirebaseRecyclerOptions<Products> options =
                                    new FirebaseRecyclerOptions.Builder<Products>()
                                            .setQuery(prod, Products.class)
                                            .build();



        FirebaseRecyclerAdapter<Products, OrderProdViewHolder> adapter = new FirebaseRecyclerAdapter<Products, OrderProdViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderProdViewHolder holder, int position, @NonNull Products model) {

                for(int i=0;i<alarmList.size();i++){

                    if(alarmList.get(i).equalsIgnoreCase(model.getPid()))
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                    }


                }

//                holder.txtProductName.setText(model.getPname());
//                        holder.txtProductDescription.setText(model.getDescription());
//                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");

            }

            @NonNull
            @Override
            public OrderProdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_prod_item, parent, false);
                OrderProdViewHolder holder = new OrderProdViewHolder(view);


                return holder;
            }
        };

        productsList.setAdapter(adapter);
        adapter.startListening();



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               // Log.w(TAG, "Error: ", databaseError.toException());
            }
        });
    }

//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//
//
//        FirebaseRecyclerOptions<Cart> options =
//                new FirebaseRecyclerOptions.Builder<Cart>()
//                        .setQuery(cartListRef, Cart.class)
//                        .build();
//
//
//
//        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
//                holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
//                holder.txtProductPrice.setText("Price " + model.getPrice() + "$");
//                holder.txtProductName.setText(model.getPname());
//            }
//
//            @NonNull
//            @Override
//            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
//                CartViewHolder holder = new CartViewHolder(view);
//
//
//                return holder;
//            }
//        };
//
//        productsList.setAdapter(adapter);
//        adapter.startListening();
//    }
}
