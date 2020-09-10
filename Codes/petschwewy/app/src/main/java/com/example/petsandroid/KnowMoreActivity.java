package com.example.petsandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class KnowMoreActivity extends AppCompatActivity {

    Button backToWelcomePage,backToHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_more);

        backToWelcomePage=findViewById(R.id.welcomePage_btn);
        backToHomePage=findViewById(R.id.homePage_btn);

        backToWelcomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(KnowMoreActivity.this, WelcomeActivity.class);
                startActivity(intent);

            }
        });
        backToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(KnowMoreActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

    }
}
