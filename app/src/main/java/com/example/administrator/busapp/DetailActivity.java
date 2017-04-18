package com.example.administrator.busapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.busapp.datamodels.Bus;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {

    private WebView webView;
    private ImageView imgBack;
    private CheckBox chkSave;
    String id;
    String name;
    String start;
    String end;
    private DatabaseReference dbBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbBus = FirebaseDatabase.getInstance().getReference("bus");

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

        chkSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkSave.isChecked()) {
                    Bus bus = new Bus(id, name, start, end);

                    dbBus.child(id).setValue(bus);
                    Toast.makeText(DetailActivity.this, "Tuyến " + name + " đã được lưu vào lịch sử tìm kiếm", Toast.LENGTH_LONG).show();

                }
                else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("bus").child(id);
                    databaseReference.removeValue();

                    Toast.makeText(DetailActivity.this, "Tuyến " + name + " đã được hủy bỏ", Toast.LENGTH_LONG).show();

                }
            }
        });

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
