package com.example.administrator.busapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
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
    private String id, name, link, detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webView = (WebView) findViewById(R.id.webView);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        chkSave = (CheckBox) findViewById(R.id.chkhistory);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bus");
        id = bundle.getString("id");
        name = bundle.getString("name");
        link = "http://buyttphcm.com.vn/RouteDetail?rId=" + id + "&sP=Route";

        Toast.makeText(DetailActivity.this, link, Toast.LENGTH_LONG).show();

        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        new getDataBus().execute();

    }

    public class getDataBus extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(link).get();

                Elements title = doc.select("h2 span.Head span.cms-highlight");
                Elements content = doc.select("div.routeDetail div.routeInfo");

                detail += "<font size=\" 18px \" style = \" color: #34B67A \"><em>"
                        + title.text() + "</em></font>";

                return null;
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
