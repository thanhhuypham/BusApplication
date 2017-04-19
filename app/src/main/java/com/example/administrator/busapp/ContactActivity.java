package com.example.administrator.busapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.busapp.datamodels.Mails;
import com.example.administrator.busapp.mail.SendMail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ContactActivity extends AppCompatActivity {

    private TextView textViewTime;
    private TextView textViewEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button btnSend;
    private int year, month, day, hours, minutes, seconds;
    private String time, date, curTime;
    private ImageView imgViewClose;
    private DatabaseReference dbMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        dbMail = FirebaseDatabase.getInstance().getReference("mails");

        textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        imgViewClose = (ImageView) findViewById(R.id.imgViewBack);
        btnSend = (Button) findViewById(R.id.buttonSend);

        imgViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactActivity.this.onBackPressed();
            }
        });

        initTime();
        textViewTime.setVisibility(View.INVISIBLE);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipident = textViewEmail.getText().toString();
                String subject = editTextSubject.getText().toString();
                String message = editTextMessage.getText().toString();

                //Creating SendMail object
                SendMail sendMail = new SendMail(ContactActivity.this, recipident, subject, message);
                String id = dbMail.push().getKey();
                Mails messages = new Mails(id, recipident, subject, message, curTime);

                dbMail.child(id).setValue(messages);

                //Executing sendmail to send email
                sendMail.execute();


            }
        });
    }

    private void initTime() {

        Calendar c = Calendar.getInstance();


        seconds = c.get(Calendar.SECOND);
        minutes = c.get(Calendar.MINUTE);
        hours = c.get(Calendar.HOUR_OF_DAY);
        time = hours+":"+minutes+":"+seconds;


        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        date = day+"/"+month+"/"+year;

        curTime = date + " " + time;


        textViewTime.setText(curTime);

    }
}
