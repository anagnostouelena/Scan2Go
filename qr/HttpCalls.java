package com.example.qr;

import static com.example.qr.MainActivity.groupIinfoTextView;
import static com.example.qr.MainActivity.trueORfalse;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.telecom.Call;
import android.util.Log;
import android.view.PixelCopy;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import javax.security.auth.callback.Callback;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpCalls extends AsyncTask<Void, Void, String> {

    Context ctxapi;
    TextView textResult;
    String point;
    String route_name;
    int success;
    int group_info;

    public HttpCalls(Context ctxapi, TextView textResult, String point) {
        this.ctxapi=ctxapi;
        this.textResult = textResult;
        this.point = point;
    }

    @Override
    protected String doInBackground(Void... params) {

        OkHttpClient client = new OkHttpClient();
        String group_name = textResult.getText().toString();
        String url = "http://10.0.10.1/game/controlers/getHttp.php?group_name=" + group_name + "&" + "point_name=" + point;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responceString =response.body().string();
            JSONObject json = new JSONObject(responceString);
            success =  json.getInt("success");
            route_name =  json.getString("routeName");
            group_info = json.getInt("group_info");

            Log.d("success",String.valueOf(success));
            System.out.println(success);
            System.out.println(route_name);
            System.out.println(group_info);
            System.out.println(responceString);

           
        } catch (JSONException | IOException e){
            throw new RuntimeException(e);

        }


        return  group_name;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        textResult.setText("Όνομα Ομάδας: " + result);
        MainActivity.resultRoute_name.setText("Όνομα Διαδρομής: " + route_name);

//        if (success == 0) {
//                trueORfalse.setImageResource(R.drawable.cancel);
//        } else if (success == 1) {
//            trueORfalse.setImageResource(R.drawable.ok);
//        }

        if (group_info == 0) {
            trueORfalse.setImageResource(R.drawable.broken);
        } else {
            if (success == 0) {
                trueORfalse.setImageResource(R.drawable.cancel);
            } else if (success == 1) {
                trueORfalse.setImageResource(R.drawable.ok);
            }
        }



    }




}





