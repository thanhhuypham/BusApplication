package com.example.administrator.busapp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.administrator.busapp.list.ListBusActivity;
import com.example.administrator.busapp.list.ListMailActivity;
import com.example.administrator.busapp.list.ListViewLaterActivity;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
    }

    private void initInstances() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.itemTuyen:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent intent1 = new Intent(MainActivity.this, ListBusActivity.class);
                        startActivity(intent1);

                        break;
                    case R.id.itemXemsau:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent intent2 = new Intent(MainActivity.this, ListViewLaterActivity.class);
                        startActivity(intent2);

                        break;
                    case R.id.itemNhantin:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent intent3 = new Intent(MainActivity.this, ContactActivity.class);
                        startActivity(intent3);

                        break;
                    case R.id.itemGanday:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        Intent intent4 = new Intent(MainActivity.this, ListMailActivity.class);
                        startActivity(intent4);

                        break;
                }
                return false;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
