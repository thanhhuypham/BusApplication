package com.example.administrator.busapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 29/03/2017.
 */

public class BusAdapter extends ArrayAdapter<Bus> {

    Activity context = null;
    int itemLayout;
    ArrayList<Bus> arrBus = null;

    public BusAdapter(Activity context, int resource, ArrayList<Bus> arrBus) {
        super(context, resource, arrBus);

        this.context = context;
        itemLayout = resource;
        this.arrBus = arrBus;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        convertView = inflater.inflate(itemLayout, null);

        TextView textViewId = (TextView) convertView.findViewById(R.id.textViewId);
        TextView textViewName = (TextView) convertView.findViewById(R.id.textViewName);

        Bus bus = arrBus.get(position);
        if (bus != null) {
            textViewId.setText(bus.getId());
            textViewName.setText(bus.getName());
        }

        return convertView;
    }
}
