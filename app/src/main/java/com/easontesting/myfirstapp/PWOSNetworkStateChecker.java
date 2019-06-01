package com.easontesting.myfirstapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PWOSNetworkStateChecker extends BroadcastReceiver {
    private final String tag = "NetworkStateChecker";
    private Context context;
    private PWOSDatabaseHelper db;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(tag, "easontesting "+tag+" onReceive 1");
        this.context = context;
        db = new PWOSDatabaseHelper(context);
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        Log.w(tag, "easontesting "+tag+" onReceive 2");
        if (activeNetwork != null) {
            Log.w(tag, "easontesting "+tag+" onReceive 3");
            //if connected to wifi or mobile
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Log.w(tag, "easontesting "+tag+" onReceive 4");
                //getting all the unsynced names
                Cursor cursor = db.getUnsyncedNames();
                if (cursor.moveToFirst()) {
                    do {
                        Log.w(tag, "easontesting "+tag+" onReceive 5");
                        saveName(
                                cursor.getInt(cursor.getColumnIndex(PWOSDatabaseHelper.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(PWOSDatabaseHelper.COLUMN_NAME))
                        );
                    } while (cursor.moveToNext());
                }
            }else{
                Log.w(tag, "easontesting "+tag+" onReceive 6");
            }
        }
        Log.w(tag, "easontesting "+tag+" onReceive 7");
    }
    /*
    * method taking two arguments
    * name that is to be saved and id of the name from SQLite
    * if the name is successfully sent
    * we will update the status as synced in SQLite
    * */
    private void saveName(final int id, final String name) {
        Log.w(tag, "easontesting "+tag+" saveName 1");
        Log.w(tag, "easontesting "+tag+" id:"+id);
        Log.w(tag, "easontesting "+tag+" name:"+name);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PWOSActivity.URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w(tag, "easontesting "+tag+" onResponse 1");
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //updating the status in sqlite
                                db.updateNameStatus(id, PWOSActivity.NAME_SYNCED_WITH_SERVER);

                                //sending the broadcast to refresh the list
                                context.sendBroadcast(new Intent(PWOSActivity.DATA_SAVED_BROADCAST));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(tag, "easontesting "+tag+" onErrorResponse 1");
                        Log.w(tag, "easontesting "+tag+" VolleyError:"+error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.w(tag, "easontesting "+tag+" getParams 1");
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };

        PWOSVolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

}