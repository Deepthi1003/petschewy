package com.example.petsandroid.WishList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.petsandroid.MyAdapter;
import com.example.petsandroid.ProductData;
import com.example.petsandroid.R;
import com.example.petsandroid.WishListAdapter;

import java.util.ArrayList;
import java.util.List;

public class WishListFragment extends Fragment {

    private WishListViewModel wishListViewModel;
    private RecyclerView mRecyclerView;
    List<ProductData> mFlowerList;
    ProductData mProductData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wishListViewModel =
                ViewModelProviders.of(this).get(WishListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wishlist, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerviewwish);
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

        WishListAdapter myAdapter = new WishListAdapter(getActivity(), mFlowerList);
        mRecyclerView.setAdapter(myAdapter);

        return root;



    }






}