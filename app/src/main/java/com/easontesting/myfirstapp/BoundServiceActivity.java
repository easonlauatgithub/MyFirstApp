package com.easontesting.myfirstapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BoundServiceActivity extends AppCompatActivity {
    private OdometerService odometer;
    private boolean bound = false;
    private double distance = 0;
    private boolean running = false;
    private boolean wasRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);
        Log.w("BoundServiceActivity", "easontesting BoundServiceActivity onCreate");
        if(savedInstanceState != null){
            distance = savedInstanceState.getDouble("distance");
        }
        watchMeters();
    }
    @Override
    protected void onStart(){
//        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
        super.onStart();
        Log.w("BoundServiceActivity", "easontesting BoundServiceActivity onStart");
        Intent i = new Intent(this, OdometerService.class);
        bindService(i, c, Context.BIND_AUTO_CREATE);
        if(wasRunning == true){
            running = true;
        }
    }
    @Override
    protected void onResume() {
//        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        super.onResume();
        if(wasRunning == true){
            running = true;
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
 //       Toast.makeText(this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putDouble("distance",distance);
    }
    @Override
    protected void onPause() {
        //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    protected void onStop(){
        //Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        super.onStop();
        Log.w("BoundServiceActivity", "easontesting BoundServiceActivity onStop");
        if(bound){
            bound = false;
            unbindService(c);
        }
        wasRunning = running;
        running = false;
    }
    private ServiceConnection c = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           // Toast.makeText(BoundServiceActivity.this, "onServiceConnected", Toast.LENGTH_SHORT).show();
            Log.w("BoundServiceActivity", "easontesting BoundServiceActivity onServiceConnected");
            OdometerService.OdometerBinder oServiceBinder = (OdometerService.OdometerBinder) service;
            odometer = oServiceBinder.getOdometer();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //Toast.makeText(BoundServiceActivity.this, "onServiceDisconnected", Toast.LENGTH_SHORT).show();
            Log.w("BoundServiceActivity", "easontesting BoundServiceActivity onServiceDisconnected");
            bound = false;
        }
    };
    private void watchMeters(){
       // Toast.makeText(this, "watchMeters", Toast.LENGTH_SHORT).show();
        Log.w("BoundServiceActivity", "easontesting BoundServiceActivity watchMeters");
        final TextView distanceView = (TextView) findViewById(R.id.bound_service_distance);
        final Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                if(odometer != null){
                    if (running == true){
                        distance = odometer.getMeters();
                    }
                }
                String distanceStr = String.format("%1$, .2f meters", distance);
                distanceView.setText(distanceStr);
                h.postDelayed(this, 1000);
            }
        });
    }
    public void onClcikOdometerStart(View v){
        //Toast.makeText(this, "onClcikOdometerStart", Toast.LENGTH_SHORT).show();
        running = true;
//        watchMeters();
    }
    public void onClcikOdometerStop(View v){
       // Toast.makeText(this, "onClcikOdometerStop", Toast.LENGTH_SHORT).show();
        running = false;
    }
    public void onClcikOdometerReset(View v){
      //  Toast.makeText(this, "onClcikOdometerReset", Toast.LENGTH_SHORT).show();
        odometer.resetMeters();
        running = true;
    }

}
