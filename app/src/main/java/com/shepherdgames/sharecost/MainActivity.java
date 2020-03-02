package com.shepherdgames.sharecost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean connected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        checkInternet();

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("LoginStatus", MODE_PRIVATE);
        String status = prefs.getString("status", "false");
        if (connected == true){
            if (status.equalsIgnoreCase("true")) {
                Intent Dashboard = new Intent(MainActivity.this, Dashboard.class);
                startActivity(Dashboard);
                finish();
            } else {
                addFragment(new Login_With_Phone(), false, "Login_With_Phone");

            }
        }
        else
        {
            Toast.makeText(MainActivity.this,"Please check internet connectivity",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

    }


    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.frame_container, fragment, tag);
        ft.commitAllowingStateLoss();

    }


}