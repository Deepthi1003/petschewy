package com.example.petsandroid.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.petsandroid.R;

import java.util.ArrayList;

public class OrdersList extends ArrayAdapter<Products> {

    Context context;

    public OrdersList(@NonNull Context context, ArrayList<Products> products) {
        super(context, products.size(), products);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Products p = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.orderscustom_layout, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.proname);
        TextView des = (TextView) convertView.findViewById(R.id.prodesc);
       TextView price = (TextView) convertView.findViewById(R.id.proprice);

        name.setText(p.getPname());
        des.setText(p.getDescription());
        price.setText(p.getPrice());

        return convertView;
    }
}
