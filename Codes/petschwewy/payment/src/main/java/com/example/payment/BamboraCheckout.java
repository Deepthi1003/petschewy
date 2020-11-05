

package com.example.payment;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.example.payment.controllers.CheckoutEventCallbackController;
import com.example.payment.enums.CheckoutEvents;
import com.example.payment.interfaces.ICheckoutEventCallback;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

/**
 * The main class for handling the Bambora Online Checkout payment window
 */
public class BamboraCheckout {
    private String token;
    private WebView checkoutWebView;
    private CheckoutEventCallbackController checkoutDispatchController;

    /**
     * Bambora Checkout constructor
     * @param token {@Link String}
     * @param webView {@Link WebView}
     */
    public BamboraCheckout(String token, WebView webView)
    {
        this.token = token;
        this.checkoutWebView = webView;
        this.checkoutDispatchController = new CheckoutEventCallbackController();
    }

    /**
     * Initilize the paymentwindow
     * @return BamboraCheckout
     */
    @SuppressLint("SetJavaScriptEnabled")
    public BamboraCheckout initialize()
    {
        String versionName = "CheckoutSDKAndroid/" + BuildConfig.VERSION_NAME;
        String checkoutOptions = String.format("{version:%1s}", versionName);

        String checkoutOptionsHash = "";
        try {
            checkoutOptionsHash = Base64.encodeToString(checkoutOptions.getBytes("UTF-8"), Base64.NO_WRAP | Base64.URL_SAFE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String checkoutUrl = String.format("https://v1.checkout.bambora.com/%1$s?ui=inline#%2$s", this.token, checkoutOptionsHash);
        WebSettings checkoutWebSettings = checkoutWebView.getSettings();
        checkoutWebSettings.setJavaScriptEnabled(true);
        checkoutWebView.addJavascriptInterface(checkoutDispatchController, "CheckoutSDKAndroid");
        checkoutWebView.loadUrl(checkoutUrl);

        return this;
    }

    /**
     * Add an event callback to be called on checkout event dispatched
     * @param eventType {@Link CheckoutEvents}
     * @param callback {@Link ICheckoutEventCallback}
     * @return {@Link BamboraCheckout }
     */
    public BamboraCheckout on(CheckoutEvents eventType, final ICheckoutEventCallback callback) {
        this.on(eventType.toString(), callback);
        return this;
    }

    /**
     * Add an event callback to be called on checkout event dispatched
     * @param eventType {@Link String}
     * @param callback {@Link ICheckoutEventCallback}
     * @return {@Link BamboraCheckout }
     */
    public BamboraCheckout on(String eventType, final ICheckoutEventCallback callback) {
        this.checkoutDispatchController.on(eventType, callback);
        return this;
    }

    /**
     * Add an event callback to be called on checkout event dispatched
     * @param callbacks {@Link Map<String, ArrayList<ICheckoutEventCallback>>}
     * @return {@Link BamboraCheckout }
     */
    public BamboraCheckout on(Map<String, ArrayList<ICheckoutEventCallback>> callbacks) {
        this.checkoutDispatchController.on(callbacks);
        return this;
    }

    /**
     * Remove an event callback from beeing called on checkout event dispatched
     * @param eventType {@Link String}
     * @return {@Link BamboraCheckout }
     */
    public BamboraCheckout off(String eventType) {
        this.checkoutDispatchController.off(eventType);
        return this;
    }

    /**
     * Returns the checkout token
     * @return {@Link String}
     */
    public String getToken()
    {
        return this.token;
    }

    /**
     * Returns the checkout token
     * @return {@Link WebView}
     */
    public WebView getWebView()
    {
        return this.checkoutWebView;
    }
}
