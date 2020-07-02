package com.example.petsandroid;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    ImageView mFlower;
    TextView mDescription;
    TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        mFlower = findViewById(R.id.ivImage);
        mDescription=findViewById(R.id.tvDescription);
        price = findViewById(R.id.price);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mFlower.setImageResource(mBundle.getInt("Image"));
            mDescription.setText(mBundle.getString("Description"));
            price.setText(String.valueOf(mBundle.getInt("price")));
        }
    }
}
