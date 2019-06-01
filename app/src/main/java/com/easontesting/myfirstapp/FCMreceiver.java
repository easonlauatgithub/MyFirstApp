package com.easontesting.myfirstapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMreceiver extends FirebaseMessagingService {
    public FCMreceiver() {}
    private static final String TAG = "FCM";
    @Override
    public void onMessageReceived(RemoteMessage rm){
        if(rm.getData().size()>0){
            Log.d(TAG, "rm.getData:"+rm.getData());
        }
        if(rm.getNotification()!=null){
            Log.d(TAG, "rm.getNotification:"+rm.getNotification());
            Log.d(TAG, "rm.getNotification.getBody:"+rm.getNotification().getBody());
        }
        String msg = rm.getNotification().getBody();
        sendNotification(msg);
    }
    private void sendNotification(String messageBody){
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.action_new_order)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(0,nBuilder.build());
    }

}
