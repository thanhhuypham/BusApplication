package com.example.administrator.busapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;


public class DetailActivity extends AppCompatActivity {

    private WebView webView;
    private ImageView imgBack;
    private CheckBox chkSave;
    String detail = "";
    String id;
    String name;
    String start;
    String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webView = (WebView) findViewById(R.id.webViewBus);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        chkSave = (CheckBox) findViewById(R.id.chkhistory);

        final Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("data");
        id = bundle.getString("id");
        name = bundle.getString("name");
        start = bundle.getString("start");
        end = bundle.getString("end");

        showContent();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.super.onBackPressed();
            }
        });


    }

    private void showContent()  {

        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        String strId = "<p>" + "<b>" + " Mã số tuyến: " + "</b>" + id.toString() + "</p>";
        String strName = "<p>" + "<b>" + " Tên tuyến: " + "</b>" + name.toString() + "</p>";
        String strStart = "<p style = \" color: #34B67A \">" + start.toString()  + "</p>";
        String strEnd = "<p style = \" color: #34B67A \">" + end.toString()  + "</p>";
        String content = strId + strName + strStart + strEnd;
        webView.loadData(content, "text/html; charset=utf-8", "utf-8");
    }
}
