package com.easontesting.myfirstapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "message should be overriden";
        if(intent !=null){
//            String name = intent.getStringExtra("name");
//            int age = intent.getIntExtra("age", -1);
//            message = "Receive intent: "+intent+" name: "+name+" of age: "+age+".";
            message = "Receive intent: "+intent;
        } else{
            message = "MyBroadcastReceiver onReceive intent is null";
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        createNotification(context, intent);
    }
    public void createNotification(Context context, Intent intent){
// 建立NotificationCompat.Builder物件
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        int CUSTOM_EFFECT_ID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
// 設定小圖示、大圖示、狀態列文字、時間、內容標題、內容訊息和內容額外資訊
        builder.setSmallIcon(R.drawable.notify_small_icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Receive intent")
                .setContentText(intent.toString())
                .setContentInfo(Integer.toString(CUSTOM_EFFECT_ID));
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
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        // 使用CUSTOM_EFFECT_ID為編號發出通知
        manager.notify(CUSTOM_EFFECT_ID, notification);
    }



}
