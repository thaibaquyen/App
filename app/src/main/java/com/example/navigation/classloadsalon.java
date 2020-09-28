package com.example.navigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

public class classloadsalon extends AsyncTask<String, Void, JSONArray> {
    Context context;
    private ArrayList<classsalon> arrsalon;
    private static ArrayList<String> arrx;
    private static ArrayList<String> arry;
    private static ArrayList<String> arrname;
    private static ArrayList<Integer> arrfreesize;
    public static ProgressDialog progressDialog;

    public classloadsalon(Context context,ArrayList<String> arrx, ArrayList<String> arry, ArrayList<String> arrname, ArrayList<Integer> arrfreesize, ArrayList<classsalon> arrsalon) {
        this.context = context;
        this.arrsalon = arrsalon;
        this.arrx = arrx;
        this.arry = arry;
        this.arrname = arrname;
        this.arrfreesize = arrfreesize;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Chạm để dừng"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strings[0], new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try{
                        JSONArray jsonArray = new JSONArray(response);
                        arrx.clear();
                        arry.clear();
                        arrname.clear();
                        arrfreesize.clear();
                        arrsalon.clear();
                        for(int i = 0;i< jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            classsalon classsalon = new classsalon();
                            classsalon.setIdsalon(jsonObject.getInt("idsalon"));
                            classsalon.setSize(jsonObject.getInt("size"));
                            classsalon.setFreesize(jsonObject.getInt("freesize"));
                            classsalon.setDiachi(jsonObject.getString("diachi"));
                            classsalon.setSdt(jsonObject.getString("sdt"));
                            classsalon.setDx(jsonObject.getDouble("dx"));
                            classsalon.setDy(jsonObject.getDouble("dy"));
                            classsalon.setIvaluate(jsonObject.getDouble("ivaluate"));
                            classsalon.setPrice(jsonObject.getDouble("price"));
                            classsalon.setImg1(jsonObject.getString("img1"));
                            classsalon.setKc(jsonObject.getInt("kc"));
                            arrsalon.add(classsalon);
                            arrx.add(jsonObject.getString("dx"));
                            arry.add(jsonObject.getString("dy"));
                            arrname.add(jsonObject.getString("diachi"));
                            arrfreesize.add(jsonObject.getInt("freesize"));
                        }
                        progressDialog.dismiss();
//                        Toast.makeText(getActivity(),arrx.get(1),Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Toast.makeText(context,"loi",Toast.LENGTH_LONG).show();
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
        };
        requestQueue.add(stringRequest);
        return null;
    }
}
