package com.example.administrator.busapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BusAdapter adapter;
    private static ArrayList<Bus> arrList;
    private EditText edtInputSearch;
    private Button btnTim;
    private ListView lvBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrList = new ArrayList<Bus>();
        edtInputSearch = (EditText) findViewById(R.id.editTextSearch);
        btnTim = (Button) findViewById(R.id.btnTim);
        lvBus = (ListView) findViewById(R.id.listViewBus);

        readDataBusJSON();
    }

    private void readDataBusJSON() {
        try {
            JSONObject jsonObject = new JSONObject(DataBusJSON.strJSON);
            JSONArray jsonArray = jsonObject.getJSONArray("Bus");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject items = jsonArray.getJSONObject(i);

                String id = items.getString("bus_id").toString();
                final String name = items.getString("bus_name").toString();

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
