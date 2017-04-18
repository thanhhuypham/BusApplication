package com.example.administrator.busapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.administrator.busapp.datamodels.Bus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailHistoryActivity extends AppCompatActivity {



    private WebView webView;
    private ImageView imgBackHis;
    private DatabaseReference dbHis;

    String id;
    String name;
    String start;
    String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        dbHis = FirebaseDatabase.getInstance().getReference("bus");

        final Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("datahis");
        id = bundle.getString(HistoryActivity.BUS_ID);
        name = bundle.getString(HistoryActivity.BUS_NAME);
        start = bundle.getString(HistoryActivity.BUS_START);
        end = bundle.getString(HistoryActivity.BUS_END);

        webView = (WebView) findViewById(R.id.webViewBusHis);
        imgBackHis = (ImageView) findViewById(R.id.imgBackHis);

        imgBackHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailHistoryActivity.super.onBackPressed();
            }
        });

        showContent();

    }

    private void showContent()  {
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        String strId = "<p>" + "<b>" + " Mã số tuyến: " + "</b>" + id.toString() + "</p>";
        String strName = "<p>" + "<b>" + " Tên tuyến: " + "</b>" + name.toString() + "</p>";
        String strStart = "<br>" + "<b>" + "Lượt đi:" + "</b>" + "</br>" + "<p style = \" color: #34B67A \">"  + start.toString()  + "</p>";
        String strEnd = "<br>" + "<b>" + "Lượt về:" + "</b>" + "</br>" + "<p style = \" color: #34B67A \">" + end.toString()  + "</p>";
        String content = strId + strName + strStart + strEnd;
        webView.loadData(content, "text/html; charset=utf-8", "utf-8");
    }

}
