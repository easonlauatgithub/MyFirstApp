package com.easontesting.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SendMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
    }

    public void sendMessage(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, message);
        Intent i1 = new Intent();
        i1.setAction(Intent.ACTION_SEND);
        i1.setType("text/plain");
        i1.putExtra(Intent.EXTRA_TEXT, message);
        String title = getString(R.string.chooserTitle);
        Intent i2 = Intent.createChooser(i1, title);
        //startActivity(intent);
        //startActivity(i1);
        startActivity(i2);
    }
}
