package com.easontesting.myfirstapp;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;

public class BackgroundMP3Service extends IntentService {
    public BackgroundMP3Service() {
        super("BackgroundMP3Service");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mp3_aboy_gdragon);
        mediaPlayer.start();
    }
}
