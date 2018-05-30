package com.easontesting.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
//import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity { //res/values/styles
//public class MainActivity extends Activity {
    public static final String EXTRA_MESSAGE = "com.easontesting.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickToSendMessage(View view){
        Intent intent = new Intent(this, SendMessageActivity.class);
        startActivity(intent);
    }
    public void onClickToTimer(View view){
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }
    public void onClickToCarParkTimer(View view){
        Intent intent = new Intent(this, CarParkTimer.class);
        startActivity(intent);
    }
    public void onClickToLayoutActivity(View view){
        Intent i = new Intent(this, LayoutActivity.class);
        startActivity(i);
    }
    public void onClickToListView(View view){
        Intent i = new Intent(this, TopLevelActivity.class);
        startActivity(i);
    }
    public void onClickToFragment(View view){
        Intent i = new Intent(this, FragmentActivity.class);
        startActivity(i);
    }
    public void onClickToBroadcastReceiverTest(View view){
        Intent i = new Intent(this, BroadcastReceiverTestActivity.class);
        startActivity(i);
    }
    public void onClickToStartedService(View view){
        Intent i = new Intent(this, StartedServiceActivity.class);
        startActivity(i);
    }
    public void onClickToBoundService(View view){
        Intent i = new Intent(this, BoundServiceActivity.class);
        startActivity(i);
    }
    public void onClickToActionBar(View view){
        Intent i = new Intent(this, ActionBarActivity.class);
        startActivity(i);
    }
    public void onClickToMatchingGame(View view){
        Intent i = new Intent(this, MatchingGameActivity.class);
        startActivity(i);
    }
}
