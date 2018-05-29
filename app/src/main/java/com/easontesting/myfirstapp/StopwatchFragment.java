package com.easontesting.myfirstapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchFragment extends Fragment implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    //Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
    private TextView textView1;
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("running");
            if(wasRunning == true){ running = true; }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        textView1 = layout.findViewById(R.id.view_debug);
        runTimer(layout);
        Button startButton = (Button) layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        Button stopButton = (Button) layout.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        Button resetButton = (Button) layout.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
        return layout;
    }

    public void onClick(View view){
        int clickedId = view.getId();
        switch(clickedId){
            case R.id.start_button:
                onClickStart(view);
                break;
            case R.id.stop_button:
                onClickStop(view);
                break;
            case R.id.reset_button:
                onClickReset(view);
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
    @Override
    public void onStop() {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onStop();
    }
    public void onDestory() {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
    }

    public void runTimer(View view){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        final TextView timeView = view.findViewById(R.id.time_view);
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

    public void onClickStart(View view){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        running = true;
    }
    public void onClickStop(View view) {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        running = false;
    }
    public void onClickReset(View view) {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        running = false; seconds = 0;
    }

}
