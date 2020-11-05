

package com.example.payment.controllers;

import android.webkit.JavascriptInterface;


import com.example.payment.interfaces.ICheckoutEventCallback;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * The controller that handles all attached events to be called on event dispatch
 */
public class CheckoutEventCallbackController {

    private Map<String, ArrayList<ICheckoutEventCallback>> callbackEventCollection;

    /**
     * The default constructor
     */
    public CheckoutEventCallbackController()
    {
        this.callbackEventCollection = new TreeMap<>();
    }

    /**
     * Add an event callback to be called on checkout event dispatched
     * @param eventType {@Link String}
     * @param callbackEvent {@Link ICheckoutEventCallback}
     * @return void
     */
    public void on(String eventType, final ICheckoutEventCallback callbackEvent) {
        eventType = eventType.toLowerCase(Locale.getDefault());
        if(this.callbackEventCollection.get(eventType) == null) {
            ArrayList<ICheckoutEventCallback> callbackEvents = new ArrayList<>();
            callbackEvents.add(callbackEvent);
            this.callbackEventCollection.put(eventType, callbackEvents);
        } else {
            ArrayList<ICheckoutEventCallback> callbackEvents = this.callbackEventCollection.get(eventType);
            callbackEvents.add(callbackEvent);
            this.callbackEventCollection.put(eventType, callbackEvents);
        }
    }

    /**
     * Add an event callback to be called on checkout event dispatched
     * @param callbackEvents {@Link Map<String, ArrayList<ICheckoutEventCallback>>}
     * @return {@Link BamboraCheckout }
     */
    public void on(Map<String, ArrayList<ICheckoutEventCallback>> callbackEvents) {
        this.callbackEventCollection.putAll(callbackEvents);
    }

    /**
     * Remove an event callback from beeing called on checkout event dispatched
     * @param eventType {@Link String}
     * @return {@Link BamboraCheckout }
     */
    public void off(String eventType) {
        this.callbackEventCollection.remove(eventType);
    }

    /**
     * Get the collection og callbackEvents
     * @return {@Link Map<String, ArrayList<ICheckoutEventCallback>>}
     */
    public Map<String, ArrayList<ICheckoutEventCallback>> getCallbackEventCollection()
    {
        return this.callbackEventCollection;
    }

    /**
     * Get an array of eventCallbacks based on eventType.
     * @param eventType {@Link String}
     * @return {@Link ArrayList<ICheckoutEventCallback>}
     */
    public ArrayList<ICheckoutEventCallback> getEventCallback(String eventType)
    {
        eventType = eventType.toLowerCase(Locale.getDefault());
        return callbackEventCollection.get(eventType);
    }

    /**
     * The JavaScript interface for handling dispatched events from the WebView
     * @param eventType {@Link String}
     * @param jsonPayload {@Link String}
     * @return void
     */
    @JavascriptInterface
    @SuppressWarnings("unused")
    public void dispatchEvent(String eventType, String jsonPayload) {

        eventType = eventType.toLowerCase(Locale.getDefault());
        if (this.callbackEventCollection.size() > 0) {
            ArrayList<ICheckoutEventCallback> callbackEvents = this.callbackEventCollection.get(eventType);
            if (callbackEvents != null && callbackEvents.size() > 0) {
                for (ICheckoutEventCallback callback : callbackEvents) {
                    callback.onEventDispatched(eventType, jsonPayload);
                }
            }
            ArrayList<ICheckoutEventCallback> anyCallbackEvents = this.callbackEventCollection.get("*");
            if (anyCallbackEvents != null && anyCallbackEvents.size() > 0) {
                for (ICheckoutEventCallback anyCallback : anyCallbackEvents) {
                    anyCallback.onEventDispatched(eventType, jsonPayload);
                }
            }
        }
    }
}
