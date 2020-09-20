package com.example.petsandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PhoneVerfication extends AppCompatActivity {

    EditText editTextPhone, editTextCode;
Button getverfi,buttonSignIn;
   // FirebaseAuth mAuth;

    String codeSent;
    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverification);

     //   mAuth = FirebaseAuth.getInstance();
        ccp = findViewById(R.id.ccp);

        editTextCode = findViewById(R.id.editTextCode);
        editTextPhone = findViewById(R.id.editTextPhone);
        getverfi= findViewById(R.id.buttonGetVerificationCode);

        buttonSignIn=findViewById(R.id.buttonSignIn);
        getverfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("send verifcation methd","10");
               // sendVerificationCode();
            }
        });


        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifySignInCode();
            }
        });
    }

//    private void verifySignInCode(){
//        String code = editTextCode.getText().toString();
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
//        signInWithPhoneAuthCredential(credential);
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            //here you can open new activity
//                            Toast.makeText(getApplicationContext(),
//                                    "Login Successfull", Toast.LENGTH_LONG).show();
//                        } else {
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                Toast.makeText(getApplicationContext(),
//                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }
//                });
//    }
//
//    private void sendVerificationCode(){
//
//        String phone = editTextPhone.getText().toString();
//        String countryCode = ccp.getSelectedCountryCodeWithPlus();
//
//        phone=countryCode+phone;
//        Toast.makeText(PhoneVerfication.this, "send verification code", Toast.LENGTH_SHORT).show();
//
//        if(phone.isEmpty()){
//            editTextPhone.setError("Phone number is required");
//            editTextPhone.requestFocus();
//            return;
//        }
//
//        if(phone.length() < 10 ){
//            editTextPhone.setError("Please enter a valid phone");
//            editTextPhone.requestFocus();
//            return;
//        }
//
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phone,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//    }
//
//
//
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//            Intent intent=getIntent();
//            String name=intent.getStringExtra("name");
//            String phone =intent.getStringExtra("phone");
//            String password=intent.getStringExtra("password");
//            String usertype=intent.getStringExtra("usertype");
//            ValidatephoneNumber(name, phone, password,usertype);
//
//
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//
//            codeSent = s;
//        }
//    };
//
//    private void ValidatephoneNumber(final String name, final String phone, final String password,final String usertype)
//    {
//        final DatabaseReference RootRef;
//        RootRef = FirebaseDatabase.getInstance().getReference();
//
//        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if (!(dataSnapshot.child("Users").child(phone).exists()))
//                {
//                    HashMap<String, Object> userdataMap = new HashMap<>();
//                    userdataMap.put("phone", phone);
//                    userdataMap.put("password", password);
//                    userdataMap.put("name", name);
//                    userdataMap.put("usertype",usertype);
//
//                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task)
//                                {
//                                    if (task.isSuccessful())
//                                    {
//                                        Toast.makeText(PhoneVerfication.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
//                                       // loadingBar.dismiss();
//
//                                        Intent intent = new Intent(PhoneVerfication.this, LoginActivity.class);
//                                        startActivity(intent);
//                                    }
//                                    else
//                                    {
//                                       // loadingBar.dismiss();
//                                        Toast.makeText(PhoneVerfication.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//                else
//                {
//                    Toast.makeText(PhoneVerfication.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
//                    //loadingBar.dismiss();
//                    Toast.makeText(PhoneVerfication.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(PhoneVerfication.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
