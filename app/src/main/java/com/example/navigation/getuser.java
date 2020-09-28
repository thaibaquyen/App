package com.example.navigation;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class getuser {
    public static Double x=null;
    public static Double y=null;
    public static String number=null;
    public static Integer taikhoan = 0;
    public Context context;

    public getuser() {
    }

    public getuser(Context context) {
        this.context = context;
    }

    public void savefile(String s){
        String fileName = "token.txt";
        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
    public String readData(){
        BufferedReader input = null;
        File file = null;
        StringBuffer buffer = new StringBuffer();

        try {
            file = new File(context.getFilesDir(), "token.txt");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
