package com.example.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivitysalon extends AppCompatActivity {

    public Switch aSwitch;
    public static ListView lv;
    public ArrayList<classuserbook> arrlis;
    public adapterlisdatlich adapterlisdatlich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitysalon);
        addcontrol();
        addevent();
        addswich();
    }

    private void addswich() {
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edittrangthai("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/salon/edittrangthai.php",1);
                }else {
                    edittrangthai("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/salon/edittrangthai.php",0);
                }
            }
        });
    }

    public void addevent() {
        arrlis = new ArrayList<>();
        getlisuserbook("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/salon/getuserdatlich.php");
        adapterlisdatlich = new adapterlisdatlich(this,R.layout.donglisuserbook,arrlis);
        lv.setAdapter(adapterlisdatlich);
    }

    public void addcontrol() {
        lv=findViewById(R.id.lvdsdat);
        aSwitch = findViewById(R.id.switch1);
        gettrangthai("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/salon/gettrangthai.php");
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salon,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.edit:
                Intent intentt = new Intent(this,Activityeditsalon.class);
                startActivity(intentt);
                break;
            case R.id.dangxuat:
                getuser getuser = new getuser(this);
                getuser.savefile("");
                Intent intent = new Intent(this,login.class);
                startActivity(intent);
                break;
        }
        return false;
    }

    private void gettrangthai(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    aSwitch.setChecked(true);
                }else
                if(response.equals("false")){
                    aSwitch.setChecked(false);
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
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void edittrangthai(String url, final int tt){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               Toast.makeText(MainActivitysalon.this,response,Toast.LENGTH_LONG).show();
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
                map.put("tt",String.valueOf(tt));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getlisuserbook(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            classuserbook classuserbook = new classuserbook();
                            classuserbook.setIdsalon(Integer.parseInt(jsonObject.getString("idsalon")));
                            classuserbook.setSdtnguoidat(jsonObject.getString("sdtnguoidat"));
                            classuserbook.setDatetime(jsonObject.getString("datetime"));
                            arrlis.add(classuserbook);
                        }
                        adapterlisdatlich.notifyDataSetChanged();
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
                getuser getuser = new getuser();
                Map<String,String> map = new HashMap<>();
                map.put("sdt",getuser.number);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void deleteuserbook(String url, final String sdtnguoidat){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    Toast.makeText(MainActivitysalon.this,response,Toast.LENGTH_LONG).show();
                    addevent();
//                    getlisuserbook("http://hairgrab.luyenthib1.com/baquyen/salon/getuserdatlich.php");
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
                map.put("sdtnguoidat",sdtnguoidat);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void finishuserbook(String url, final String sdtnguoidat){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    Toast.makeText(MainActivitysalon.this,response,Toast.LENGTH_LONG).show();
                    addevent();
//                    getlisuserbook("http://hairgrab.luyenthib1.com/baquyen/salon/getuserdatlich.php");
                  //  adapterlisdatlich.notifyDataSetChanged();
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
                map.put("sdtnguoidat",sdtnguoidat);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
