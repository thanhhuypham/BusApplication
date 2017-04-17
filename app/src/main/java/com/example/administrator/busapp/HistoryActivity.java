package com.example.administrator.busapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.busapp.adapter.BusAdapter;
import com.example.administrator.busapp.datamodels.Bus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ImageView imgViewClose;
    private ListView lvHistory;
    private ArrayList<Bus> arrBus;
    private BusAdapter adapter;
    private DatabaseReference dbHisBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHisBus = FirebaseDatabase.getInstance().getReference("bus");
        arrBus = new ArrayList<Bus>();

        lvHistory = (ListView) findViewById(R.id.lvHistory);
        imgViewClose = (ImageView) findViewById(R.id.imgViewClose);

        imgViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity.super.onBackPressed();
            }
        });

        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("id", arrBus.get(position).getId());
                bundle.putString("name", arrBus.get(position).getName());
                bundle.putString("start", arrBus.get(position).getStart());
                bundle.putString("end", arrBus.get(position).getEnd());

                Intent intent = new Intent(HistoryActivity.this, DetailActivity.class);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        dbHisBus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrBus.clear();

                for(DataSnapshot busSnap : dataSnapshot.getChildren()) {
                    Bus bus = busSnap.getValue(Bus.class);

                    arrBus.add(bus);
                }

                adapter = new BusAdapter(HistoryActivity.this, R.layout.layout_item_listview, arrBus);
                lvHistory.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
