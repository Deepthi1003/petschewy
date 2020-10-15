/*
 * Copyright (c) 2018 Bambora ( https://bambora.com/ )
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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