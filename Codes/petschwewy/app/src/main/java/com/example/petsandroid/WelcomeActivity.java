package com.example.petsandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    Button main_join_now_btn, main_login_btn;
    TextView SKIP,KNOW_MORE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        main_login_btn=findViewById(R.id.main_login_btn);
        main_join_now_btn=findViewById(R.id.main_join_now_btn);
        SKIP=findViewById(R.id.SKIP);
        KNOW_MORE=findViewById(R.id.KNOWMORE);

        main_join_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });


        main_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        SKIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        KNOW_MORE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(WelcomeActivity.this, KnowMoreActivity.class);
                startActivity(intent);
            }
        });
    }
}
