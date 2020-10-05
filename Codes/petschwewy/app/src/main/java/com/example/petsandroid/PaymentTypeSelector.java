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

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;


import com.example.payment.interfaces.ICheckoutEventCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentTypeSelector implements ICheckoutEventCallback {

    private Context context;

   public PaymentTypeSelector(Context context) {
       this.context = context;
   }

    @Override
    public void onEventDispatched(String eventType, String jsonPayload) {
        if(jsonPayload != null) {
            try {
                JSONObject paymentTypePayload = new JSONObject(jsonPayload);
                final String displayName = paymentTypePayload.getString("displayname");
                JSONObject paymentTypeFee = paymentTypePayload.getJSONObject("fee");
                int amountInMinorunits = paymentTypeFee.getInt("amount");
                final double feeAmount = (double)amountInMinorunits / 100; //100 is representing the two minorunits for EUR currency

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView paymentTypeDisplayNameTextView = ((Activity) context).findViewById(R.id.paymentTypeDiaplayNameDataTextView);
                        paymentTypeDisplayNameTextView.setText(displayName);
                        TextView paymentTypeFeeAmountTextView = ((Activity) context).findViewById(R.id.paymentTypeFeeDataAmount);
                        paymentTypeFeeAmountTextView.setText(String.format("%1$s EUR", feeAmount));
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
