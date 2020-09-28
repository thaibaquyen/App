package com.example.navigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class loadimg extends AsyncTask<String,Integer, Bitmap> {

    Bitmap bitmap = null;
    ImageView img;

    public loadimg(ImageView img) {
        this.img = img;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL u = new URL(strings[0]);
            InputStream inputStream = u.openConnection().getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap s) {
        super.onPostExecute(s);
        img.setImageBitmap(s);
    }
}
