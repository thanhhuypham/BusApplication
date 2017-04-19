package com.example.administrator.busapp.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.administrator.busapp.R;
import com.example.administrator.busapp.list.ListMailActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailMailActivity extends AppCompatActivity {

    private WebView webViewMail;
    private ImageView imageViewBack;
    private DatabaseReference dbMails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbMails = FirebaseDatabase.getInstance().getReference("mails");
        Intent intent = getIntent();
        String rec = intent.getStringExtra(ListMailActivity.MAIL_REC);
        String sub = intent.getStringExtra(ListMailActivity.MAIL_SUB);
        String content = intent.getStringExtra(ListMailActivity.MAIL_CONTENT);
        String time = intent.getStringExtra(ListMailActivity.MAIL_TIME);

        webViewMail = (WebView) findViewById(R.id.webViewBusHis);
        imageViewBack = (ImageView) findViewById(R.id.imgBackHis);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailMailActivity.this.onBackPressed();
            }
        });
        showContentMail(rec, sub, content, time);
    }

    private void showContentMail(String strRec, String strSub, String strContent, String strTime) {
        WebSettings webSettings = webViewMail.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");

        String date = "<p style = \" color: #3F51B5 \">" + "<b>" + " Ngày gửi: " + "</b>" + strTime.toString() + "</p>";
        String recipident = "<p style = \" color: #3F51B5 \">" + "<b>" + " Người nhận: " + "</b>" + strRec.toString() + "</p>";
        String subject = "<p style = \" color: #3F51B5 \">" + "<b>" + " Chủ đề: " + "</b>" + strSub.toString() + "</p>";
        String message = "<p style = \" color: #3F51B5 \">" + "<b>" + " Nội dung: " + "</b>" + strContent.toString() + "</p>";
        String detail = date + recipident + subject + message;

        webViewMail.loadData(detail, "text/html; charset=utf-8", "utf-8");
    }
}
