package com.easontesting.myfirstapp;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
public class PostWebserviceAsycn extends AsyncTask<String, Integer, String> {
    private String DEBUG_TAG = this.getClass().getSimpleName();
    public void funcDebug(){
        Log.w(DEBUG_TAG,Thread.currentThread().getStackTrace()[3].getMethodName());
    }
    private PostWebserviceActivity pwsMainActivity;
    PostWebserviceAsycn(PostWebserviceActivity ma) {
        pwsMainActivity = ma;
        funcDebug();
    }
    @Override
    protected void onPreExecute() {
        funcDebug();
    }
    @Override
    protected String doInBackground(String... params) {
        funcDebug();
        String result = "default value in doInBackground";
        if ( params != null && params.length > 0) {
            String urlString = params[0];
            String postParamString = params[1];
            String httpMethod = params[2];
            String strRPCT = params[3];
            try {
                URL url = new URL(urlString);
                result = postRequest(url, postParamString, httpMethod, strRPCT);
            } catch(Exception e) {
                result = e.getMessage();
            }
        }
        return result;
    }
    private String postRequest(URL url, String postParamString, String httpMethod, String strRPCT) throws IOException {
        funcDebug();
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);            // Timeout for connection.connect() arbitrarily set to 3000ms.
            connection.setReadTimeout(3000);            // Timeout for reading InputStream arbitrarily set to 3000ms.
            //connection.setFixedLengthStreamingMode(postParameters.getBytes().length);
            connection.setRequestProperty("Content-Type",strRPCT);
            //connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            //connection.setRequestProperty("Content-Type","application/json");
            connection.setDoInput(true);
            Log.w(DEBUG_TAG, "POST DATA - START, httpMethod: "+httpMethod);
            connection.setRequestMethod(httpMethod);
            Log.w(DEBUG_TAG, "POST DATA - 1");
            connection.setDoOutput(true);
            Log.w(DEBUG_TAG, "POST DATA - 2");
            OutputStream out = connection.getOutputStream();
            Log.w(DEBUG_TAG, "POST DATA - 3");
            writeStream(out, postParamString);
            Log.w(DEBUG_TAG, "POST DATA - END" );
            connection.connect();
            Log.w(DEBUG_TAG, "connection.getRequestMethod(): "+ connection.getRequestMethod());
            Log.w(DEBUG_TAG, "connection.getResponseCode(): "+ connection.getResponseCode());
            Log.w(DEBUG_TAG, "connection.getResponseMessage(): "+ connection.getResponseMessage());
            //Log.w(DEBUG_TAG, "connection.getHeaderFields()");
            //Map m = connection.getHeaderFields();
            //for (Object key : m.keySet()) {
            //    Log.w(DEBUG_TAG, key+ " : " + m.get(key));
            //}
            //publishProgress(2, 0);
            // Retrieve the response body as an InputStream.
            Log.w(DEBUG_TAG, "connection.getInputStream() - START, stream: "+stream );
            stream = connection.getInputStream();
            //stream = connection.getErrorStream();
            Log.w(DEBUG_TAG, "connection.getInputStream() - END, stream: "+stream );
            if (stream != null) {
                // Converts Stream to String with max length of 500.
                //result = readStream(stream, 500);
                result = readStream(stream, 10);
            }

        } catch(Exception e){
            result = "Exception in postRequest: "+e.getMessage();
        }finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                Log.w(DEBUG_TAG, "stream.close()" );
                stream.close();
            }
            if (connection != null) {
                Log.w(DEBUG_TAG, "connection.disconnect()" );
                connection.disconnect();
            }
        }
        return result;
    }
    @Override
    protected void onPostExecute(String result) {
        funcDebug();
        if (result != null && pwsMainActivity != null) {
            pwsMainActivity.showPostResponse(result);
            pwsMainActivity.ap = null;
        }
    }
    public void writeStream(OutputStream out, String postParameters){
        funcDebug();
        Log.w(DEBUG_TAG, "postParameters: "+postParameters );
        BufferedOutputStream bos = new BufferedOutputStream(out);
        try{
            OutputStreamWriter osw = new OutputStreamWriter(bos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(postParameters);
            bw.flush();
            bw.close();
            bos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public String readStream(InputStream stream, int maxReadSize) throws IOException, UnsupportedEncodingException {
        funcDebug();
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        /*
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        */
        while (((readSize = reader.read(rawBuffer)) != -1) ) {
            buffer.append(rawBuffer, 0, readSize);
        }

        return buffer.toString();
    }
}