package com.example.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;

public class adaptercomment extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<classcomment> arrcomment;

    public adaptercomment(Context context, int layout, ArrayList<classcomment> arrcomment) {
        this.context = context;
        this.layout = layout;
        this.arrcomment = arrcomment;
    }

    @Override
    public int getCount() {
        return arrcomment.size();
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
        TextView txtsdt,txtcomment,txtdatetime,txtstart;
        //RatingBar ratingBar;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        adaptercomment.ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new adaptercomment.ViewHolder();
            viewHolder.txtsdt = view.findViewById(R.id.textView);
            viewHolder.txtcomment = view.findViewById(R.id.textView6);
            viewHolder.txtdatetime =view.findViewById(R.id.textView8);
            viewHolder.txtstart = view.findViewById(R.id.textView7);

         //   viewHolder.ratingBar = view.findViewById(R.id.simpleRatingBar);
            view.setTag(viewHolder);
        }else {
            viewHolder = (adaptercomment.ViewHolder) view.getTag();
        }

        final classcomment classcomment = arrcomment.get(i);
        viewHolder.txtsdt.setText(classcomment.getSdt().substring(0,7)+"xxx");
        viewHolder.txtcomment.setText(classcomment.getComment());
        viewHolder.txtdatetime.setText(classcomment.getDatetime());
        viewHolder.txtstart.setText(String.valueOf(classcomment.getStart()));
      //  viewHolder.ratingBar.setRating(Float.parseFloat(String.valueOf(classcomment.getStart())));
        return view;
    }
}
