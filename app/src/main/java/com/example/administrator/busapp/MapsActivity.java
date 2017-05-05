package com.example.administrator.busapp;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.busapp.map.DirectionFinder;
import com.example.administrator.busapp.map.DirectionFinderListener;
import com.example.administrator.busapp.map.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private Button btnFindPath;
    private Button btnSwap;
    private EditText etOrigin;
    private EditText etDestination;
    private ArrayList<Marker> arrOriginMarker = new ArrayList<>();
    private ArrayList<Marker> arrDestinationMarker = new ArrayList<>();
    private ArrayList<Polyline> arrPolylinePath = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        btnSwap = (Button) findViewById(R.id.btnSwap);
        etOrigin = (EditText) findViewById(R.id.editTextOrigin);
        etDestination = (EditText) findViewById(R.id.editTextDestination);

        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String swapOrigin = etOrigin.getText().toString();
                String swapDestination = etDestination.getText().toString();

                if (swapOrigin.isEmpty()) {
                    Toast.makeText(MapsActivity.this, "Vui lòng nhập địa chỉ xuất phát!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (swapDestination.isEmpty()) {
                    Toast.makeText(MapsActivity.this, "Vui lòng nhập địa chỉ cần đến!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    etOrigin.setText(swapDestination);
                    etDestination.setText(swapOrigin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(MapsActivity.this, "Vui lòng nhập địa chỉ xuất phát!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(MapsActivity.this, "Vui lòng nhập địa chỉ cần đến!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        LatLng tdc = new LatLng(10.850670, 106.758292);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tdc, 18));
        arrOriginMarker.add(mMap.addMarker(new MarkerOptions()
                .title("Cao đẳng Công nghệ Thủ Đức")
                .position(tdc)));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Vui lòng đợi.",
                "Đang dò nơi cần đến..!", true);

        if (arrOriginMarker != null) {
            for (Marker marker : arrOriginMarker) {
                marker.remove();
            }
        }

        if (arrDestinationMarker != null) {
            for (Marker marker : arrDestinationMarker) {
                marker.remove();
            }
        }

        if (arrPolylinePath != null) {
            for (Polyline polyline: arrPolylinePath) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(ArrayList<Route> arrRoute) {
        Toast.makeText(MapsActivity.this, "Thành công!", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        arrPolylinePath = new ArrayList<>();
        arrOriginMarker = new ArrayList<>();
        arrDestinationMarker = new ArrayList<>();

        for (Route route : arrRoute) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.textViewTime)).setText(route.duration.getText().toString());
            ((TextView) findViewById(R.id.textViewDistance)).setText(route.distance.getText().toString());

            arrOriginMarker.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_on_black_24dp))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            arrDestinationMarker.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_panorama_fish_eye_black_24dp))
                    .title(route.endAddress)
                    .position(route.endLocation)));

    //Ta khai báo đối tượng PolylineOptions
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE). // Thiết lập màu đường kẻ
                    width(10); // Thiết lập độ dày đường kẻ

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            //đưa tất cả những tọa độ vào PolylineOptions rồi đẩy vào hàm
            arrPolylinePath.add(mMap.addPolyline(polylineOptions));
        }
    }
}
