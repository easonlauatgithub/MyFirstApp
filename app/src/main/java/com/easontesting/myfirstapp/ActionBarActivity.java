package com.easontesting.myfirstapp;

import android.app.ActionBar;
import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class ActionBarActivity extends Activity {
    private final String TAG = this.getClass().getSimpleName();
    private ShareActionProvider shareActionProvider;
//public class ActionBarActivity extends AppCompatActivity {
//public class ActionBarActivity extends ActionBarActivity {
/* ActionBarActivity - work with Theme.AppCompat only and cannot use new theme e.g. Holo, Material */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);
        //infalte UP
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e(TAG, "easontesting " + TAG + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
        getMenuInflater().inflate(R.menu.menu_main, menu); //inflate - deserialize i.e. xml -> obj
        //action provider - share - start
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent1("This is example text");
        //action provider - share - end
        Boolean b1 = super.onCreateOptionsMenu(menu);
        return b1;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        switch(item.getItemId()){
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Toast.makeText(this,"action_settings",Toast.LENGTH_SHORT).show();
                return true;
            default:
                Boolean b2 = super.onOptionsItemSelected(item);
                return b2;
        }
    }

    private void setIntent1(String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }
}
