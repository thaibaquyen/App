package com.example.navigation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BlankFragment3 extends Fragment{
    private View view;
    private static Spinner spinphuong;
    private Button btnseach;
    private static ArrayList<String> arrphuong;
    private static String phuong;
    private static ListView lv;
    private static adapterlissalon adapterlissalon;
    private static ArrayList<classsalon> arrayListsl;
    private static ArrayList<classsalon> arrayListsltam;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank_fragment3, container, false);
       addcontrol();
       xulylv();
       addgetarr();
       addeventspin();
       addeventcheck();
       return view;
    }

    private void xulylv(){
        getclasssalon("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/getsl.php");
//        getclasssalon("https://cutheadvinhcty.000webhostapp.com/baquyen/baquyen/do_an_tn/getsalon.php");
        Log.d("cc",String.valueOf(arrayListsl.size()));
        adapterlissalon = new adapterlissalon(getActivity(),R.layout.dongsalon,arrayListsltam);
        lv.setAdapter(adapterlissalon);
    }

    private void addeventcheck() {
        btnseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phuong.trim().length() == 0){
                    arrayListsltam.clear();
                    for(int i = 0;i< arrayListsl.size();i++){
                        arrayListsltam.add(arrayListsl.get(i));
                    }
                    adapterlissalon.notifyDataSetChanged();
                }else {
                   // Toast.makeText(getActivity(),phuong.toString(),Toast.LENGTH_LONG).show();
                    arrayListsltam.clear();
                    for(classsalon classsalon:arrayListsl){
                        if(classsalon.getPhuong().trim().equals(phuong.trim())){
                            arrayListsltam.add(classsalon);
                        }
                    }
                    adapterlissalon.notifyDataSetChanged();
                }
            }
        });
    }

    private void addeventspin() {
//        Toast.makeText(getActivity(),"phuong"+arrphuong.size(),Toast.LENGTH_LONG).show();
        spinphuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    phuong = "";
                }else {
                    phuong = arrphuong.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                phuong = "";
            }
        });
    }

    private void addgetarr() {
        ArrayAdapter<String> adapphuong = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,arrphuong);
        adapphuong.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinphuong.setAdapter(adapphuong);
    }

    private void addcontrol() {
        spinphuong = view.findViewById(R.id.phuong);
        btnseach = view.findViewById(R.id.btnseach);
        arrphuong = new ArrayList<>();
        arrphuong.add("chọn phường");
//        getarraypuong("http://hairgrab.luyenthib1.com/baquyen/getphuong.php");

        getarraypuong("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/getphuong.php");
        lv = view.findViewById(R.id.lvsalon);
        arrayListsl = new ArrayList<>();
        arrayListsltam = new ArrayList<>();
//        Toast.makeText(getActivity(),"phuong"+arrphuong.size(),Toast.LENGTH_LONG).show();
    }
    private void getarraypuong(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0 ;i<response.length();i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        arrphuong.add(jsonObject.getString("phuong"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void getclasssalon(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try{
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0;i< jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            classsalon classsalon = new classsalon();
                            classsalon.setIdsalon(jsonObject.getInt("idsalon"));
                            classsalon.setSize(jsonObject.getInt("size"));
                            classsalon.setFreesize(jsonObject.getInt("freesize"));
                            classsalon.setDiachi(jsonObject.getString("diachi"));
                            classsalon.setPhuong(jsonObject.getString("phuong"));
                            classsalon.setImg1(jsonObject.getString("img1"));
                            //classsalon.setImg2(jsonObject.getString("img2"));
                            classsalon.setSdt(jsonObject.getString("sdt"));
                            classsalon.setDx(jsonObject.getDouble("dx"));
                            classsalon.setDy(jsonObject.getDouble("dy"));
                            classsalon.setIvaluate(jsonObject.getDouble("ivaluate"));
                            classsalon.setPrice(jsonObject.getDouble("price"));
                            classsalon.setKc(jsonObject.getInt("kc"));
//                            Log.d("aaa", jsonObject.toString());
                          Log.d("bbb",classsalon.toString());
                            arrayListsl.add(classsalon);
                            arrayListsltam.add(classsalon);
                          //  Log.d("dd", String.valueOf(arrayListsl.size()));
                        }
                        Log.d("dd", String.valueOf(arrayListsltam.size()));
                        adapterlissalon.notifyDataSetChanged();
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                getuser getuser = new getuser();
                Map<String,String> map = new HashMap<>();
                map.put("dx",String.valueOf(getuser.x));
                map.put("dy",String.valueOf(getuser.y));
                return  map;
            }
        }
                ;
        requestQueue.add(stringRequest);
    }
//    private void addctrol() {
//        String[] arr = {"http://thuthuatphanmem.vn/uploads/2018/09/11/hinh-anh-dep-10_044127763.jpg","https://cutheadvinhcty.000webhostapp.com/img/333.jpg"};
//        viewFlipper = view.findViewById(R.id.viewflipper);
//
//        for(int i = 0;i< arr.length;i++){
//            ImageView img = new ImageView(getActivity());
//            Picasso.with(getActivity()).load(arr[i]).into(img);
//            viewFlipper.addView(img);
//        }
//        viewFlipper.setFlipInterval(3000);
//        viewFlipper.setAutoStart(true);
//        Animation animation_in = AnimationUtils.loadAnimation(getActivity(),R.anim.file_in);
//        Animation animation_out = AnimationUtils.loadAnimation(getActivity(),R.anim.file_out);
//        viewFlipper.setInAnimation(animation_in);
//        viewFlipper.setOutAnimation(animation_out);
//    }
}

