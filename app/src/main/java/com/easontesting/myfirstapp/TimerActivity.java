package com.easontesting.myfirstapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    private static final String TAG2 = "TimerActivity";
    private TextView textView1;
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG2,"easontesting TimerActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        textView1 = (TextView) findViewById(R.id.view_for_easontesting);
        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("running");
        }
        runTimer();
    }
    public void runTimer(){
        Log.e(TAG2,"easontesting TimerActivity runTimer");
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable(){
            @Override
            public void run(){
            int hours = seconds/3600;
            int minutes = seconds/60%60;
            int secs = seconds%60;
            String str1 = "Seconds value: "+seconds+", "+hours+", "+minutes+", "+secs
                    +", Running status: "+running
                    +", wasRunning: "+wasRunning;
            textView1.setText(str1);
            String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
            timeView.setText(time);
            if(running){ seconds++; }
            handler.postDelayed(this, 1000);
            }
        });
    }
    @Override
    protected void onStart() {
        Log.e(TAG2,"easontesting TimerActivity onStart");
        super.onStart();
        if(wasRunning == true){
            running = true;
        }
    }
    @Override
    protected void onResume() {
        Log.e(TAG2,"easontesting TimerActivity onResume");
        super.onResume();
        if(wasRunning == true){
            running = true;
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
        Log.e(TAG2,"easontesting TimerActivity onSaveInstanceState wasRunning " + wasRunning);
    }
    @Override
    protected void onPause() {
        Log.e(TAG2,"easontesting TimerActivity onPause");
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    protected void onStop() {
        Log.e(TAG2,"easontesting TimerActivity onStop");
        super.onStop();
        wasRunning = running;
        running = false;
    }
    protected void onDestory() {
        Log.e(TAG2,"easontesting TimerActivity onDestory");
    }
    public void onClickStart(View view){
        running = true;
    }
    public void onClickStop(View view) { running = false; }
    public void onClickReset(View view) {running = false; seconds = 0; }
}
