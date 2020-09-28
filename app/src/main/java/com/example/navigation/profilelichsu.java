package com.example.navigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class profilelichsu extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
   // private classsalon classsalon1;
    private static ArrayList<classsalon> arrsl;
    private TextView txtdiachi,txtgia,txtsdt,txtkc;
    private Button btnhuy;
    public static classsalon classsalon;
    private  static Double dx = 3.4 ,dy = 4.5;
    private  static View view;
    private getuser getuser;
    private LinearLayout lout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profilelichsu, container, false);
        arrsl = new ArrayList<>();
        addcontrol();
        getclasssalon1("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/getlichsu.php");
        return view;
    }

    private void addevent() {
        txtdiachi.setText(classsalon.getDiachi());
        txtsdt.setText(classsalon.getSdt());
        txtkc.setText(classsalon.getKc()+"m");
        txtgia.setText(String.valueOf(classsalon.getPrice()).substring(0,String.valueOf(classsalon.getPrice()).length()-2)+"đ");
        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapchitiet1);
        supportMapFragment.getMapAsync(this);
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                huydat("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/huydat.php");
            }
        });
    }

    private void addcontrol() {
        txtdiachi = view.findViewById(R.id.txtdiachisldat);
        txtgia =view.findViewById(R.id.textView14);
        txtsdt = view.findViewById(R.id.txtsdtsldat);
        txtkc = view.findViewById(R.id.textView15);
        btnhuy = view.findViewById(R.id.btnhuy);
        lout = view.findViewById(R.id.viewlichsu);
        getuser = new getuser();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng salon = new LatLng(dx,dy);
        mMap.addMarker(new MarkerOptions().position(salon).title("salon bạn đặt"));
        getuser getuser = new getuser();
        Double x = getuser.x;
        Double y = getuser.y;
        LatLng sydney1 = new LatLng(x, y);
        MarkerOptions marker = new MarkerOptions().position(sydney1).title("vị trí của bạn");
        marker.icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_directions_walk_black_24dp));
        mMap.addMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1,15));
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_directions_walk_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 80, vectorDrawable.getIntrinsicHeight() + 40);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private void getclasssalon1(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try{
                        JSONArray jsonArray = new JSONArray(response);
                      //  for(int i = 0;i< jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            classsalon = new classsalon();
                            classsalon.setIdsalon(jsonObject.getInt("idsalon"));
                            classsalon.setSize(jsonObject.getInt("size"));
                            classsalon.setFreesize(jsonObject.getInt("freesize"));
                            classsalon.setDiachi(jsonObject.getString("diachi"));
                            classsalon.setPhuong(jsonObject.getString("phuong"));
                            classsalon.setImg1(jsonObject.getString("img1"));
                            classsalon.setSdt(jsonObject.getString("sdt"));
                            dx=jsonObject.getDouble("dx");
                            dy = jsonObject.getDouble("dy");
                            classsalon.setIvaluate(jsonObject.getDouble("ivaluate"));
                            classsalon.setPrice(jsonObject.getDouble("price"));
                            classsalon.setKc(jsonObject.getInt("kc"));

                            addevent();
//                            Log.d("aaa", jsonObject.toString());
                        //    Log.d("bbb",classsalon.toString());
                          //  arrsl.add(classsalon);

                              Log.d("bbb", "for"+classsalon.toString());
                      //  }

//                        Toast.makeText(getActivity(),arrx.get(1),Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        lout.setHovered(false);
                        Toast.makeText(getActivity(),"Bạn Không có lịch đặt",Toast.LENGTH_LONG).show();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"loi111",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                getuser getuser = new getuser();
                Map<String,String> map = new HashMap<>();
                map.put("dx",String.valueOf(getuser.x));
                map.put("dy",String.valueOf(getuser.y));
                map.put("sdt",getuser.number);
                return  map;
            }
        }
                ;
        requestQueue.add(stringRequest);
    }

    private void huydat(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getActivity(),BlankFragment4.class);
//                    startActivity(intent);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"bạn không có lịch đặt",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                getuser getuser = new getuser();
                Map<String,String> map = new HashMap<>();
                map.put("sdt",getuser.number);
                map.put("idsalon",String.valueOf(classsalon.getIdsalon()));
                return  map;
            }
        }
                ;
        requestQueue.add(stringRequest);
    }

}
