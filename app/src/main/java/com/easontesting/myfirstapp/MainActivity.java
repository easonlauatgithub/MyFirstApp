package com.easontesting.myfirstapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.easontesting.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /* Called when the user taps the Send button */
    public void sendMessage(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, message);
        Intent i1 = new Intent(Intent.ACTION_SEND);
        i1.setType("text/plain");
        i1.putExtra(Intent.EXTRA_TEXT, message);
        String title = getString(R.string.chooserTitle);
        Intent i2 = Intent.createChooser(i1, title);
        //startActivity(intent);
        //startActivity(i1);
        startActivity(i2);
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

}
