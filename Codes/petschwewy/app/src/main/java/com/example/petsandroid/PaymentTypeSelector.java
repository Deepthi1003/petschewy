

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
