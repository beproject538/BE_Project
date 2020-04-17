package com.example.arijitlahiri.be_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConnectionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConnectionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConnectionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    String name,token,did,verkey,metadata;

    ArrayList<Item> mList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ConnectionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConnectionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConnectionsFragment newInstance(String param1, String param2) {
        ConnectionsFragment fragment = new ConnectionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient().newBuilder().build();


    void  getConnectionApppost(String URL, String requestBody) throws IOException, InterruptedException {
        RequestBody body = RequestBody.create(JSON, requestBody);
        okhttp3.Request request = new okhttp3.Request.Builder()
                //.addHeader("Authorization",token)
                .url(URL)
                .post(body)
                .build();

        final String res = "" ;
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
                SharedPreferences myPrefs = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("getConnectionsApp",response.body().string());
                editor.commit();

                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        //return res;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_connections, container, false);
        // Inflate the layout for this fragment

        recyclerView = (RecyclerView) RootView.findViewById(R.id.Connection_list);

        final ImageView logo =  RootView.findViewById(R.id.logo);;

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences myPrefs = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        token = myPrefs.getString("token",null);
        did = myPrefs.getString("did",null);
        verkey = myPrefs.getString("verkey",null);
        metadata = myPrefs.getString("metadata",null);
        name = myPrefs.getString("name",null);

        Toast.makeText(getActivity(),"Token : "+token+"\nDID : "+did+"\n",Toast.LENGTH_LONG).show();

        Log.i("Name:",name);
        Log.i("DID:",did);
        Log.i("Token:",token);


        String url ="http://ec2-13-235-238-26.ap-south-1.compute.amazonaws.com:8080/";


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("did", did);
        }
        catch(JSONException e){

        }
        final String requestBody = jsonBody.toString();

        try {
            getConnectionApppost(url+"getConnectionsApp",requestBody);

            String response = myPrefs.getString("getConnectionsApp",null);

            JSONArray Res = new JSONArray(response);
            if(Res.length()>0) {
                for (int i = 0; i < Res.length(); i++) {
                    JSONObject j = Res.getJSONObject(i);
                    String did = j.getString("did");
                    String name = j.getString("name");
                    String status = j.getString("status");

                    mList.add(new Item(logo, did, name));
                    //textView.append("Connection ID : " + conid + "\nInviter : " + inviter + "\nInvitee : " + invitee + "\nStatus : " + status + "\n\n");
                }
            }

            // specify an adapter (see also next example)
            mAdapter = new Adapter(getActivity(),mList);
            recyclerView.setAdapter(mAdapter);
        }
        catch (final Exception e){
            e.printStackTrace();
        }

        return RootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
