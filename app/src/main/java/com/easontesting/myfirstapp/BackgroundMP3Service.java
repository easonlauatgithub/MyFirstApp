package com.easontesting.myfirstapp;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

public class BackgroundMP3Service extends IntentService {
    private String DEBUG_TAG = this.getClass().getSimpleName();
    public void funcDebug(){
        Log.w(DEBUG_TAG,Thread.currentThread().getStackTrace()[3].getMethodName());
    }

    public static final String MP3_CODE = "MP3 Code";
    public static final String ACTION_CODE = "ACTION Code";
    MediaPlayer mediaPlayer;
    public BackgroundMP3Service() {
        super("BackgroundMP3Service");
    }
    protected void onHandleIntent(Intent intent) {    }
    public int onStartCommand (Intent intent, int flags,int startId){
        funcDebug();
        int mp3Code = intent.getIntExtra(MP3_CODE, -1);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), mp3Code);
        mediaPlayer.start();
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy(){
        funcDebug();
        Log.w(DEBUG_TAG,"mediaPlayer: "+mediaPlayer);
        mediaPlayer.stop();
        super.onDestroy();
    }
}
