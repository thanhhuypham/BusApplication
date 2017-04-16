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
                Intent intent1 = new Intent(DetailActivity.this, ListActivity.class);
                startActivity(intent1);
            }
        });


    }

    private void showContent()  {

        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        String strId = "<p> Mã số tuyến: " + id.toString() + "</p>";
        String strName = "<p> Tên tuyến: " + name.toString()  + "</p>";
        String strStart = "<p>" + start.toString()  + "</p>";
        String strEnd= "<p>" + end.toString()  + "</p>";
        String content = strId + strName + strStart + strEnd;
        webView.loadData(content, "text/html; charset=utf-8", "utf-8");
    }
}
