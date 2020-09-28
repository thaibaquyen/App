package com.example.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class adapterlisdatlich extends BaseAdapter {
    public MainActivitysalon context;
    public int layout;
    public ArrayList<classuserbook> arrayList;

    public adapterlisdatlich(MainActivitysalon context, int layout, ArrayList<classuserbook> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
        TextView txtsdt,txttime;
        Button btnhuy, btnfinish;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        adapterlisdatlich.ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.txtsdt = view.findViewById(R.id.txtsdtuser);
            viewHolder.btnhuy = view.findViewById(R.id.btncancel);
            viewHolder.btnfinish = view.findViewById(R.id.btnfinish);
            viewHolder.txttime = view.findViewById(R.id.txtdatetimedat);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final classuserbook classuserbook = arrayList.get(i);
            viewHolder.txtsdt.setText(classuserbook.getSdtnguoidat());
            viewHolder.txttime.setText(classuserbook.getDatetime());
            viewHolder.btnhuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.deleteuserbook("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/salon/huyuserdat.php",classuserbook.getSdtnguoidat());
                    // Toast.makeText(context,"sdnguoidat : "+classuserbook.getSdtnguoidat()+ " idsalon: "+classuserbook.getIdsalon(),Toast.LENGTH_LONG).show();
                }
            });
            viewHolder.btnfinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.finishuserbook("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/salon/finish.php",classuserbook.getSdtnguoidat());
                }
            });
        return view;
    }
}
