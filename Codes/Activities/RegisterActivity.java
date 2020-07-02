package com.example.petschewy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petschewy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private ProgressDialog PD;
    Button register_btn;

    EditText register_username_input, register_phone_number_input,register_password_input;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        register_password_input=findViewById(R.id.register_password_input);
        register_username_input=findViewById(R.id.register_username_input);
        register_phone_number_input=findViewById(R.id.register_phone_number_input);
        register_btn= findViewById(R.id.register_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phnstr = register_phone_number_input.getText().toString();
                final String passwordstr = register_password_input.getText().toString();
                final String usernamestr= register_username_input.getText().toString();
                // Toast.makeText(RegisterActivity.this, "username already exists", Toast.LENGTH_LONG).show();


                DatabaseReference refer = FirebaseDatabase.getInstance().getReference("Users");
                Query checkUser = refer.orderByChild("name").equalTo(usernamestr);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(RegisterActivity.this, "username already exists", Toast.LENGTH_LONG).show();


                        } else {

                            rootNode= FirebaseDatabase.getInstance();
                            reference=rootNode.getReference("Users");
                            CustomerModel cust= new CustomerModel(usernamestr,phnstr,passwordstr);
                            //  Workermodel workermodel = new Workermodel(usernamestr,passwordstr,emailstr,phonestr,"","","",addressstr,"","","customer");
                            reference.child(usernamestr).setValue(cust);

//                            Intent ob = new Intent(RegisterActivity.this, LoginActivity.class);
//                            startActivity(ob);
//                            finish();

                            Toast.makeText(RegisterActivity.this, "Succesfully saved all the data of user", Toast.LENGTH_LONG).show();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }


                });


            }
        });


    }
}
