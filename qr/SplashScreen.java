package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences preferences = getSharedPreferences("point", MODE_PRIVATE);

        String point = preferences.getString("point", "");

        if(point.equals("")){
            startActivity(new Intent(getApplicationContext(),PointsActivity.class));

        }
        else{
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("point",point);
            startActivity(intent);
        }


    }
}