package com.example.qr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;



import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity {

    public static TextView resultGroup_name, resultRoute_name,point_name,groupIinfoTextView;
    public static ImageView trueORfalse;
    ImageButton scannQR;

    public static  String point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultGroup_name = findViewById(R.id.group_name);
        trueORfalse =findViewById(R.id.trueORfalse);
        point_name =findViewById(R.id.point_name);
        groupIinfoTextView =findViewById(R.id.group_info);

        scannQR=findViewById(R.id.scannQR);

        resultRoute_name = findViewById(R.id.route_name);

        Bundle bundle =getIntent().getExtras();
         point = bundle.getString("point");
         point_name.setText(point);




        scannQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQRScanner();

            }
        });

    }

    private void startQRScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setOrientationLocked(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Εάν το αποτέλεσμα είναι κενό
                resultGroup_name.setText("Scan canceled");

            } else {
                // Εάν βρέθηκε QR Code
                resultGroup_name.setText(result.getContents());

                HttpCalls httpCalls = new HttpCalls(MainActivity.this, resultGroup_name,point);
                httpCalls.execute();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if(true){

        }else{
            super.onBackPressed();
        }

    }

}
