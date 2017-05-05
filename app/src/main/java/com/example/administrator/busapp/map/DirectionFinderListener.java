package com.example.administrator.busapp.map;

import java.util.ArrayList;

/**
 * Created by Administrator on 20/04/2017.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(ArrayList<Route> route);
}
