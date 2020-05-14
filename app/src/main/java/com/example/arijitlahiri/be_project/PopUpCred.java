package com.example.arijitlahiri.be_project;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class PopUpCred {
    TextView cred_ConName,Cred_text,credentials_text;
    Button close;
    ImageView cred_logo;
    //PopupWindow display method
    ArrayList<CredItem> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public void showPopupWindow(final View view, final String credentials) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.credential_popup, null);

        cred_ConName = popupView.findViewById(R.id.cred_ConName);
        //credentials_text = popupView.findViewById(R.id.credentials_text);
        close = popupView.findViewById(R.id.close_button);
        cred_logo = popupView.findViewById(R.id.cred_logo);

        recyclerView = (RecyclerView) popupView.findViewById(R.id.Credential_list);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(popupView.getContext());
        recyclerView.setLayoutManager(layoutManager);


        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);




        try {
            JSONObject Res = new JSONObject(credentials);
            Iterator<String> iter = Res.keys();
            while (iter.hasNext()) {
                String cred_key = iter.next();
                try {
                    String cred_value = Res.getString(cred_key);
                    mList.add(new CredItem(cred_key,cred_value));
                } catch (JSONException e) { }
            }

            mAdapter = new CredAdapter(popupView.getContext(), mList);
            recyclerView.setAdapter(mAdapter);
        }
        catch (final Exception e){
            e.printStackTrace();
        }
        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
