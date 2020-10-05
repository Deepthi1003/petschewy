package com.example.petsandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("Please refer to our Shipping Policies page for detailed information on shipping and returns.");


        List<String> football = new ArrayList<String>();
        football.add("We accept all the major credit cards including Visa, MasterCard, American Express and Discover.");


        List<String> basketball = new ArrayList<String>();
        basketball.add("We value your privacy. We do not sell or share your information to third parties, nor use personal information for any reason other than its intended use.");

        List<String> volly = new ArrayList<String>();
        volly.add("Please check our Shipping Policies page");
        List<String> game = new ArrayList<String>();
        game.add("Wish-Lists are personal lists that a customer can create once an acocunt is created. Customers can add products to their wish list from the Product Page. Customers can use these wish lists to purchase the items at any time. Products will be automatically addedd to the shopping bag based on the wish list selection. Customers can prepare more than one wish list.");

        expandableListDetail.put("Q1. What is your shipping policy?", cricket);
        expandableListDetail.put("Q2. What forms of payments do you accept?", football);
        expandableListDetail.put("Q3. Will you share my information with any one?", basketball);

        expandableListDetail.put("Q4. What is your Return Policy?", volly);
        expandableListDetail.put("Q5. What is a wish list and hows does it work?", game);



        return expandableListDetail;
    }
}
