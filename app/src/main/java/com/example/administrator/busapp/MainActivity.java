package com.example.administrator.busapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BusAdapter adapter;
    private static ArrayList<Bus> arrList;
    private EditText edtInput;
    private Button btnTim;
    private ListView lvBus;

    private String strJSON =
            "{\"Bus\":[\n" +
                    "   {\n" +
                    "      \"bus_id\":1,\n" +
                    "      \"bus_name\":\"Bến Thành - BX Chợ Lớn\"\n" +
                    "   },\n" +
                    "   {\n" +
                    "      \"bus_id\":2,\n" +
                    "      \"bus_name\":\"Bến Thành - BX Miền Tây\"\n" +
                    "   },\n" +
                    "   {\n" +
                    "      \"bus_id\":4,\n" +
                    "      \"bus_name\":\"Bến Thành - Cộng Hòa - An Sương\"\n" +
                    "   },\n" +
                    "   {\n" +
                    "      \"bus_id\":3,\n" +
                    "      \"bus_name\":\"Bến Thành - Thạnh Lộc\"\n" +
                    "   },\n" +
                    "   {\n" +
                    "      \"bus_id\":5,\n" +
                    "      \"bus_name\":\"Bến xe Chợ Lớn - Biên Hòa\"\n" +
                    "   }\n" +
                    "]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrList = new ArrayList<Bus>();
        edtInput = (EditText) findViewById(R.id.editTextSearch);
        btnTim = (Button) findViewById(R.id.btnTim);
        lvBus = (ListView) findViewById(R.id.listViewBus);

        try {
            JSONObject jsonObject = new JSONObject(strJSON);
            JSONArray jsonArray = jsonObject.optJSONArray("Bus");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject items = jsonArray.getJSONObject(i);

                String id = items.optString("bus_id").toString();
                String name = items.optString("bus_name").toString();

                Bus bus = new Bus(id, name);
                arrList.add(bus);

                adapter = new BusAdapter(MainActivity.this, R.layout.layout_item_listview, arrList);
                lvBus.setAdapter(adapter);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
