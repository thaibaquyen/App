package com.example.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class adapmarketmap implements GoogleMap.InfoWindowAdapter {
    private final View view;
    Context context;
    LayoutInflater layoutInflater = null;

    public adapmarketmap(Context context, View view) {
        this.context = context;
        this.view = view;
    }


    @Override
    public View getInfoWindow(final Marker marker) {
        classsalon classsalon = (com.example.navigation.classsalon) marker.getTag();
        LinearLayout linearLayout = view.findViewById(R.id.viewmapct);
        ImageView img = view.findViewById(R.id.imgsalon1);
        TextView txtdc = view.findViewById(R.id.txtdiachi1);
        TextView txtgia = view.findViewById(R.id.txtgia1);
        TextView txtsdt = view.findViewById(R.id.txtsdt1);
        TextView txtghe = view.findViewById(R.id.txtghe1);
        TextView txtkc = view.findViewById(R.id.txtkc1);
        RatingBar ratingBar = view.findViewById(R.id.simpleRatingBar1);
        if(classsalon != null){
            txtdc.setText(classsalon.getDiachi());
            txtgia.setText(classsalon.getPrice().toString()+"đ");
            txtsdt.setText(classsalon.getSdt());
            if(classsalon.getFreesize() > 0)
                txtghe.setText(String.valueOf(classsalon.getFreesize())+" / "+String.valueOf(classsalon.getSize()));
            else
                txtghe.setText("Hết Chỗ");
            ratingBar.setRating(Float.parseFloat(String.valueOf(classsalon.getIvaluate())));
            txtkc.setText(classsalon.getKc()+"m");
            loadimg loadimg = new loadimg(img);
            loadimg.execute("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/img/"+classsalon.getImg1());
            txtgia.setEnabled(false);
            txtsdt.setEnabled(false);
            txtghe.setEnabled(false);
            txtkc.setEnabled(false);
            ratingBar.setEnabled(false);
            txtdc.setText(marker.getTitle());
            linearLayout.setVisibility(View.VISIBLE);
        }else {
            linearLayout.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
