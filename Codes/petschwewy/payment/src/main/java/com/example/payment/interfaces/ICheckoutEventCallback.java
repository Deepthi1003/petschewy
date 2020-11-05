

package com.example.payment.interfaces;

/**
 * The interface to be called on event dispatched
 */
public interface ICheckoutEventCallback {

    /**
     * Method for handling the event dispatch
     * @param eventType {@Link String}
     * @param jsonPayload {@Link String}
     */
    void onEventDispatched(String eventType, String jsonPayload);
}
