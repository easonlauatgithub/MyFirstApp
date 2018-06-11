package com.easontesting.myfirstapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.Calendar;

public class PostWebserviceActivity extends AppCompatActivity {
    private String DEBUG_TAG = this.getClass().getSimpleName();
    public void funcDebug(){
        Log.w(DEBUG_TAG,Thread.currentThread().getStackTrace()[3].getMethodName());
    }
    PostWebserviceAsycn ap = null;
    String strURL ="";
    String strEmail ="";
    String strSurname = "";
    String strGivenname = "";
    String strHttpMethod = "";
    String postParams = "";
    String strRequestPropertyContentType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        funcDebug();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_webservice);
    }

    public void onClickCheckBoxHttpMethod(View v) {
        CheckBox cb = (CheckBox) findViewById(v.getId());
        Boolean isChecked = cb.isChecked();
        if(isChecked){
            strHttpMethod = cb.getText().toString();
        }else{
            strHttpMethod = "GET";
        }
    }

    public void onSelectRadioButtonForRequestPropertyContentType(View v) {
        RadioButton rb = (RadioButton) findViewById(v.getId());
        switch(v.getId()){
            case R.id.pws_rp_ct_1:
                strRequestPropertyContentType = rb.getText().toString();
                break;
            case R.id.pws_rp_ct_2:
                strRequestPropertyContentType = rb.getText().toString();
                break;
        }
    }

    public void getValueFromViewSetParameters(){
        //Set PARAMETERS
        Spinner spinnerURL=(Spinner) findViewById(R.id.pws_value_url);
        strURL = spinnerURL.getSelectedItem().toString();

        EditText vEmail = findViewById(R.id.pws_value_email);
        strEmail =  vEmail.getText().toString() + Calendar.getInstance().getTime().toString();

        EditText vSurname = findViewById(R.id.pws_value_surname);
        strSurname = vSurname.getText().toString();

        EditText vGivenname = findViewById(R.id.pws_value_givenName);
        strGivenname = vGivenname.getText().toString();

        postParams = "{" +
                "'surname':'"+strSurname+"'," +
                "'givenName': '"+strGivenname+"'," +
                "'gender': 'M'," +
                "'email': '"+strEmail+"'," +
                "'phoneDistrictNum': '852'," +
                "'phoneNumber': '23231000'," +
                "'password': 'test123%^afd'" +
                "}";
    }

    public void onClickToStart(View v) {
        funcDebug();
        try{
            if (ap == null) {
                Log.w(DEBUG_TAG, "ap == null " );
                ap = new PostWebserviceAsycn(this);
                getValueFromViewSetParameters();
            }else {
                Log.w(DEBUG_TAG, "ap != null " );
            }
            String[] arrParams = new String[]{strURL, postParams, strHttpMethod,strRequestPropertyContentType};
            ap.execute(arrParams);
            //ap.execute(strURL, postParams, strHttpMethod,strRequestPropertyContentType);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onProgressUpdate(int progressCode, int percentComplete) {
        funcDebug();
        Log.w(DEBUG_TAG,"progressCode: "+progressCode+"percentComplete: "+percentComplete);
    }

    public void showPostResponse(String result){
        funcDebug();
        TextView tv = (TextView) findViewById(R.id.pws_tv_return);
        tv.setText(result);
        ap = null;
    }
}
