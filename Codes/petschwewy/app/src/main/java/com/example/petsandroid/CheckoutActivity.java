

package com.example.petsandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.payment.BamboraCheckout;
import com.example.payment.enums.CheckoutEvents;
import com.example.payment.interfaces.ICheckoutEventCallback;


public class CheckoutActivity extends AppCompatActivity {

    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Checkout");
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String token = intent.getStringExtra("checkoutToken");
        WebView webView = findViewById(R.id.checkoutWebview);

        mProgress = ProgressDialog.show(this, "Loading", "Please wait for a moment...");
        webView.setWebViewClient(new WebViewClient(){
            // load url
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            // when finish loading page
            public void onPageFinished(WebView view, String url) {
                if(mProgress.isShowing()) {
                    mProgress.dismiss();
                }
            }
        });
        PaymentTypeSelector paymentTypeSelectorEvent = new PaymentTypeSelector(this);
        BamboraCheckout bamboraCheckout = new BamboraCheckout(token, webView)
            .on(CheckoutEvents.AUTHORIZE, new ICheckoutEventCallback() {
                @Override
                public void onEventDispatched(String eventType, String jsonPayload) {
                    paymentComplete(jsonPayload);
                }
            })
            .on("*", new ICheckoutEventCallback() {
                @Override
                public void onEventDispatched(String eventType, String jsonPayload) {
                   //Toast.makeText(getApplicationContext(), "This should always get fired Event: " + eventType + " Payload: " + jsonPayload, Toast.LENGTH_LONG).show();
                }
            })
            .on(CheckoutEvents.CARDTYPERESOLVE, (ICheckoutEventCallback) paymentTypeSelectorEvent)
            .initialize();




        Button declineButton = findViewById(R.id.declineButton);

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(), ConfirmFinalOrderActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    private void paymentComplete(final String jsonPayload)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout paymentTypeLayout = findViewById(R.id.paymentTypeLayout);
                paymentTypeLayout.setVisibility(View.GONE);
                LinearLayout paymentCompleteLayout = findViewById(R.id.paymentCompletedLayout);
                paymentCompleteLayout.setVisibility(View.VISIBLE);

                Button completeButton = findViewById(R.id.completeButton);
                completeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent authIntent = new Intent(getApplicationContext(), AuthorizeActivity.class);
                        authIntent.putExtra("payload", jsonPayload);
                        startActivity(authIntent);
                    }
                });
            }
        });
    }
}