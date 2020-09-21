package com.example.petsandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ContactUsActivity extends AppCompatActivity {
   Button button,emailIDBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        button = (Button) findViewById(R.id.buttonCall);
        emailIDBTN=(Button) findViewById(R.id.emailIDBTN);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri number = Uri.parse("tel:+16605280951");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });



        emailIDBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={"deepthitejaswini@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Questions to be Answered");
                intent.putExtra(Intent.EXTRA_TEXT,"Kindly write your query here...");
                intent.putExtra(Intent.EXTRA_CC,"dtchokka@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });


    }
}
