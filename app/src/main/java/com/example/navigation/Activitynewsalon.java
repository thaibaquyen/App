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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Activitynewsalon extends AppCompatActivity {

    public EditText edtdc,edtname,edtgia,edtfreesize;
    public ImageView img;
    private Button btnsl, btnput;
    private Bitmap bitmap;
    private int IMG_result = 1;
    private static int j = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitynewsalon);
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
        btnput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diachi = edtdc.getText().toString();
                String sdt = edtname.getText().toString();
                String gia = edtgia.getText().toString();
                String size = edtfreesize.getText().toString();
//                String img = getimg(bitmap);
                if(diachi.length() < 5 || sdt.length() < 5 || gia.length() < 4 || size.length() < 1 || j == 0){
                    Toast.makeText(Activitynewsalon.this,"thiếu dữ liệu",Toast.LENGTH_LONG).show();
                }else {
                    putsalon("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/salon/putnewsalon.php");
                }
            }
        });
    }

    private String getimg(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgbyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte,Base64.DEFAULT);
    }

    private void addcontrol() {
        edtdc = findViewById(R.id.editTextdc);
        edtgia = findViewById(R.id.editText9);
        edtname = findViewById(R.id.editTextname);
        edtfreesize = findViewById(R.id.editText11);
        btnsl = findViewById(R.id.btnchonimg);
        btnput = findViewById(R.id.btnsubmit);
        img = findViewById(R.id.imageView3);
        getuser getuser = new getuser();
        Toast.makeText(Activitynewsalon.this,getuser.number,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_result && data != null){
            Uri url = data.getData();
            //Log.d("bv",url.toString());
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

    private void putsalon(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Activitynewsalon.this,response,Toast.LENGTH_LONG).show();
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
                map.put("diachi",edtdc.getText().toString());
                map.put("name",edtname.getText().toString());
                map.put("size",edtfreesize.getText().toString());
                map.put("sdt",getuser.number);
                map.put("gia",edtgia.getText().toString());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
