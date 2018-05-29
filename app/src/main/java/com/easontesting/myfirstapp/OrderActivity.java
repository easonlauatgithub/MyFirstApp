package com.easontesting.myfirstapp;

//import android.support.v7.app.AppCompatActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

//public class OrderActivity extends AppCompatActivity {
public class OrderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
