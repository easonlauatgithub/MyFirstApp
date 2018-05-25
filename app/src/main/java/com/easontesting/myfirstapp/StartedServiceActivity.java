package com.easontesting.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.concurrent.Delayed;

public class StartedServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_service);
    }
    public void onCreateDelayedMessageService(View v){
        Intent i = new Intent(this, DelayedMessageService.class);
        i.putExtra(DelayedMessageService.EXTRA_MESSAGE, getResources().getString(R.string.started_service_btn_response));
        startService(i);
    }
    public void onCreateBackgroundMP3(View v){
        Intent i = new Intent(this, BackgroundMP3Service.class);
        switch (v.getId()) {
            case R.id.started_service_btn_start_mp3:
                startService(i);
                break;
            case R.id.started_service_btn_stop_mp3:
                stopService(i);
                break;
        }
    }
}
