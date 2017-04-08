package com.example.administrator.busapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class DetailActivity extends AppCompatActivity {

    private WebView webView;
    private ImageView imgBack;
    private CheckBox chkSave;
    String id;
    String link;
    String detail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webView = (WebView) findViewById(R.id.webViewBus);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        chkSave = (CheckBox) findViewById(R.id.chkhistory);

        final Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("bus");
        id = bundle.getString("id");
        link = "http://buyttphcm.com.vn/RouteDetail?rId=" + id + "&sP=Route";

        Toast.makeText(DetailActivity.this, link, Toast.LENGTH_LONG).show();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        new getDataBus().execute();

    }

    public class getDataBus extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document document = Jsoup.connect(link).get();


                Elements content = document.select("div.EasyDNNSkin_NewsTwo div.NewsTwoBackgroundGradient div.NewsTwoMain div.content-top-wraper div#dnn_ContentTop div.DnnModule div.eds_templateGroup_default div#dnn_ctr3593_ContentPane div#dnn_ctr3593_ModuleContent div.routeDetail div.routeInfo table tbody td td");

                detail += "<p>" + content.text() + "</p>";

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            webView.loadDataWithBaseURL("", detail, "text/html", "UTF-8", "");
        }
    }

}
