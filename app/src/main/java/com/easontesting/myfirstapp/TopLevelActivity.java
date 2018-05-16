package com.easontesting.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        //Create Listener
        AdapterView.OnItemClickListener listener1 = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View listViewItem, int pos, long id){
                if(pos == 0){
                    Intent i = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(i);
                }else{
                    TextView item = (TextView) listViewItem;
                    CharSequence txt = "Toast - the activity is not supported yet: " + item.getText();
                    int duration = Toast.LENGTH_SHORT;
                    Toast t = Toast.makeText(TopLevelActivity.this, txt, duration);
                    t.show();
                }
            }
        };
        //Assign Listener to ListView
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(listener1);
    }
}
