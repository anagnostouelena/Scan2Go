package com.example.qr;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PointsActivity extends AppCompatActivity {

    ListView allPointsNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        allPointsNames= findViewById(R.id.allPointsNames);
        HttpCallsPoints httpCallsPoints = new HttpCallsPoints(PointsActivity.this,allPointsNames);
        httpCallsPoints.execute();


    }
}
class HttpCallsPoints extends AsyncTask<Void, Void, String> {

    Context ctx;
    ListView pointsListView;
    List<String> points;
    Response response;

    public HttpCallsPoints(Context ctx,ListView pointsListView) {
        this.ctx=ctx;
        this.pointsListView = pointsListView;
    }

    @Override
    protected String doInBackground(Void... params) {

        OkHttpClient client = new OkHttpClient();

        String url = "http://10.0.10.1/game/controlers/getPoint.php";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            response = client.newCall(request).execute();
            String body=response.body().string();
            System.out.println(body);
            JSONArray pointsJson =new JSONArray(body);

            for(int i=0; i<pointsJson.length(); i++){
                JSONObject point = pointsJson.getJSONObject(i);
                String pointName = point.getString("point_name");
                System.out.println(pointName);
                points.add(pointName);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            response.close();

        }

        return url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        points=new ArrayList<>();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ArrayAdapter adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, points);
        pointsListView.setAdapter(adapter);

        pointsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println(points.get(i));
                SharedPreferences preferences = ctx.getSharedPreferences("point", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("point", points.get(i));
                editor.apply();
                Intent intent = new Intent(ctx,MainActivity.class);
                intent.putExtra("point",points.get(i));
                ctx.startActivity(intent);

            }
        });

    }



}
