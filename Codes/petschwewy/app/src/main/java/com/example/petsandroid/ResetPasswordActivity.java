package com.example.petsandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petsandroid.Model.Users;
import com.example.petsandroid.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity
{
    private String check = "";

private TextView pageTitle, questionTitle;
private EditText phoneNumber, question1,question2;
private Button verifybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);




        check = getIntent().getStringExtra("check");
        pageTitle=findViewById(R.id.pagetitle);
        questionTitle=findViewById(R.id.title_questions);
        phoneNumber=findViewById(R.id.find_phone_number);
        question1=findViewById(R.id.question_1);

        question2=findViewById(R.id.question_2);
        verifybtn=findViewById(R.id.verify_btn);
    }



    @Override
    protected void onStart()
    {
        super.onStart();

       // Users usersdata= dataSnapshot.child("Users").child(phone).getValue(Users.class);
        phoneNumber.setVisibility(View.GONE);

        if (check.equals("settings"))
        {

            pageTitle.setText("Set Questions");
            questionTitle.setText("Answer the following Security questions");


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(Prevalent.currentOnlineUser.getPhone());

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // String mphone = dataSnapshot.child("phone").getValue().toString();


                        if (dataSnapshot.hasChild("Security Questions")) {

                            displayprevious();


                        }
                        else
                        {


                        }
                    }

                    else {
                        Toast.makeText(ResetPasswordActivity.this, "this phone number not exist", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            verifybtn.setText("SET");

            verifybtn.setOnClickListener(new View.OnClickListener() {
    @Override
        public void onClick(View v) {
        String answer1=question1.getText().toString().toLowerCase();
        String answer2=question2.getText().toString().toLowerCase();
        if(answer1.equals("") && answer2.equals("")){
            Toast.makeText(ResetPasswordActivity.this, "please annswer questions", Toast.LENGTH_SHORT).show();

        }
        else
        {
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

            HashMap<String, Object> userdatamap=new HashMap<>();
            userdatamap.put("answer1",answer1);
            userdatamap.put("answer2",answer2);

            ref.child("Security Questions").updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){

                        JavaMailAPI javaMailAPI = new JavaMailAPI(ResetPasswordActivity.this,ref.child("email").toString(),"PETSCHEWY APP- password Updated","Passed Chnaged Sucessfully");

                        javaMailAPI.execute();

                        Toast.makeText(ResetPasswordActivity.this, "setted annswer questions", Toast.LENGTH_SHORT).show();

finish();

                    }
                }
            });


        }
    }
});

        }
        else if (check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);

verifybtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
verifyUser();


    }
});


        }
    }

    private void verifyUser() {

        String phone=phoneNumber.getText().toString();


        String answer1=question1.getText().toString().toLowerCase();
        String answer2=question2.getText().toString().toLowerCase();

        if(!phone.equals("")&& !answer1.equals("")&& !answer2.equals("")) {


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {


                        if (dataSnapshot.hasChild("Security Questions")) {
                            String ans1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                            String ans2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();
                            if (!ans1.equals(answer1)) {
                                Toast.makeText(ResetPasswordActivity.this, "Your answer1 is wrong"+ans1, Toast.LENGTH_SHORT).show();

                            } else if (!ans2.equals(answer2)) {
                                Toast.makeText(ResetPasswordActivity.this, "Your answer2 is wrong"+ans2, Toast.LENGTH_SHORT).show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("set New Password");

                                final EditText newPassword = new EditText(ResetPasswordActivity.this);
                                newPassword.setHint("write new password");
                                builder.setView(newPassword);

                                builder.setPositiveButton("change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (!newPassword.getText().toString().equals("")) {


                                            ref.child("password").setValue(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){
                                                        JavaMailAPI javaMailAPI = new JavaMailAPI(ResetPasswordActivity.this,dataSnapshot.child("email").getValue().toString(),"PETSCHEWY APP- password Updated","Passed Chnaged Sucessfully");

                                                        javaMailAPI.execute();

                                                        Toast.makeText(ResetPasswordActivity.this, "updated password", Toast.LENGTH_SHORT).show();

                                                       finish();

                                                    }
                                                }
                                            });

                                        }
                                    }
                                });
                                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();
                            }
                        } else {

                            Toast.makeText(ResetPasswordActivity.this, "you have not set security questions", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        Toast.makeText(ResetPasswordActivity.this, "this phone number not exist", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else {
            Toast.makeText(ResetPasswordActivity.this, "Please Complete the form", Toast.LENGTH_SHORT).show();

        }

    }

    private void displayprevious(){

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users")
  .child(Prevalent.currentOnlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


              String ans1=dataSnapshot.child("answer1").getValue().toString();
                String ans2=dataSnapshot.child("answer2").getValue().toString();

                question1.setText(ans1);
                question2.setText(ans2);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
