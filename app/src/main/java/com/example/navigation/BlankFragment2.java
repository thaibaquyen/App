package com.example.navigation;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.AnimatorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;


public class BlankFragment2 extends Fragment implements LocationListener, OnMapReadyCallback {
    private View view;
    private GoogleMap mMap,mapp;
    private static ArrayList<String> arrx = new ArrayList<>();
    private static ArrayList<String> arry = new ArrayList<>();
    private static ArrayList<String> arrname = new ArrayList<>();
    private static ArrayList<Integer> arrfreesize = new ArrayList<>();
    private static ArrayList<classsalon> arrayListsl = new ArrayList<>();
    LocationManager locationManager;
    private static double x = 0;
    private static double y = 0;
    private static int j = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_blank_fragment2,container, false);
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
//        getstring("http://hairgrab.luyenthib1.com/baquyen/getsalon.php");arrx,arry,arrname,arrfreesize
        new classloadsalon(getActivity(),arrx,arry,arrname,arrfreesize,arrayListsl).execute("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/getsalonmap.php");
//        Toast.makeText(getActivity(),String.valueOf(arrx.size()),Toast.LENGTH_LONG).show();
        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        return  view;
    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapp =googleMap;
        //custum market
        View viewmap = getLayoutInflater().inflate(R.layout.custommarketmap,null);
        adapmarketmap adapmarketmap=new adapmarketmap(getActivity(),viewmap);
        mMap.setInfoWindowAdapter(adapmarketmap);
        //-------------
        getLocation();
        for(int i = 0; i<arrx.size(); i++){
            j = i;
            LatLng sydney = new LatLng(Float.parseFloat(arrx.get(i)),Float.parseFloat(arry.get(i)));
            if(arrfreesize.get(i) > 0){
                Marker mk = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(100)).position(sydney).title(arrname.get(i)));
                mk.setTag(arrayListsl.get(i));
                //event
                mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
                    @Override
                    public void onInfoWindowLongClick(Marker marker) {
                        classsalon classsalon = (com.example.navigation.classsalon) marker.getTag();
                        if(classsalon.getFreesize() > 0){
                        Intent intent = new Intent(getActivity(),Mainchitiet.class);
                        intent.putExtra("salon",classsalon);
                        getActivity().startActivity(intent);
                        }
                    }
                });
            }else {
                MarkerOptions marker = new MarkerOptions().position(sydney).title(arrname.get(i));
                marker.icon(BitmapDescriptorFactory.defaultMarker(60));
                Marker marker1 = mMap.addMarker(marker);
                marker1.setTag(arrayListsl.get(i));
//                Marker mk = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(60)).position(sydney).title(arrname.get(i)));
//                mk.setTag(arrayListsl.get(i));
            }

           // mMap.addMarker(new MarkerOptions().position(sydney).title(arrname.get(i)));
        }
        LatLng sydney1 = new LatLng(x, y);
        MarkerOptions marker = new MarkerOptions().position(sydney1).title("vị trí của bạn");
        marker.icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_directions_walk_black_24dp));
//        Circle circle = mMap.addCircle(new CircleOptions()
//                .center(sydney1)
//                .radius(500)
//                .fillColor(R.drawable.custummap));
        mapp.addMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1,15));

    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 80, vectorDrawable.getIntrinsicHeight() + 40);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    public void getstring(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try{
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0;i< jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            arrx.add(jsonObject.getString("dx"));
                            arry.add(jsonObject.getString("dy"));
                            arrname.add(jsonObject.getString("diachi"));
                        }
//                        Toast.makeText(getActivity(),arrx.get(1),Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Toast.makeText(getActivity(),"loi",Toast.LENGTH_LONG).show();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onLocationChanged(Location location) {
        x = location.getLatitude();
        y = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText(getActivity(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
