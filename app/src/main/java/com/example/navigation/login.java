package com.example.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity implements LocationListener {

    private Button btnlogin, Btnloginsalon, btnsend;
    private EditText txtsdt, editotp;
    LocationManager locationManager;
    private static double x = 0;
    private static double y = 0;
    private String verificationCode,otp;
    static getuser getuser;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private static int taikhoan = 0;
    public static Boolean result = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        begin();

    }

    private void begin() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            getuser = new getuser(this);
            getLocation();
            StartFirebaseLogin();
            addcontrol();
            addevent();
        }else{
            showGPSDisabledAlertToUser();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        begin();
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Bạn chưa bật GPS")
                .setCancelable(false)
                .setPositiveButton("Bật nó ngay",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }
    void getLocation() {
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    private void addevent() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numb = txtsdt.getText().toString()+"1";
                getuser.number = numb;
                taikhoan = 1;
                getuser.taikhoan = 1;
                sendsms(view);
//                Toast.makeText(login.this, getuser.readData() ,Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(login.this,MainActivity.class);
//                startActivity(intent);
               // getuser.savefile(txtsdt.getText().toString());
            }
        });
        Btnloginsalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numb = txtsdt.getText().toString()+"2";
                getuser.number = numb;
                taikhoan = 2;
                getuser.taikhoan = 2;
                sendsms(view);
            }
        });
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp=editotp.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                SigninWithPhone(credential);
            }
        });
        editotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editotp.setText("");
            }
        });
        txtsdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtsdt.setText("+84");
            }
        });
    }

    private void addcontrol() {
        btnlogin = findViewById(R.id.btnlogin);
        txtsdt = findViewById(R.id.editText6);
        Btnloginsalon = findViewById(R.id.button);
        btnsend = findViewById(R.id.button2);
        editotp = findViewById(R.id.editText7);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(this,getuser.number,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
//        Toast.makeText(this,"close",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        x = location.getLatitude();
        y = location.getLongitude();
        getuser.x = x;
        getuser.y = y;
        if(getuser.readData().trim().length() > 5){
            getuser.number = getuser.readData();
            int tt = Integer.parseInt(getuser.number.substring(12,13));
            if(tt == 1){
                Intent intent = new Intent(login.this,MainActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(login.this,MainActivitysalon.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    ////otp
    public void sendsms(View v){
        String phoneNumber = txtsdt.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            getstring("http://hairgrab.luyenthib1.com/baquyen/login.php");
                            if(taikhoan == 1){
                                if(result){
                                    getuser.savefile(getuser.number);
                                    startActivity(new Intent(login.this,MainActivity.class));
                                    Toast.makeText(login.this,getuser.number,Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }else {
//                                getstring("http://hairgrab.luyenthib1.com/baquyen/login.php");
                                if(result){
                                    getuser.savefile(getuser.number);
                                    startActivity(new Intent(login.this,MainActivitysalon.class));
                                    finish();
                                }else {
                                    startActivity(new Intent(login.this,Activitynewsalon.class));
                                    finish();
                                }
                            }
                        } else {
                            Toast.makeText(login.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void StartFirebaseLogin() {
        auth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(login.this,"verification completed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(login.this,"verification fialed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
//                Log.d("quyen",verificationCode);
                getstring("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/login.php");
                Toast.makeText(login.this,"Code sent",Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void getstring(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){

                    result = true;
//                    Toast.makeText(login.this,result.toString(),Toast.LENGTH_LONG).show();
                }else
                    if(response.equals("false")){
                    result = false;
//                    Toast.makeText(login.this,result.toString(),Toast.LENGTH_LONG).show();
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
                map.put("sdt",getuser.number);
                map.put("ma", String.valueOf(taikhoan));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
