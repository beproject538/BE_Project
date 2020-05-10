package com.example.arijitlahiri.be_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ConnectionPageActivity extends AppCompatActivity {

    String did,token,ConDid, ConName;
    JSONObject details;
    TextView byline, conName, credOffer;
    ImageButton refresh, back;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1,TimeUnit.MINUTES)
            .writeTimeout(1,TimeUnit.MINUTES)
            //.callTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();


    void getConnectionApppost(String URL, String requestBody) throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(JSON, requestBody);
        okhttp3.Request request = new okhttp3.Request.Builder()
                //.addHeader("Authorization",token)
                .url(URL)
                .post(body)
                .build();

        //final String res = "" ;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("status","Request Failed"+e);
                call.cancel();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                //Log.d("Res",response.body().string());
                SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("getConnectionsApp",response.body().string());
                editor.commit();

                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        //return res;
    }

    void listenForCredentialOfferPost(String URL, String requestBody) throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(JSON, requestBody);
        okhttp3.Request request = new okhttp3.Request.Builder()
                //.addHeader("Authorization",token)
                .url(URL)
                .post(body)
                .build();

        //final String res = "" ;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("status","Request Failed"+e);
                call.cancel();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                //Log.d("Res",response.body().string());
                SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("listenForCredentialOffer",response.body().string());
                editor.commit();

                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        //return res;
    }
    void createCredentialRequestPost(String URL, String requestBody, final String token) throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(JSON, requestBody);
        Request request = new Request.Builder()
                .addHeader("Authorization",token)
                .url(URL)
                .post(body)
                .build();

        //final String res ;
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
                editor.putString("createCredentialRequest",response.body().string());
                editor.commit();

                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        //return res;
    }
    void getCredentialsPost(String URL, String requestBody, final String token) throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(JSON, requestBody);
        Request request = new Request.Builder()
                .addHeader("Authorization","Bearer "+token)
                .url(URL)
                .post(body)
                .build();

        //final String res ;
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
                editor.putString("credentials",response.body().string());
                editor.commit();

                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        //return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_page);

        byline = findViewById(R.id.byline);
        refresh = findViewById(R.id.refresh);
        back = findViewById(R.id.edit_back_button);
        conName = findViewById(R.id.conName);
        credOffer = findViewById(R.id.credOffer);

        Bundle bundle  =  getIntent().getExtras();
        ConDid = bundle.getString("did");
        ConName = bundle.getString("name");

        conName.setText(ConName);
        byline.setText("You added "+ConName+" as a Connection");

        final SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        token = myPrefs.getString("token",null);
        did = myPrefs.getString("did",null);

        final String url ="http://ec2-13-127-141-57.ap-south-1.compute.amazonaws.com:8080/";

        final String indyurl = "http://ec2-13-127-141-57.ap-south-1.compute.amazonaws.com:8082/";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("did", did);
        }
        catch(JSONException e){

        }
        final String requestBody = jsonBody.toString();
        try{
            getConnectionApppost(url+"getConnectionsApp",requestBody);

            String response = myPrefs.getString("getConnectionsApp",null);
            JSONArray Res = new JSONArray(response);

            for(int i=0;i<Res.length();i++){
                JSONObject j = Res.getJSONObject(i);
                if(j.getString("did").equals(ConDid)){
                    details = j;
                    break;
                }
            }
        }
        catch(final Exception e){

        }


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    /*listenForCredentialOfferPost(url+"listenForCredentialOffer",requestBody);
                    String credOfferStatus = myPrefs.getString("listenForCredentialOffer",null);

                     JSONArray arr = new JSONArray(credOfferStatus);
                    for(int i=0;i<arr.length();i++){
                        JSONObject j = arr.getJSONObject(i);
                        credOffer.append("\ntrxid:"+j.getString("trxid"));
                        credOffer.append("\nschemaname:"+j.getString("schemaname"));
                        credOffer.append("\nsenderdid:"+j.getString("senderdid"));
                        credOffer.append("\nrecipientdid:"+j.getString("recipientdid"));
                        credOffer.append("\nstatus"+j.getString("status"));
                    }*/

                    JSONObject credReqBody = new JSONObject();
                    try {
                        credReqBody.put("recipientDid", ConDid);

                        credReqBody.put("did", did);
                    }
                    catch(JSONException e){ }
                    /*String credReq = credReqBody.toString();
                    createCredentialRequestPost(url+"createCredentialRequest",credReq,token);
                    String credReqResponse = myPrefs.getString("createCredentialRequest", null);
                    credOffer.append("\n\n"+credReqResponse);
*/
                    credReqBody.remove("did");
                    credReqBody.put("name","College id2");
                    String credBody = credReqBody.toString();
                    getCredentialsPost(indyurl+"getCredentials","",token);
                    String credentials = myPrefs.getString("credentials",null);

                    credOffer.setText(credentials);
                }
                catch(final Exception e){}
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forward = new Intent(ConnectionPageActivity.this, MainActivity.class);
                ConnectionPageActivity.this.startActivity(forward);
            }
        });
    }
}
