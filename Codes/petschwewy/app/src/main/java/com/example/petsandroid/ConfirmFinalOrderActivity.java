package com.example.petsandroid;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petsandroid.Model.Cart;
import com.example.petsandroid.Prevalent.Prevalent;
import com.example.petsandroid.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConfirmFinalOrderActivity extends AppCompatActivity
{
    private EditText nameEditText, phoneEditText, addressEditText, cityEditText;
    private Button confirmOrderBtn;

    private String totalAmount = "";
    private String productRandomKey;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    ArrayList<String> stock_list;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private ProgressBar progressBar;
    private Button openPaymentWindowButton;
    private String checkoutUrl;
    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);

        checkoutUrl = "https://api.v1.checkout.bambora.com/sessions";
        apiKey = "RHZ6RkdBcTVEOExMVmpOS29vM2NAVDEwNTExMzcwMTpQZ3VtUDVhUWs2NWdFcExUcU5Pd29CV1drNVVlZFI5REpCTjdibU9r";

        progressBar = findViewById(R.id.openPaymentWindowProgressBar);
        progressBar.setVisibility(View.GONE);


        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price =  $ " + totalAmount, Toast.LENGTH_SHORT).show();
        stock_list = new ArrayList<String>();

        stock_list = getIntent().getStringArrayListExtra("proaddlist");
        Log.d("product is confirm", String.valueOf(stock_list));

        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText = (EditText) findViewById(R.id.shippment_name);
        phoneEditText = (EditText) findViewById(R.id.shippment_phone_number);
        addressEditText = (EditText) findViewById(R.id.shippment_address);
        cityEditText = (EditText) findViewById(R.id.shippment_city);


        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                radioSexButton = (RadioButton) findViewById(selectedId);

                Toast.makeText(ConfirmFinalOrderActivity.this, radioSexButton.getText(), Toast.LENGTH_SHORT).show();

                Check((String) radioSexButton.getText());
            }
        });
    }



    private void Check(String radio)
    {
        if (TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your full name.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your city name.", Toast.LENGTH_SHORT).show();
        }
        else
        {



                ConfirmOrder(radio);


        }
    }

    private void ConfirmOrder(String rad){
        final String saveCurrentDate, saveCurrentTime;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        Log.d("date", saveCurrentDate);
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        Log.d("time", saveCurrentTime);





        productRandomKey = saveCurrentDate + saveCurrentTime;

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Orders");


        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("Product", stock_list);
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("phone", phoneEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state", "not shipped");

        cartListRef.child(Prevalent.currentOnlineUser.getPhone()).child(productRandomKey)
                .updateChildren(ordersMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {


                            JavaMailAPI javaMailAPI = new JavaMailAPI(ConfirmFinalOrderActivity.this,Prevalent.currentOnlineUser.getEmail(),"PETSCHEWY APP- ORDER CONFIRMATION","Congratulations, you have placed your Order.\n"+"Your Order Details.\n"+"Order ID: " +
                                    ""+productRandomKey+"\n"+"Order Total Amount: "+totalAmount
                            +"\n Shipping Address: "+addressEditText.getText().toString()+"\n"+"\n"+"Thanks, "+"\n"+"PETCHWEY APP");

                            javaMailAPI.execute();

                             FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                       // Toast.makeText(ConfirmFinalOrderActivity.this, "your final order has been placed successfully.", Toast.LENGTH_SHORT).show();
                                        if(rad.equals("Pay Online")) {

                                            try {
                                                confirmOrderBtn.setVisibility(View.GONE);
                                                //progressBar.setVisibility(View.VISIBLE);
                                                String checkoutRequestJson = createCheckoutRequest();
                                                createCheckoutSession(checkoutRequestJson, new Callback() {
                                                    @Override
                                                    public void onFailure(Call call, IOException e) {
                                                        //Do Stuff
                                                    }

                                                    @Override
                                                    public void onResponse(Call call, Response response) throws IOException {
                                                        try {
                                                            JSONObject resp = new JSONObject(response.body().string());
                                                            if ((boolean) resp.getJSONObject("meta").get("result") == true) {
                                                                Intent checkoutIntent = new Intent(getApplicationContext(), CheckoutActivity.class);
                                                                checkoutIntent.putExtra("checkoutToken", resp.getString("token"));
                                                                startActivity(checkoutIntent);
                                                            } else {
                                                                final String merchantMessage = resp.getJSONObject("meta").getJSONObject("message").getString("merchant");
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Toast.makeText(getApplicationContext(), "An error occurred: " + merchantMessage, Toast.LENGTH_LONG).show();
                                                                        confirmOrderBtn.setVisibility(View.VISIBLE);
                                                                        progressBar.setVisibility(View.GONE);
                                                                    }
                                                                });

                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        else{

                                            Toast.makeText(ConfirmFinalOrderActivity.this, "Congratulations your order has been placed", Toast.LENGTH_SHORT).show();
                                            Intent i =new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class );
                                            startActivity(i);

                                        }
//
                                    }
                                }
                            });
//
//                                            }// task succes
//                                        }//on complete
//                                    });//add on compl
                        } //
                    }
                });





    }


    public String createCheckoutRequest() throws JSONException {

        Random orderIdGenerator = new Random();
        int orderId = orderIdGenerator.nextInt(999999999);
        JSONObject checkoutOrder = new JSONObject();
        checkoutOrder.put("id", String.valueOf(orderId));
        checkoutOrder.put("amount", totalAmount);
        checkoutOrder.put("currency", "EUR");

        JSONObject checkoutUrl = new JSONObject();
        checkoutUrl.put("accept", "https://checkout-sdk-demo.bambora.com/accept");
        checkoutUrl.put("cancel", "https://checkout-sdk-demo.bambora.com/cancel");

        JSONObject checkoutRequest = new JSONObject();
        checkoutRequest.put("order", checkoutOrder);
        checkoutRequest.put("url", checkoutUrl);

        String jsonRequest = checkoutRequest.toString();

        return jsonRequest;
    }

    public void createCheckoutSession(String checkoutRequest, final Callback callback) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, checkoutRequest);

        Request request = new Request.Builder()
                .url(this.checkoutUrl)
                .header("Authorization", "Basic " + this.apiKey)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("X-EPay-System", "Android SDK Demo")
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
