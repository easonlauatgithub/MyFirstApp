package com.easontesting.myfirstapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//public class PWOSActivity extends AppCompatActivity implements View.OnClickListener{
public class PWOSActivity extends AppCompatActivity{
    private final String tag = "PWOSActivity";
    /* url to webservice */
    public static final String URL_SAVE_NAME = "http://deberra.000webhostapp.com/saveName.php";
    //public static final String URL_SAVE_NAME = "https://easonwebservice.000webhostapp.com/Android/saveData.php";
    private PWOSDatabaseHelper db;
    private Button buttonShowAll;
    private Button buttonSave;
    private EditText editTextName;
    private ListView listViewNames;
    private List<PWOSName> names;
    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;

    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

    //adapterobject for list view
    private PWOSNameAdapter nameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwos);
        Log.w(tag, "easontesting "+tag+" onCreate 1");
        //initializing views and objects
        db = new PWOSDatabaseHelper(this);
        names = new ArrayList<>();
        buttonShowAll = (Button) findViewById(R.id.buttonShowAll);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextName = (EditText) findViewById(R.id.editTextName);
        listViewNames = (ListView) findViewById(R.id.listViewNames);
        //adding click listener to button
        //buttonSave.setOnClickListener(this);
        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveNameToServer();
            }
        });
        buttonShowAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(PWOSActivity.this,"go to website to see all data",Toast.LENGTH_LONG).show();
                Uri uriUrl = Uri.parse("http://deberra.000webhostapp.com/showName.php");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
        Log.w(tag, "easontesting "+tag+" onCreate 2");
        //calling the method to load all the stored names
        loadNames();
        Log.w(tag, "easontesting "+tag+" onCreate 3");
        //the broadcast receiver to update sync status
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.w(tag, "easontesting "+tag+" onCreate BroadcastReceiver 1");
                //loading the names again
                loadNames();
            }
        };

        Log.w(tag, "easontesting "+tag+" onCreate 4");
        //registering the broadcast receiver to update sync status
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
        Log.w(tag, "easontesting "+tag+" onCreate 5");
        //register NetworkStateChecker.java, similar to registered in AndroidManifest.xml
        registerReceiver(new PWOSNetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        Log.w(tag, "easontesting "+tag+" onCreate 6");
    }
    /* this method will load the names from the database with updated sync status*/
    private void loadNames() {
        Log.w(tag, "easontesting "+tag+" loadNames 1");
        names.clear();
        Cursor cursor = db.getNames();
        Log.w(tag, "easontesting "+tag+" loadNames 2");
        if (cursor.moveToFirst()) {
            Log.w(tag, "easontesting "+tag+" loadNames 3");
            do {
                Log.w(tag, "easontesting "+tag+" loadNames 4");
                PWOSName name = new PWOSName(
                        cursor.getString(cursor.getColumnIndex(PWOSDatabaseHelper.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(PWOSDatabaseHelper.COLUMN_STATUS))
                );
                names.add(name);
            } while (cursor.moveToNext());
            Log.w(tag, "easontesting "+tag+" loadNames 8");
        }
        Log.w(tag, "easontesting "+tag+" loadNames 9");
        nameAdapter = new PWOSNameAdapter(this, R.layout.pwosnames, names);
        Log.w(tag, "easontesting "+tag+" loadNames 10");
        listViewNames.setAdapter(nameAdapter);
        Log.w(tag, "easontesting "+tag+" loadNames 11");
    }
    /* this method will simply refresh the list */
    private void refreshList() {
        Log.w(tag, "easontesting "+tag+" refreshList 1");
        nameAdapter.notifyDataSetChanged();
    }
    /* this method is saving the name to ther server */
    private void saveNameToServer() {
        Log.w(tag, "easontesting "+tag+" saveNameToServer 1");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Name [saveNameToServer] ...");
        progressDialog.show();
        final String name = editTextName.getText().toString().trim();
        Log.w(tag, "easontesting "+tag+" saveNameToServer 2");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.w(tag, "easontesting "+tag+" saveNameToServer 6");
                            if (!obj.getBoolean("error")) {
                                //if there is a success
                                //storing the name to sqlite with status synced
                                Log.w(tag, "easontesting "+tag+" saveNameToServer 7");
                                saveNameToLocalStorage(name, NAME_SYNCED_WITH_SERVER);
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                Log.w(tag, "easontesting "+tag+" saveNameToServer 8");
                                saveNameToLocalStorage(name, NAME_NOT_SYNCED_WITH_SERVER);
                            }
                        } catch (JSONException e) {
                            Log.w(tag, "easontesting "+tag+" saveNameToServer 9");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(tag, "easontesting "+tag+" saveNameToServer 10");
                        Log.w(tag, "easontesting "+tag+" VolleyError:"+error);
                        progressDialog.dismiss();
                        Log.w(tag, "easontesting "+tag+" saveNameToServer 11");
                        //on error storing the name to sqlite with status unsynced
                        saveNameToLocalStorage(name, NAME_NOT_SYNCED_WITH_SERVER);
                        Log.w(tag, "easontesting "+tag+" saveNameToServer 12");
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };
        Log.w(tag, "easontesting "+tag+" saveNameToServer 13");
        PWOSVolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        Log.w(tag, "easontesting "+tag+" saveNameToServer 14");
    }
    //saving the name to local storage
    private void saveNameToLocalStorage(String name, int status) {
        Log.w(tag, "easontesting "+tag+" saveNameToLocalStorage 1");
        editTextName.setText("");
        Log.w(tag, "easontesting "+tag+" saveNameToLocalStorage 2");
        db.addName(name, status);
        Log.w(tag, "easontesting "+tag+" saveNameToLocalStorage 3");
        PWOSName n = new PWOSName(name, status);
        names.add(n);
        refreshList();
        Log.w(tag, "easontesting "+tag+" saveNameToLocalStorage 4");
    }

    /*
    @Override
    public void onClick(View view) {
        Log.w(tag, "easontesting "+tag+" onClick 1");
        saveNameToServer();
        Log.w(tag, "easontesting "+tag+" onClick 2");
    }
    */

}
