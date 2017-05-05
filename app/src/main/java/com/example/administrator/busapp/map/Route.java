package com.example.administrator.busapp.map;

import com.example.administrator.busapp.datamodels.Distance;
import com.example.administrator.busapp.datamodels.Duration;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Administrator on 20/04/2017.
 */

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public ArrayList<LatLng> points;
}
