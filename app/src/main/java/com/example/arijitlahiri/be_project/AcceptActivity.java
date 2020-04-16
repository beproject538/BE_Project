package com.example.arijitlahiri.be_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;
import android.os.Handler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AcceptActivity extends AppCompatActivity {

    TextView userName,orgName,requestText,demoText;
    ImageView userLogo, orgLogo;
    Button deny,accept;
    String scanResult;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(2,TimeUnit.MINUTES)
            .readTimeout(2,TimeUnit.MINUTES)
            .writeTimeout(2,TimeUnit.MINUTES)
            //.callTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();


    void initiateConnectionpost(String URL, String requestBody, final String token) throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(JSON, requestBody);
        Request request = new Request.Builder()
                .addHeader("Authorization",token)
                .url(URL)
                .post(body)
                .build();

        final String res ;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("status","Request Failed"+e);
                call.cancel();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.d("Res",response.body().string());
                SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                try {
                    JSONObject Result = new JSONObject(response.body().string());

                }catch (JSONException e){}
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        //return res;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        userName = findViewById(R.id.userName);
        orgName = findViewById(R.id.OrgName);
        requestText = findViewById(R.id.requestText);
        userLogo = findViewById(R.id.userLogo);
        orgLogo = findViewById(R.id.orgLogo);
        deny = findViewById(R.id.deny);
        accept = findViewById(R.id.accept);
        demoText = findViewById(R.id.demoText);

        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        //userName.setText(myPrefs.getString("name", null));
        Bundle bundle  =  getIntent().getExtras();
        scanResult = bundle.getString("result");

        demoText.setVisibility(View.VISIBLE);
        try{
            JSONObject j = new JSONObject(scanResult);
            demoText.setText("Scan Result\nDID : "+j.getString("did")+"\nToken : "+j.getString("token"));
        }
        catch(JSONException e){

        }


        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AcceptActivity.this, MainActivity.class);
                AcceptActivity.this.startActivity(back);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent forward = new Intent(AcceptActivity.this, MainActivity.class);
                AcceptActivity.this.startActivity(forward);
            }
        });

    }
}
