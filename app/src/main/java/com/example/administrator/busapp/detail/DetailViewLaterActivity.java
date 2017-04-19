package com.example.administrator.busapp.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.administrator.busapp.list.ListViewLaterActivity;
import com.example.administrator.busapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailViewLaterActivity extends AppCompatActivity {



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
        setContentView(R.layout.activity_detail);
        dbHis = FirebaseDatabase.getInstance().getReference("bus");

        final Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("datahis");
        id = bundle.getString(ListViewLaterActivity.BUS_ID);
        name = bundle.getString(ListViewLaterActivity.BUS_NAME);
        start = bundle.getString(ListViewLaterActivity.BUS_START);
        end = bundle.getString(ListViewLaterActivity.BUS_END);

        webView = (WebView) findViewById(R.id.webViewBusHis);
        imgBackHis = (ImageView) findViewById(R.id.imgBackHis);

        imgBackHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailViewLaterActivity.super.onBackPressed();
            }
        });

        showContent();

    }

    private void showContent()  {
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        String strId = "<p style = \" color: #3F51B5 \">" + "<b>" + " Mã số tuyến: " + "</b>" + id.toString() + "</p>";
        String strName = "<p style = \" color: #3F51B5 \">" + "</b>" + name.toString() + "</p>";
        String strStart = "<br>" + "<b style = \" color: #3F51B5 \">" + "Lượt đi:" + "</b>" + "</br>" + "<p style = \" color: #3F51B5 \">"  + start.toString()  + "</p>";
        String strEnd = "<br>" + "<b style = \" color: #3F51B5 \">" + "Lượt về:" + "</b>" + "</br>" + "<p style = \" color: #3F51B5 \">" + end.toString()  + "</p>";
        String content = strId + strName + strStart + strEnd;
        webView.loadData(content, "text/html; charset=utf-8", "utf-8");
    }

}
