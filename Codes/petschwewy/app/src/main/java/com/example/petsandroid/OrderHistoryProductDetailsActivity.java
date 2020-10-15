package com.example.petsandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.petsandroid.Model.OrdersList;
import com.example.petsandroid.Model.Products;
import com.example.petsandroid.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryProductDetailsActivity extends AppCompatActivity {

    private ListView listview;
    private DatabaseReference ref, refe;

    private String userID = "";
    ArrayList<String> pIdList;
    private int x =1;
    ArrayList<Products> mylist = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_product_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order Product Details");
        setSupportActionBar(toolbar);
        listview = findViewById(R.id.productList);
        userID = getIntent().getStringExtra("uid");
        Log.d("userId", userID);


        Log.d("details of Product", "hello");

        ref = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone()).child(userID).child("Product");
        getProductIds();
        Log.d("details of Product", "hello");


    }

    public void getProductIds(){
        pIdList = new ArrayList<String>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("details of Product", "Inside");
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String pid = postSnapshot.getValue(String.class);
                    pIdList.add(pid);
                    Log.d("poductId",pid);
                }
                refe = FirebaseDatabase.getInstance().getReference().child("Products");
                ListOrderProducts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ListOrderProducts(){
        mylist = new ArrayList<Products>();
        refe.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String pid =  postSnapshot.child("pid").getValue().toString();
                    String name =  postSnapshot.child("pname").getValue().toString();
                    String des =  postSnapshot.child("description").getValue().toString();
                    String price =  postSnapshot.child("price").getValue().toString();
                    if(pIdList.contains(pid)){
                        Products p = new Products("Name: "+name,"Description: "+des,"Price: "+price);
                        mylist.add(p);
                        Log.d("name", name);
                    }
                }
                updatelist();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updatelist(){
        Log.d("name", "insidelist");

        for(Products p : mylist){
            Log.d("name", p.getPname());
        }

        OrdersList adapter = new OrdersList(OrderHistoryProductDetailsActivity.this, mylist);
       listview.setAdapter(adapter);
    }

}