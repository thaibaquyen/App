package com.example.navigation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Activityeditsalon extends AppCompatActivity {
    public static ImageView img;
    private static EditText sdt,tien,ghe;
    public static Button btnsl, btnup;
    public int IMG_result =1;
    public  Bitmap bitmap;
    public static String ttimg ;
    private static int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityeditsalon);
        addcontrol();
        addevent();
    }

    private void addevent() {
        btnsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"),
                        IMG_result);
            }
        });

        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // putimg("http://hairgrab.luyenthib1.com/baquyen/salon/putnewsalon.php");
//                Toast.makeText(Activityeditsalon.this,getimg(bitmap),Toast.LENGTH_LONG).show();
                if(sdt.getText().length() < 9 || ghe.getText().length() < 0 || tien.getText().length() < 5){
                    Toast.makeText(Activityeditsalon.this,"Thiếu Dữ Liệu",Toast.LENGTH_LONG).show();
                }else {
                    if(j > 0)
                    {
//                        Log.d("bv",getimg(bitmap).toString());
                        putedit("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/salon/editsalon.php");
                    }else {
                        putedit1("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/salon/editsalon.php");
                    }
                }
            }
        });
    }

    private void addcontrol() {
        img = findViewById(R.id.imageView2);
        btnsl = findViewById(R.id.buttonseach);
        btnup = findViewById(R.id.button6);
        sdt = findViewById(R.id.editsdt);
        tien = findViewById(R.id.editgia);
        ghe = findViewById(R.id.editsoghe);
        ttimg = "";
        getsl("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/salon/getmysalon.php");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_result && data != null){
            Uri url = data.getData();
            Log.d("bv",url.toString());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),url);
                img.setImageBitmap(bitmap);
                j = 1;
//                Toast.makeText(this,url.toString(),Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String getimg(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgbyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte,Base64.DEFAULT);
    }

    private void putedit(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Activityeditsalon.this,response.toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Activityeditsalon.this,MainActivitysalon.class);
                startActivity(intent);
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
                getuser getuser = new getuser();
                map.put("img",getimg(bitmap));
                map.put("sdtnew",sdt.getText().toString().trim());
                map.put("price",tien.getText().toString().trim());
                map.put("size",ghe.getText().toString().trim());
                map.put("sdt",getuser.number);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void putedit1(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Activityeditsalon.this,response,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Activityeditsalon.this,MainActivitysalon.class);
                startActivity(intent);
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
                getuser getuser = new getuser();
                map.put("img",ttimg);
                map.put("sdtnew",sdt.getText().toString().trim());
                map.put("price",tien.getText().toString().trim());
                map.put("size",ghe.getText().toString().trim());
                map.put("sdt",getuser.number);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getsl(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        sdt.setText(jsonObject.getString("sdt"));
                        ghe.setText(jsonObject.getString("size"));
                        tien.setText(jsonObject.getString("price"));
                        loadimg loadimg = new loadimg(img);
                        loadimg.execute("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/img/"+jsonObject.getString("img1"));
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
                getuser getuser = new getuser();
                map.put("sdt",getuser.number);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
