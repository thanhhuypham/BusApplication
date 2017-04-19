package com.example.administrator.busapp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.busapp.R;
import com.example.administrator.busapp.datamodels.Mails;

import java.util.ArrayList;

/**
 * Created by Administrator on 19/04/2017.
 */

public class MailsAdapter extends ArrayAdapter<Mails> {
    Activity context = null;
    int itemLayout;
    ArrayList<Mails> arrMessage = null;


    public MailsAdapter(Activity context, int resource, ArrayList<Mails> arrMessage) {
        super(context, resource, arrMessage);
        this.context = context;
        itemLayout = resource;
        this.arrMessage = arrMessage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        convertView = inflater.inflate(itemLayout, null);

        TextView textViewSubject = (TextView) convertView.findViewById(R.id.textViewSubject);
        TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);

        Mails mail = arrMessage.get(position);
        if (mail != null) {
            textViewSubject.setText(mail.getSubject());
            textViewDate.setText(mail.getTime());
        }

        return convertView;
    }
}
