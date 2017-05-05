package com.example.administrator.busapp.list;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.administrator.busapp.R;
import com.example.administrator.busapp.adapter.BusAdapter;
import com.example.administrator.busapp.datamodels.Bus;
import com.example.administrator.busapp.detail.DetailBusActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

public class ListBusActivity extends AppCompatActivity {

    BusAdapter adapter;
    private static ArrayList<Bus> arrBus;
    private ListView lvBus;
    private EditText edtSearch;
    private ImageView imgClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bus);

        imgClose = (ImageView) findViewById(R.id.imgClose);
        edtSearch = (EditText) findViewById(R.id.editTextSearch);
        lvBus = (ListView) findViewById(R.id.listViewBus);
        arrBus = new ArrayList<Bus>();

        // Yeu cau chay AsyncTask
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("http://timxebuyttphcm.tk/json.php");
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListBusActivity.super.onBackPressed();
            }
        });

        lvBus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("id", arrBus.get(position).getId());
                bundle.putString("name", arrBus.get(position).getName());
                bundle.putString("start", arrBus.get(position).getStart());
                bundle.putString("end", arrBus.get(position).getEnd());

                Intent intent = new Intent(ListBusActivity.this, DetailBusActivity.class);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
        lvBus.setTextFilterEnabled(true);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = edtSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });
    }

    class docJSON extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ListBusActivity.this, "Vui lòng đợi.",
                    "Đang tải dữ liệu...!", true);
        }

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(ListBusActivity.this, s, Toast.LENGTH_LONG).show();
            Toast.makeText(ListBusActivity.this, "Hoàn tất việc tải dữ liệu!", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("bus_id");
                    String name = jsonObject.getString("bus_name");
                    String start = jsonObject.getString("bus_start");
                    String end = jsonObject.getString("bus_end");
                    Bus bus = new Bus(id, name, start, end);
                    arrBus.add(bus);
                }


                adapter = new BusAdapter(ListBusActivity.this, R.layout.layout_item_listview_bus, arrBus);
                lvBus.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // Doc noi dung tu html cua mot duong dan tu internet
    private static String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }


}
