package com.example.administrator.busapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public static final String BUS_ID = "com.example.administrator.busapp.busid";
    public static final String BUS_NAME = "com.example.administrator.busapp.busname";
    public static final String BUS_START = "com.example.administrator.busapp.busstart";
    public static final String BUS_END = "com.example.administrator.busapp.busend";
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
                bundle.putString(BUS_ID, arrBus.get(position).getId());
                bundle.putString(BUS_NAME, arrBus.get(position).getName());
                bundle.putString(BUS_START, arrBus.get(position).getStart());
                bundle.putString(BUS_END, arrBus.get(position).getEnd());

                Intent intent = new Intent(HistoryActivity.this, DetailHistoryActivity.class);
                intent.putExtra("datahis", bundle);
                startActivity(intent);
            }
        });

        registerForContextMenu(lvHistory);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menuDelete:
                deleteBus(info.position);
                break;
        }
        return true;
    }

    private void deleteBus(int position) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("bus").child(arrBus.get(position).getId());
        dR.removeValue();
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
