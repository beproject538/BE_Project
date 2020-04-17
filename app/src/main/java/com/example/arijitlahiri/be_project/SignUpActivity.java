package com.example.arijitlahiri.be_project;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.NetworkResponse;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.HashMap;

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

import android.widget.ProgressBar;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText nameField, aadharField;
    Button signUpButton;
    String name, aadhar_no;
    String token, did, verkey, metadata;
    String WalletResponse;
    ProgressBar progressBar;
    int progressStatus = 0;
    Handler handler = new Handler();


    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(15,TimeUnit.MINUTES)
            .readTimeout(15,TimeUnit.MINUTES)
            .writeTimeout(15,TimeUnit.MINUTES)
            //.callTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();


    void createWalletpost(String URL, String requestBody) throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(JSON, requestBody);
        Request request = new Request.Builder()
                //.addHeader("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTg1Njg3OTIwNDFlMzI2Zjg0YjRjMDciLCJpYXQiOjE1ODU4MDEzMzl9.zcH6m1Nehg6Salw_E0AkJLvrlnPqdIX31Ws-kXjGnEM")
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
                editor.putString("createWallet",response.body().string());
                editor.commit();
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        //return res;
    }
    void createDidpost(String URL, String requestBody, final String token) throws IOException, InterruptedException {
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
                    did = Result.getString("did");
                    verkey = Result.getString("verkey");
                    metadata = Result.getString("metadata");
                }catch (JSONException e){}
                editor.putString("name", name);
                editor.putString("token", token);
                editor.putString("did",did);
                editor.putString("verkey",verkey);
                editor.putString("metadata",metadata);
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
        setContentView(R.layout.activity_sign_up);

        nameField = (TextInputEditText) findViewById(R.id.name);
        aadharField = (TextInputEditText) findViewById(R.id.aadhar_no);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);


        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                name = nameField.getText().toString();
                aadhar_no  = aadharField.getText().toString();
                Toast.makeText(getApplicationContext(),"Name:"+name,Toast.LENGTH_SHORT).show();
                Log.d("Name", name);

                //final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                final String baseURL = "http://ec2-13-235-238-26.ap-south-1.compute.amazonaws.com:8080/";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("name", name);
                    //jsonBody.put("role", "User");
                    //jsonBody.put("did", "96VPwGV4SSfZaQxTFwHr2j");
                    //jsonBody.put("aadhar_no", aadhar_no);
                }
                catch(JSONException e){

                }
                final String requestBody = jsonBody.toString();

                try {
                    createWalletpost(baseURL+"createWallet", requestBody);

                    SharedPreferences myPrefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                    String wallet = myPrefs.getString("createWallet",null);
                    if(wallet!=null){
                        Log.d("Response", wallet);
                        //Toast.makeText(getApplicationContext(),wallet,Toast.LENGTH_LONG).show();
                        JSONObject Res = new JSONObject(wallet);
                        token = Res.getString("token");
                        Toast.makeText(getApplicationContext(),"Token : "+token,Toast.LENGTH_LONG).show();

                        JSONObject didBody = new JSONObject();
                        try {
                            didBody.put("name", name);
                            didBody.put("role", "User");
                            //jsonBody.put("aadhar_no", aadhar_no);
                        }
                        catch(JSONException e) { }

                        createDidpost(baseURL+"createDid",didBody.toString(),token);
                        Toast.makeText(getApplicationContext(),"DID : "+did,Toast.LENGTH_LONG).show();
                        Intent i=new Intent(SignUpActivity.this,
                                MainActivity.class);
                        SignUpActivity.this.startActivity(i);
                    }
                    else
                        Log.d("Response", "error");

                }
                catch (final Exception e){

                }
            }
        });
    }
}
