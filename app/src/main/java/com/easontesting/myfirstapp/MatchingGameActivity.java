package com.easontesting.myfirstapp;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.widget.ImageView;

@TargetApi(16)
public class MatchingGameActivity extends AppCompatActivity {
    private final String TAG_DEBUG = this.getClass().getSimpleName();
    private int[] arrImgId;
    private int[] arrBtnId;
    private int firstClickedBtnId = 0;
    private int notMatchedPairs;
    private ArrayList<Integer> arrListMatchedPairs;
    private boolean isClickable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_game);
        Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        //Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+new Throwable().getStackTrace()[0].getMethodName());

        String[] arrImageFileNameFromXMLSource = this.getResources().getStringArray(R.array.image_file_names); //get from strings.xml
        notMatchedPairs = arrImageFileNameFromXMLSource.length;
        arrListMatchedPairs = new ArrayList<>(arrImageFileNameFromXMLSource.length * 2 );
        arrImgId = funcGenImageIdArray(arrImageFileNameFromXMLSource);//arrImgId - an array storing Image id in random
        arrBtnId = funcGetImageButtonIdArray();//arrBtnId - an array storing Image Button Id
        funcSetImage(arrBtnId, arrImgId);
        funcSetLayout();
    }

    public void funcSetLayout(){
        final GridLayout layout = findViewById(R.id.mg_gridLayout);
        ViewTreeObserver o = layout.getViewTreeObserver();
        o.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        int height_layout = layout.getHeight();
                        int width_layout = layout.getWidth();
                        int numOfChild = layout.getChildCount();
                        for(int i=0; i<numOfChild; i++){
                            View v = layout.getChildAt(i);
                            v.setMinimumWidth(width_layout/4);
                            v.setMinimumHeight(height_layout/2);
                        }
                    }
                });
    }

    public void onCLickPicture(View v) {
//            Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        int _clickedBtnId = v.getId();
        Boolean isMatchedPairs = arrListMatchedPairs.contains(_clickedBtnId);

        if(isClickable){
            if(isMatchedPairs){
                getAlertDialog("Warning!","Button 己經成功配對, 請按其他......").show();
            }else{
                showImage(_clickedBtnId);
                if(firstClickedBtnId == 0){ //if it is first clicked
                    firstClickedBtnId = _clickedBtnId;
                } else{ // if it is second clicked
                    funcResponseToTheTwoClicks(firstClickedBtnId, _clickedBtnId, arrBtnId, arrImgId); //match - keep showing, not match - hide after 5s
                }
            }
        }else{
            Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName()+" not clickable yet" );
        }
    }

    private void funcResponseToTheTwoClicks(int id1stBtnClicked, int id2ndBtnClicked, int[] arrBtnId, int[] arrImgId){
        //Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        final int ID_1 = id1stBtnClicked;
        final int ID_2 = id2ndBtnClicked;
        boolean _isMatched = funcCompareImage(ID_1, ID_2, arrBtnId, arrImgId); //check if images match
        if(_isMatched){
            notMatchedPairs--;
            arrListMatchedPairs.add(ID_1);
            arrListMatchedPairs.add(ID_2);
            if(notMatchedPairs == 0){
                getAlertDialog("All Matched!!!","全部"+arrListMatchedPairs.size()/2+"對圖案己配對成功......").show();
            } else{
                getAlertDialog("Matched!","尚餘"+notMatchedPairs+"對圖案, 加油......").show();
            }
        }else{
            isClickable = false;
            //Count Down and Hide image if not match
            Toast.makeText(MatchingGameActivity.this, "未能配對圖案......", Toast.LENGTH_SHORT).show();
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                    //Log.w(TAG_DEBUG, "funcCountDownTimer seconds remaining: " + millisUntilFinished / 1000 );
                }
                public void onFinish() {
                    hideImage(ID_1);
                    hideImage(ID_2);
                    isClickable = true;
                }
            }.start();
            //Can't use Thread.sleep() to count down 5s
            //funcThreadSleep(5000);
            //funcGoToNewThread(id1, id2);
        }
        firstClickedBtnId = 0;
    }

    private Boolean funcCompareImage(int btnId1, int btnId2, int[] arrBtnId, int[] arrImgId){
        //Get the Image Id according to Btn Id
//        Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        int _posOfBtnId1, _posOfBtnId2, _imgId1, _imgId2;
        Boolean isSamePicture = false;
        ArrayList<Integer> _arrBtnIdList = new ArrayList<>();
        for(int btnId: arrBtnId){_arrBtnIdList.add(btnId);}
        if(btnId1 == btnId2){
            getAlertDialog("Warning!","你按下相同的Button, 請按其他......").show();
        }else{
            _posOfBtnId1 = _arrBtnIdList.indexOf(btnId1);
            _posOfBtnId2 = _arrBtnIdList.indexOf(btnId2);
            _imgId1 = arrImgId[_posOfBtnId1];
            _imgId2 = arrImgId[_posOfBtnId2];
            isSamePicture = (_imgId2 == _imgId1);
        }
        return isSamePicture;
    }

    private void hideImage(int id){
        ImageButton imageButton = (ImageButton) findViewById(id);
        //Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName()+", imageButton: "+imageButton.getId() );
        imageButton.setImageAlpha(0);
    }

    private void showImage(int id){
        ImageButton imageButton = (ImageButton) findViewById(id);
        //Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName()+", imageButton: "+imageButton.getId() );
        imageButton.setImageAlpha(255);
    }

    private int[] funcGenImageIdArray(String[] arrSource){
        //Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        int _num = (arrSource.length)*2; //8
        int[] _arrImgId = new int[_num];
        ArrayList<String> _listOfImageName = new ArrayList<String>();
        // Paired
        _listOfImageName.addAll(Arrays.asList(arrSource));
        _listOfImageName.addAll(Arrays.asList(arrSource));
        // Shuffled
        Collections.shuffle(_listOfImageName);
        String[] _arrImageNameJoinedShuffled = _listOfImageName.toArray(new String[0]);
        // Get ImageId by Image file name
        for(int i =0; i<_num; i++){
            _arrImgId[i] = this.getResources().getIdentifier(_arrImageNameJoinedShuffled[i], "drawable", getPackageName());
        }
        return _arrImgId;
    }

    private int[] funcGetImageButtonIdArray(){
        //Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        int[] _arr = new int[]{R.id.mg_pic_0,R.id.mg_pic_1,R.id.mg_pic_2,R.id.mg_pic_3,R.id.mg_pic_4,R.id.mg_pic_5,R.id.mg_pic_6,R.id.mg_pic_7};
        return _arr;
    }

    private void funcSetImage(int[] arrBtnId, int[] arrImgFileId){
        //Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        int lenArrBtnId = arrBtnId.length;
        int lenArrImgFileId = arrImgFileId.length;
        if(lenArrBtnId == lenArrImgFileId){
            for(int i=0; i<lenArrBtnId; i++){
                ImageButton _imgBtn = findViewById(arrBtnId[i]);
                _imgBtn.setImageResource(arrImgFileId[i]);
                _imgBtn.setScaleType(ImageView.ScaleType.FIT_XY);
                _imgBtn.setImageAlpha(0);
            }
        }

    }

    private AlertDialog getAlertDialog(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        return builder.create();
    }

    private void funcGoToNewThread(int id1, int id2){
        Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        Thread t1 = new Thread(){
            public void run() {
                Log.w(TAG_DEBUG, "funcNewThread t1 START: "+ Thread.currentThread());
                Thread.currentThread().setName("t1");
                try{
                    Thread.sleep(5000);
                } catch(Exception e){
                    e.printStackTrace();
                }
                Log.w(TAG_DEBUG, "funcNewThread t1 END: "+ Thread.currentThread());
            }
        };
        Log.w(TAG_DEBUG, "funcNewThread 1 .currentThread() : "+ Thread.currentThread());
        t1.start();
        Log.w(TAG_DEBUG, "funcNewThread 2 .currentThread() : "+ Thread.currentThread());
        try{
            Log.w(TAG_DEBUG, "funcNewThread 3 .currentThread() : "+ Thread.currentThread());
            t1.join();
            Log.w(TAG_DEBUG, "funcNewThread 4 .currentThread() : "+ Thread.currentThread());
        } catch(Exception e){
            e.printStackTrace();
        }
        Log.w(TAG_DEBUG, "funcNewThread 5 .currentThread() : "+ Thread.currentThread());
        //hideImage(id1);
        //hideImage(id2);
    }

    private void funcThreadSleep(int t){
        Log.w(TAG_DEBUG, "easontesting "+TAG_DEBUG+": "+Thread.currentThread().getStackTrace()[2].getMethodName() );
        try{
            Log.w(TAG_DEBUG, "funcThread 1 .currentThread() : "+ Thread.currentThread());
            Thread.sleep(t);
            Log.w(TAG_DEBUG, "funcThread 2 .currentThread() : "+ Thread.currentThread());
        }catch (Exception e){
            Log.w(TAG_DEBUG, Thread.currentThread().getStackTrace()[2].getMethodName()+" e.1" );
            e.printStackTrace();
        }
    }
}
