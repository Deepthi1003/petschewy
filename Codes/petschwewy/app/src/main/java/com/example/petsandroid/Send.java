package com.example.petsandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Send extends AppCompatActivity {

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private ProgressBar progressBar;
    private Button openPaymentWindowButton;
    private String checkoutUrl;
    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);
        checkoutUrl = "https://api.v1.checkout.bambora.com/sessions";
        apiKey = "RHZ6RkdBcTVEOExMVmpOS29vM2NAVDEwNTExMzcwMTpQZ3VtUDVhUWs2NWdFcExUcU5Pd29CV1drNVVlZFI5REpCTjdibU9r";

        progressBar = findViewById(R.id.openPaymentWindowProgressBar);
        progressBar.setVisibility(View.GONE);
        openPaymentWindowButton = findViewById(R.id.openPaymentWindowButton);
        openPaymentWindowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openPaymentWindowButton.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    String checkoutRequestJson = createCheckoutRequest();
                    createCheckoutSession(checkoutRequestJson, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            //Do Stuff
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONObject resp =  new JSONObject(response.body().string());
                                if((boolean)resp.getJSONObject("meta").get("result") == true) {
                                    Intent checkoutIntent = new Intent(getApplicationContext(), CheckoutActivity.class);
                                    checkoutIntent.putExtra("checkoutToken", resp.getString("token"));
                                    startActivity(checkoutIntent);
                                } else {
                                    final String merchantMessage = resp.getJSONObject("meta").getJSONObject("message").getString("merchant");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "An error occurred: " + merchantMessage, Toast.LENGTH_LONG).show();
                                            openPaymentWindowButton.setVisibility(View.VISIBLE);
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
        });
    }

    public String createCheckoutRequest() throws JSONException {

        Random orderIdGenerator = new Random();
        int orderId = orderIdGenerator.nextInt(999999999);
        JSONObject checkoutOrder = new JSONObject();
        checkoutOrder.put("id", String.valueOf(orderId));
        checkoutOrder.put("amount", 375);
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

