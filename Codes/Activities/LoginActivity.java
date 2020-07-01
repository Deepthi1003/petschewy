package com.example.petschewylogin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


   EditText login_phone_number_input,login_password_input;
   Button login_btn;
   String phone, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_password_input=findViewById(R.id.login_password_input);
        login_phone_number_input=findViewById(R.id.login_phone_number_input);
        login_btn=findViewById(R.id.login_btn);

        SharedPreferences sharedPreferences = getSharedPreferences("register", MODE_PRIVATE);
         phone = sharedPreferences.getString("phone","default");
         password =  sharedPreferences.getString("Password","default");
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//             String passinput=   login_password_input.getText().toString();
//             String phninput=login_phone_number_input.getText().toString();
//                Log.d("login",password);
//                Log.d("login",phone);
//
//             if(passinput.equals(password)&&phninput.equals(phone)){
//                 Toast.makeText(LoginActivity.this, "you have logged successfully" , Toast.LENGTH_SHORT).show();
//
//             }
//             else{
//                 Toast.makeText(LoginActivity.this, "your have entered incorrect credentials" , Toast.LENGTH_SHORT).show();
//
//             }


            }
        });




    }
}
