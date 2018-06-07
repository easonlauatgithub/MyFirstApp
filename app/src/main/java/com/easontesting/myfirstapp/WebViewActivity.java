package com.easontesting.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebViewActivity extends AppCompatActivity {
    private final String TAG_DEBUG = this.getClass().getSimpleName();
    public WebView wv;
    private boolean isSafeBrowsingInitialized = false;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        //Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+new Throwable().getStackTrace()[0].getMethodName());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        wv = (WebView) findViewById(R.id.webview);

        WebAppInterface wai = new WebAppInterface(this);
        wv.addJavascriptInterface(wai, "Interface1");

        WebSettings ws = wv.getSettings();
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        ws.setJavaScriptEnabled(true);

        wv.setWebViewClient(wvc);
        wv.setWebChromeClient(wcc);
        wv.loadUrl("http://192.168.1.57/index.php");
        //wv.loadUrl("https://www.youtube.com/watch?v=JfnQ8qtcDyQ");
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        if ((keyCode == KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClickGoToUrl(View v){
        Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        EditText et = findViewById(R.id.wv_edittext);
        String url = et.getText().toString();
        String http = "http://";
        String https = "https://";
        if ( !url.toLowerCase().contains(http.toLowerCase()) ) {
            if ( !url.toLowerCase().contains(https.toLowerCase()) ) {
                url = https+url;
            }
        }
        wv.loadUrl(url);
    }

    WebViewClient wvc = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
            if (Uri.parse(url).getHost().equals("hk.yahoo.com")) {
                return false;
            }else if (Uri.parse(url).getHost().equals("hk.mobi.yahoo.com")) {
                return false;
            }else{
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        }
    };

    WebChromeClient wcc = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );

            if ((title != null) && (title.trim().length() != 0)) {
                setTitle(title);
            }
        }
        /*
        public boolean onConsoleMessage(ConsoleMessage cm) {
            Log.d("MyApplication", cm.message() + " -- From line "
                    + cm.lineNumber() + " of "
                    + cm.sourceId() );
            return true;
        }
        */
    };

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

}
