package com.example.navigation;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mainchitiet extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static classsalon classsalon;
    private TextView txtdiachi,txtgia,txtsoghe,txtsdt;
    private Button btndatlick,btnlike;
    private static ListView lv;
    private static ArrayList<classcomment> arrcomments;
    private static adaptercomment adaptercomment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainchitiet);
        Intent intent = getIntent();
        classsalon = (com.example.navigation.classsalon) intent.getSerializableExtra("salon");
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapchitiet);
        supportMapFragment.getMapAsync(this);
        addcontrol();
        addfilldata();
        addevent();
       // Toast.makeText(this,classsalon.toString(),Toast.LENGTH_LONG).show();
    }

    private void addfilldata() {
        getstring("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/getdanhgia.php");
        adaptercomment = new adaptercomment(this,R.layout.dongcomment,arrcomments);
        Log.d("lv",String.valueOf(arrcomments.size()));
        lv.setAdapter(adaptercomment);
    }

    private void addcontrol() {
        txtdiachi = findViewById(R.id.textView2);
        txtsoghe = findViewById(R.id.textView3);
        txtgia = findViewById(R.id.textView5);
        txtsdt = findViewById(R.id.textView4);
        btndatlick = findViewById(R.id.button3);
        btnlike = findViewById(R.id.button4);
        lv=findViewById(R.id.lvcomment);
        arrcomments = new ArrayList<>();
        txtdiachi.setText(classsalon.getDiachi());
        txtsdt.setText(" "+classsalon.getSdt());
        txtgia.setText(" "+classsalon.getPrice().toString()+"đ");
        txtsoghe.setText("chỗ trống: "+classsalon.getFreesize());
    }

    private void addevent() {
        btndatlick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putstring("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/commitdatlich.php");
                //Toast.makeText(Mainchitiet.this,arrcomments.size(),Toast.LENGTH_LONG).show();
//                Log.d("loi",String.valueOf(classsalon.getIdsalon()));
            }
        });

        btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putstring("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/commitlike.php");
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng salon = new LatLng(classsalon.getDx(),classsalon.getDy());
        mMap.addMarker(new MarkerOptions().position(salon).title(classsalon.getDiachi()));
        getuser getuser = new getuser();
        Double x = getuser.x;
        Double y = getuser.y;
        LatLng sydney1 = new LatLng(x, y);
        MarkerOptions marker = new MarkerOptions().position(sydney1).title("vị trí của bạn");
        marker.icon(bitmapDescriptorFromVector(this,R.drawable.ic_directions_walk_black_24dp));
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

    private void getstring(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try {
                        JSONArray jsonArray =  new JSONArray(response);
                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject jsonObject =jsonArray.getJSONObject(i);
                        classcomment classcomment = new classcomment();
                        classcomment.setSdt(jsonObject.getString("sdtnguoidat"));
                        classcomment.setStart(jsonObject.getDouble("start"));
                        classcomment.setComment(jsonObject.getString("comment"));
                        classcomment.setDatetime(jsonObject.getString("datetime"));
                        arrcomments.add(classcomment);
                        }
                        adaptercomment.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idsalon",String.valueOf(classsalon.getIdsalon()));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void putstring(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    Toast.makeText(Mainchitiet.this,"success",Toast.LENGTH_LONG).show();
                }else
                if(response.equals("false")){
                    Toast.makeText(Mainchitiet.this,"failure",Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                getuser getuser = new getuser();
                Map<String,String> map = new HashMap<>();
                map.put("sdt",getuser.number);
                map.put("idsalon", String.valueOf(classsalon.getIdsalon()));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
