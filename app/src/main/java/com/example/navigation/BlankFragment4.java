package com.example.navigation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class BlankFragment4 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank_fragment4, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = item.getItemId();
        switch (id) {
            case R.id.lichdadat:
                profilelichsu profilelichsu = new profilelichsu();
                fragmentTransaction.replace(R.id.framprofile,profilelichsu);
                fragmentTransaction.commit();
                break;
            case R.id.like:
                profilelike profilelike = new profilelike();
                fragmentTransaction.replace(R.id.framprofile,profilelike);
                fragmentTransaction.commit();
                break;
            case R.id.danhgia:
                profiledanhgia profiledanhgia = new profiledanhgia();
                fragmentTransaction.replace(R.id.framprofile,profiledanhgia);
                fragmentTransaction.commit();
                break;
            case R.id.logout:
                getuser getuser = new getuser(getActivity());
                getuser.savefile("");
                Intent intent = new Intent(getActivity(),login.class);
                startActivity(intent);
                break;
        }

        return false;
    }

    public void getstring(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    try{
                        JSONArray jsonArray = new JSONArray(response);
                        Intent intent = new Intent(getActivity(),fontdangki.class);
                        intent.putExtra("data",jsonArray.toString());
                        startActivity(intent);
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("tk", "thaiba");
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
