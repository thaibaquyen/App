package com.example.navigation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class adapterlissalon extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<classsalon> arrsalon;

    public adapterlissalon(Context context, int layout, ArrayList<classsalon> arrsalon) {
        this.context = context;
        this.layout = layout;
        this.arrsalon = arrsalon;
    }

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
        TextView txtdc,txtgia,txtsdt,txtghe,txtkc;
        RatingBar ratingBar;
        Button btn;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.img = view.findViewById(R.id.imgsalon);
            viewHolder.txtdc = view.findViewById(R.id.txtdiachi);
            viewHolder.txtgia = view.findViewById(R.id.txtgia);
            viewHolder.txtsdt = view.findViewById(R.id.txtsdt);
            viewHolder.txtghe = view.findViewById(R.id.txtghe);
            viewHolder.txtkc = view.findViewById(R.id.txtkc);
            viewHolder.ratingBar = view.findViewById(R.id.simpleRatingBar);
            viewHolder.btn=view.findViewById(R.id.chitiet);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final classsalon classsalon = arrsalon.get(i);
        viewHolder.txtdc.setText(classsalon.getDiachi());
        viewHolder.txtgia.setText(classsalon.getPrice().toString()+"đ");
        viewHolder.txtsdt.setText(classsalon.getSdt());
        viewHolder.txtghe.setText(String.valueOf(classsalon.getFreesize())+" / "+String.valueOf(classsalon.getSize()));
        viewHolder.ratingBar.setRating(Float.parseFloat(String.valueOf(classsalon.getIvaluate())));
        viewHolder.ratingBar.setEnabled(false);
        viewHolder.txtkc.setText(classsalon.getKc()+"m");
        loadimg loadimg = new loadimg(viewHolder.img);
        loadimg.execute("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/img/"+classsalon.getImg1());
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.animation_lv);
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Mainchitiet.class);
                intent.putExtra("salon",classsalon);
                context.startActivity(intent);
            }
        });
        view.setAnimation(animation);
        return view;
    }
}
