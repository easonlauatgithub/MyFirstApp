package com.easontesting.myfirstapp;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class DelayedMessageService extends IntentService {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE defined in DelayedMessageService(IntentService)";
    private Handler h1;
    //private Handler h2 = new Handler();
    public static int nid = 0;

    public DelayedMessageService() {super("DelayedMessageService");}

    @Override
    public int onStartCommand(Intent i, int flags, int startId){
        //onStartCommand execute on Main Thread, before onHandleIntent, so create a Handler h here
        h1 = new Handler();
        return super.onStartCommand(i, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            try{ wait(5000); }catch(InterruptedException e){ e.printStackTrace(); }
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);
    }
    @TargetApi(16) // for Notification n.setPriority
    private void showText(final String text){
        /* SHOW LOG */
        Log.v("DelayedMessageService", "The message is: "+text);
        /* SHOW TOAST */
        h1.post(new Runnable(){
            @Override
            public void run(){Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();}
        });
        /*
        h2.post(new Runnable(){
            @Override
            public void run(){Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();}
        });
        */
        /* SHOW NOTIFICATION */
        Intent i = new Intent(this, StartedServiceActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(i);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_NO_CREATE);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification n = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nid = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        nManager.notify(nid, n);

    }
}
