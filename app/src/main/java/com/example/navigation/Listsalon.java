package com.example.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class Listsalon extends Fragment {
    private ViewFlipper viewFlipper;
    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.listsalon, container, false);
       // addcontrol();
        return  view;
    }

    private void addcontrol() {
        String[] arr = {"https://tse2.mm.bing.net/th?id=OIP.qFUltGMcA-TBLY94IFrW0AHaEo&pid=Api&P=0&w=252&h=158","https://photographer.vn/wp-content/uploads/2016/10/goi-y-nhung-dia-diem-chup-anh-dep-vao-thang-10.jpg"};
        viewFlipper = view.findViewById(R.id.viewflipper);
        for(int i = 0;i< arr.length;i++){
            ImageView img = new ImageView(getActivity());
            Picasso.with(getActivity()).load(arr[i]).into(img);
            viewFlipper.addView(img);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getActivity(),R.anim.file_in);
        Animation animation_out = AnimationUtils.loadAnimation(getActivity(),R.anim.file_out);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }
}
