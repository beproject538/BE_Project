package com.example.arijitlahiri.be_project;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.BottomNavigationView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ConnectionsFragment.OnFragmentInteractionListener, QRFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener{

    BottomNavigationView bottomNavigation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(ConnectionsFragment.newInstance("", ""));

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.page, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        //Toast.makeText(getApplicationContext(), "4th line", Toast.LENGTH_LONG).show();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            //Toast.makeText(getApplicationContext(), "Conn Tab", Toast.LENGTH_LONG).show();
                            openFragment(ConnectionsFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_dashboard:
                            //Toast.makeText(getApplicationContext(), "QR Tab", Toast.LENGTH_LONG).show();
                            //openFragment(QRFragment.newInstance("", ""));
                            MainActivity.this.startActivity(new Intent(MainActivity.this, QRScanActivity.class));
                            return true;
                        case R.id.navigation_notifications:
                            //Toast.makeText(getApplicationContext(), "Set Tab", Toast.LENGTH_LONG).show();
                            openFragment(SettingsFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };
    public void onFragmentInteraction(Uri uri) {

    }
}
