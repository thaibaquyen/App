package com.example.navigation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
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

public class adapterdanhgia extends BaseAdapter {
    profiledanhgia context;
    int layout;
    ArrayList<classsalon> arrsalon;

    public adapterdanhgia(profiledanhgia context, int layout, ArrayList<classsalon> arrsalon) {
        this.context = context;
        this.layout = layout;
        this.arrsalon = arrsalon;
    }
    public getuser getuser = new getuser();

    @Override
    public int getCount() {
        return arrsalon.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        ImageView img;
        TextView txtdc;
        EditText editText;
        RatingBar ratingBar;
        Button btn;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final adapterdanhgia.ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.img = view.findViewById(R.id.appCompatImageView);
            viewHolder.txtdc = view.findViewById(R.id.textViewdiachi);
            viewHolder.editText = view.findViewById(R.id.editTextdanhgia);
            viewHolder.ratingBar = view.findViewById(R.id.ratingBardanhgia);
            viewHolder.btn = view.findViewById(R.id.buttoncommit);
            view.setTag(viewHolder);
        }else {
            viewHolder = (adapterdanhgia.ViewHolder) view.getTag();
        }
        final classsalon classsalon = arrsalon.get(i);
        viewHolder.txtdc.setText(classsalon.getDiachi());
        viewHolder.ratingBar.setRating(0);
        loadimg loadimg = new loadimg(viewHolder.img);
        loadimg.execute("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/img/"+classsalon.getImg1());
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.editText.getText().length() < 1 || viewHolder.ratingBar.getRating() == 0 ){
                    Toast.makeText(context.getActivity(),"Bạn chưa đánh giá",Toast.LENGTH_LONG).show();
                }else {
                    String danhgia = viewHolder.editText.getText().toString();
                    Float sao = viewHolder.ratingBar.getRating();
                    putstring("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/postviewcomment.php",danhgia,sao,classsalon.getIdsalon(),getuser.number);
                   // Toast.makeText(context,danhgia+"    " +sao.toString()+"   "+classsalon.getIdsalon()+"    "+ getuser.number,Toast.LENGTH_LONG).show();
                }
            }
        });
            return view;

    }
    private void putstring(String url, final String coment, final Float start,final int idsalon, final String number){
        RequestQueue requestQueue = Volley.newRequestQueue(context.getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                        Toast.makeText(context.getActivity(),"Cảm ơn đóng góp của bạn",Toast.LENGTH_LONG).show();
                        context.addcontrol();
//                        Intent intent = new Intent(context,profiledanhgia.class);
//                        context.startActivity(intent);
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
                map.put("comment",coment);
                map.put("start",String.valueOf(start));
                map.put("idsalon",String.valueOf(idsalon));
                map.put("sdt",number);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
