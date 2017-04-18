package com.example.administrator.busapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactActivity extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtEmail;
    private EditText edtContent;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        edtUser = (EditText) findViewById(R.id.edtInputUser);
        edtEmail = (EditText) findViewById(R.id.edtInputEmail);
        edtContent = (EditText) findViewById(R.id.edtInputContent);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEmail = new Intent(Intent.ACTION_SEND);
                intentEmail.setType("plain/text");
                intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"thanhhuy.25101996@gmail.com"});
                
            }
        });

    }
}
