package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class fontdangki extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fontdangki);
        Intent intent =  this.getIntent();
        Toast.makeText(fontdangki.this,intent.getStringExtra("data"),Toast.LENGTH_LONG).show();
    }
}
