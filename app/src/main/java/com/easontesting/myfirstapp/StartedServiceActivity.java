package com.easontesting.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Delayed;

public class StartedServiceActivity extends AppCompatActivity {
    private String DEBUG_TAG = this.getClass().getSimpleName();
    public void funcDebug() {
        Log.w(DEBUG_TAG, Thread.currentThread().getStackTrace()[3].getMethodName());
    }

    Intent intent_mp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        funcDebug();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_service);
        intent_mp3 = new Intent(this, BackgroundMP3Service.class);
    }
    public void onCreateDelayedMessageService(View v) {
        Intent i = new Intent(this, DelayedMessageService.class);
        i.putExtra(DelayedMessageService.EXTRA_MESSAGE, getResources().getString(R.string.started_service_btn_response));
        startService(i);
    }
    public void onCreateBackgroundMP3(View v) {
        funcDebug();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Yoga Lin", R.raw.mp3_yogalin);
        map.put("Jay Chow", R.raw.mp3_jaychow);
        map.put("G Dragon", R.raw.mp3_aboy_gdragon);
        Spinner spinnerURL=(Spinner) findViewById(R.id.started_service_mp3_lists);
        String strMP3 = spinnerURL.getSelectedItem().toString();
        int idMP3 = map.get(strMP3);
        intent_mp3.putExtra(BackgroundMP3Service.MP3_CODE, idMP3);
        stopService(intent_mp3);
        startService(intent_mp3);
    }
    public void onStopBackgroundMP3(View v) {
        funcDebug();
        stopService(intent_mp3);
    }
}
