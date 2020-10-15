package com.example.petsandroid.Admin;

import android.os.Bundle;
;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsandroid.Model.AdminOrders;
import com.example.petsandroid.OrderAdapter;
import com.example.petsandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class AdminNewOrdersActivity extends AppCompatActivity
{
    RecyclerView ordersList;
    private DatabaseReference ordersRef;
    ArrayList<AdminOrders> usersList = new ArrayList<>();
    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        ordersList = findViewById(R.id.orders_list);

        build();
    }

    private void build() {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Orders");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){

                            for(DataSnapshot childChild: childSnapshot.getChildren()){
                                Log.d("snap",childChild.getValue().toString());
                                usersList.add(new AdminOrders((Map) childChild.getValue()));
                            }

                        }
                        adapter = new OrderAdapter(usersList);
                        ordersList.setAdapter(adapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        ordersList.setLayoutManager(layoutManager);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });


    }


}
