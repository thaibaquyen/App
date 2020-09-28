package com.example.navigation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;


public class editsalon extends Fragment implements View.OnClickListener {

    private static final int RESULT_OK = 2;
    View view;
    private ImageView imageView;
    private Button btnselect, btnput;
    private TextView textView;
    private int IMG_result = 1;
    private Bitmap bitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_editsalon, container, false);
        addcontrol();
        addevent();
        return  view;
    }

    private void addcontrol() {
        imageView = view.findViewById(R.id.imageView);
        btnput = view.findViewById(R.id.btnput);
        btnselect = view.findViewById(R.id.btnselect);
        textView = view.findViewById(R.id.txtbitmap);
    }

    private void addevent() {
        btnselect.setOnClickListener(this);
        btnput.setOnClickListener(this);
    }
    public void selectt(){

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"),
                    IMG_result);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_result && data != null){
            Uri url = data.getData();
            Log.d("bv",url.toString());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),url);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnselect:
                selectt();
                break;
        }
    }
}
