package com.example.petsandroid.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.petsandroid.ProductData;
import com.example.petsandroid.MyAdapter;
import com.example.petsandroid.R;

import java.util.ArrayList;
import java.util.List;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment  {

    private HomeViewModel shareViewModel;
    RecyclerView mRecyclerView;
    List<ProductData> mFlowerList;
    ProductData mProductData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mFlowerList = new ArrayList<>();
        mProductData = new ProductData("Dogs", "Description for a product",
                R.drawable.dog3,9);
        mFlowerList.add(mProductData);
        mProductData = new ProductData("Carnation", "Description for a product",
                R.drawable.toy4,8);
        mFlowerList.add(mProductData);
        mProductData = new ProductData("dog toy", "Description for a product",
                R.drawable.dogtoy2,20);
        mFlowerList.add(mProductData);
        mProductData = new ProductData("Daisy", "Description for a product",
                R.drawable.dogtoy1,4);
        mFlowerList.add(mProductData);
        mProductData = new ProductData("Azawakh", "Description for a product",
                R.drawable.azawakhdog,50);
        mFlowerList.add(mProductData);
        mProductData = new ProductData("Adult Dog", "Description for a product",
                R.drawable.dogfood2,1);
        mFlowerList.add(mProductData);
        mProductData = new ProductData("FreshPEt", "pet Food",
                R.drawable.freshpet,6);
        mFlowerList.add(mProductData);
        mProductData = new ProductData("Classic", "food for fish",
                R.drawable.classic,9);
        mFlowerList.add(mProductData);
        mProductData = new ProductData("Dog Bone", "Description for a product",
                R.drawable.petfood,5);
        mFlowerList.add(mProductData);
        mProductData = new ProductData("Play dog", "Description for a product",
                R.drawable.dogcatfood,9);
        mFlowerList.add(mProductData);

        MyAdapter myAdapter = new MyAdapter(getActivity(), mFlowerList);
        mRecyclerView.setAdapter(myAdapter);


        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //  textView.setText(s);
            }
        });
        return root;
    }
}