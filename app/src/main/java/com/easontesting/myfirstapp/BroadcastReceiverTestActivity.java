package com.easontesting.myfirstapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.net.wifi.WifiManager;
import android.hardware.Camera;

import java.util.Date;

public class BroadcastReceiverTestActivity extends AppCompatActivity {
//    public static final String BROADCAST_ACTION = "Action1";
    public String nameValue = "Eason Lau";
    public int ageValue = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver_test);
    }

    public void createBroadcast(View v){
        String BROADCAST_ACTION = "Action_1";
        //Intent intent = new Intent(BROADCAST_ACTION);
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra("name", nameValue);
        intent.putExtra("age", ageValue);
        sendBroadcast(intent);
        //<intent-filter><action android:name="Action_1" /></intent-filter>
        Toast.makeText(BroadcastReceiverTestActivity.this, "Broadcast["+BROADCAST_ACTION+"] is created", Toast.LENGTH_LONG).show();
    }

    // BroadcastReceiver ON during application on
    MyBroadcastReceiver receiver = new MyBroadcastReceiver();
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter if1 = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(receiver, if1);
        /* <intent-filter><action android:name="android.intent.action.AIRPLANE_MODE" /></intent-filter> replaced */
        //IntentFilter if2 = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        //registerReceiver(receiver, if2);
    }
    @Override
    protected void onPause() {
        Toast.makeText(BroadcastReceiverTestActivity.this, "BroadcastReceiverTestActivity onPause", Toast.LENGTH_SHORT).show();
        unregisterReceiver(receiver);
        super.onPause();
    }

    public void createNotification1(View v){
// 建立NotificationCompat.Builder物件
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        int CUSTOM_EFFECT_ID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
// 設定小圖示、大圖示、狀態列文字、時間、內容標題、內容訊息和內容額外資訊
        builder.setSmallIcon(R.drawable.notify_small_icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Basic Notification")
                .setContentText("Demo for basic notification control.")
                .setContentInfo("[setContentInfo]"+Integer.toString(CUSTOM_EFFECT_ID));
// 準備設定通知效果用的變數
        int defaults = 0;
// 加入震動效果
            defaults |= Notification.DEFAULT_VIBRATE;
// 加入音效效果
            defaults |= Notification.DEFAULT_SOUND;
// 加入閃燈效果
            defaults |= Notification.DEFAULT_LIGHTS;
// 設定通知效果
        builder.setDefaults(defaults);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        // 使用CUSTOM_EFFECT_ID為編號發出通知
        manager.notify(CUSTOM_EFFECT_ID, notification);
    }

    public void createNotification2(View v){
               // 建立NotificationCompat.Builder物件
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // 建立大圖示需要的Bitmap物件
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.notify_big_icon);
        int CUSTOM_EFFECT_ID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
// 設定小圖示、大圖示、狀態列文字、時間和內容標題
        builder.setSmallIcon(R.drawable.notify_small_icon)
                .setLargeIcon(largeIcon)
                .setTicker("CUSTOM EFFECT")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("[setContentTitle] Custom effect")
                .setContentText("[setContentText] vibrate_effect, sound_effect, light_effect")
                .setContentInfo("[setContentInfo]"+Integer.toString(CUSTOM_EFFECT_ID));
// 建立震動效果，陣列中元素依序為停止、震動的時間，單位是毫秒
        long[] vibrate_effect = {1000, 2000, 1000, 1000, 1000, 500, 1000, 300, 1000, 100};
        builder.setVibrate(vibrate_effect);
// 建立音效效果，放在res/raw下的音效檔
        Uri sound_effect = Uri.parse("android.resource://" + getPackageName() + "/raw/zeta");
// 設定音效效果
        builder.setSound(sound_effect);
// 設定閃燈效果，參數依序為顏色、打開與關閉時間，單位是毫秒
        builder.setLights(Color.GREEN, 1000, 1000);
// 建立通知物件
        Notification notification = builder.build();
// 取得NotificationManager物件
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// 使用CUSTOM_EFFECT_ID為編號發出通知
        manager.notify(CUSTOM_EFFECT_ID, notification);
    }

    public void createNotification3(View v){
// 建立NotificationCompat.Builder物件
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        int CUSTOM_EFFECT_ID = (int) ( new Date().getTime() );
// 設定小圖示、大圖示、狀態列文字、時間、內容標題、內容訊息和內容額外資訊
        builder.setSmallIcon(R.drawable.notify_small_icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("new Date().getTime()")
                .setContentText(Integer.toString(CUSTOM_EFFECT_ID))
                .setContentInfo("Step 1");
// 準備設定通知效果用的變數
        int defaults = 0;
// 加入震動效果
        defaults |= Notification.DEFAULT_VIBRATE;
// 加入音效效果
        defaults |= Notification.DEFAULT_SOUND;
// 加入閃燈效果
        defaults |= Notification.DEFAULT_LIGHTS;
// 設定通知效果
        builder.setDefaults(defaults);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        // 使用CUSTOM_EFFECT_ID為編號發出通知
        manager.notify(CUSTOM_EFFECT_ID, notification);
    }

    public void createNotification4(View v){
// 建立NotificationCompat.Builder物件
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        int CUSTOM_EFFECT_ID = (int) ((new Date().getTime() / 1000L) );
// 設定小圖示、大圖示、狀態列文字、時間、內容標題、內容訊息和內容額外資訊
        builder.setSmallIcon(R.drawable.notify_small_icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("(new Date().getTime() / 1000L")
                .setContentText(Integer.toString(CUSTOM_EFFECT_ID))
                .setContentInfo("Step 2");
// 準備設定通知效果用的變數
        int defaults = 0;
// 加入震動效果
        defaults |= Notification.DEFAULT_VIBRATE;
// 加入音效效果
        defaults |= Notification.DEFAULT_SOUND;
// 加入閃燈效果
        defaults |= Notification.DEFAULT_LIGHTS;
// 設定通知效果
        builder.setDefaults(defaults);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        // 使用CUSTOM_EFFECT_ID為編號發出通知
        manager.notify(CUSTOM_EFFECT_ID, notification);
    }

    public void createNotification5(View v){
// 建立NotificationCompat.Builder物件
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        int CUSTOM_EFFECT_ID = (int) (Integer.MAX_VALUE);
// 設定小圖示、大圖示、狀態列文字、時間、內容標題、內容訊息和內容額外資訊
        builder.setSmallIcon(R.drawable.notify_small_icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Integer.MAX_VALUE")
                .setContentText(Integer.toString(CUSTOM_EFFECT_ID))
                .setContentInfo("Step 3");
// 準備設定通知效果用的變數
        int defaults = 0;
// 加入震動效果
        defaults |= Notification.DEFAULT_VIBRATE;
// 加入音效效果
        defaults |= Notification.DEFAULT_SOUND;
// 加入閃燈效果
        defaults |= Notification.DEFAULT_LIGHTS;
// 設定通知效果
        builder.setDefaults(defaults);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        // 使用CUSTOM_EFFECT_ID為編號發出通知
        manager.notify(CUSTOM_EFFECT_ID, notification);
    }


}
