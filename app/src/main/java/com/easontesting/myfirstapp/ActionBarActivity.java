package com.easontesting.myfirstapp;

import android.app.ActionBar;
import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ActionBarActivity extends Activity {
    private final String TAG = this.getClass().getSimpleName();
    private ShareActionProvider shareActionProvider;
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;
//public class ActionBarActivity extends AppCompatActivity {
//public class ActionBarActivity extends ActionBarActivity {
/* ActionBarActivity - work with Theme.AppCompat only and cannot use new theme e.g. Holo, Material */

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View listViewItem, int pos, long id){
            Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
            selectItem(pos);

        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);
        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        drawerList = (ListView)findViewById(R.id.drawer);
        titles = getResources().getStringArray(R.array.titles);
        if(savedInstanceState == null){
            selectItem(0);
        }else{
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }
        /* Navigation Drawer */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titles);
        drawerList.setAdapter(adapter);
        /* Create Listener & Assign Listener to ListView listOptions */
            /* AdapterView.OnItemClickListener listener1 = new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> listView, View listViewItem, int pos, long id){}
            };
            ListView listOptions = (ListView) findViewById(R.id.list_options);
            listOptions.setOnItemClickListener(listener1); */
        DrawerItemClickListener listener = new DrawerItemClickListener();
        drawerList.setOnItemClickListener(listener);
        /* Hide ActionBar Item if Drawer is open */
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // trigger onPrepareOptionsMenu()
            }
            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                invalidateOptionsMenu(); // trigger onPrepareOptionsMenu()
            }
        };
        //drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.addDrawerListener(drawerToggle);
        /* infalte UP icon */
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true); // trigger onPostCreate, onCreateOptionsMenu, onPrepareOptionsMenu
        /* change title when "Back" is clicked */
        getFragmentManager().addOnBackStackChangedListener(
            new FragmentManager.OnBackStackChangedListener(){
                public void onBackStackChanged(){
                    FragmentManager manager2 = getFragmentManager();
                    Fragment fragment2 = manager2.findFragmentByTag("visible_fragment");
                    if(fragment2 instanceof NDrawerTopFragment){currentPosition=0;}
                    //if(fragment2 instanceof NDrawerPizzaFragment){currentPosition=1;}
                    if(fragment2 instanceof PizzaMaterialFragment){currentPosition=1;}
                    //if(fragment2 instanceof NDrawerPastaFragment){currentPosition=2;}
                    if(fragment2 instanceof PastaMaterialFragment){currentPosition=2;}
                    if(fragment2 instanceof NDrawerStoresFragment){currentPosition=3;}
                    setActionBarTitle(currentPosition);
                    drawerList.setItemChecked(currentPosition, true);
                }
            }
        );
    }

    private void selectItem(int pos){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        currentPosition = pos;
        //Change fragment according to clicked position
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment fragment ;
        switch(pos) {
            case 0:
                fragment = new NDrawerTopFragment();
                break;
            case 1:
                //fragment = new NDrawerPizzaFragment();
                fragment = new PizzaMaterialFragment();
                break;
            case 2:
                //fragment = new NDrawerPastaFragment();
                fragment = new PastaMaterialFragment();
                break;
            case 3:
                fragment = new NDrawerStoresFragment();
                break;
            default:
                fragment = new NDrawerTopFragment();
                break;
        }
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        //Change title according to clicked position
        setActionBarTitle(pos);
        //Close Drawer drawerList in DrawerLayout
        drawerLayout.closeDrawer(drawerList);
    }

    private void setActionBarTitle(int pos){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        String title = titles[pos];
        getActionBar().setTitle(title);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        menu.findItem(R.id.action_create_order).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
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
        if(drawerToggle.onOptionsItemSelected(item)){ return true; }
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
        Log.e(TAG, "easontesting "+TAG+": "+ Thread.currentThread().getStackTrace()[2].getMethodName() );
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }
}
