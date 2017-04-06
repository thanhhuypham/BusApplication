package com.example.administrator.busapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

        lvBus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("id", arrList.get(position).getId());
                bundle.putString("name", arrList.get(position).getName());
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent. putExtra("bus", bundle);
                startActivity(intent);
                finish();
            }
        });
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
