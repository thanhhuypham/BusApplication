package com.example.administrator.busapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BusAdapter adapter;
    private static ArrayList<Bus> arrBus;
    private ListView lvBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvBus = (ListView) findViewById(R.id.listViewBus);
        arrBus = new ArrayList<Bus>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("http://bustphcm.tk/json.php");
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

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
    }

    class docJSON extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();

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


                adapter = new BusAdapter(MainActivity.this, R.layout.layout_item_listview, arrBus);
                lvBus.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

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
