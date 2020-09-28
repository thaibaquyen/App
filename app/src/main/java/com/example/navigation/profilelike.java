package com.example.navigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class profilelike extends Fragment {

    private View view;
    private static ListView lv;
    private static ArrayList<classsalon> arrayList;
    private static adapterlissalon adapterlissalon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profilelike, container, false);
        addcontrol();
        return  view;
    }

    private void addcontrol() {
        arrayList = new ArrayList<>();
        lv = view.findViewById(R.id.lvlslike);
        getclasssalon("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/getlislike.php");
        adapterlissalon = new adapterlissalon(getActivity(),R.layout.dongsalon,arrayList);
        lv.setAdapter(adapterlissalon);
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
                          //  classsalon.setImg2(jsonObject.getString("img2"));
                            classsalon.setSdt(jsonObject.getString("sdt"));
                            classsalon.setDx(jsonObject.getDouble("dx"));
                            classsalon.setDy(jsonObject.getDouble("dy"));
                            classsalon.setIvaluate(jsonObject.getDouble("ivaluate"));
                            classsalon.setPrice(jsonObject.getDouble("price"));
                            classsalon.setKc(jsonObject.getInt("kc"));
//                            Log.d("aaa", jsonObject.toString());
                         //   Log.d("bbb",classsalon.toString());
                            arrayList.add(classsalon);
                            //  Log.d("dd", String.valueOf(arrayListsl.size()));
                        }
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
                map.put("sdt",getuser.number);
                return  map;
            }
        }
                ;
        requestQueue.add(stringRequest);
    }
}
