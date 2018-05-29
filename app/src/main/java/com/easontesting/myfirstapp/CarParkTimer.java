package com.easontesting.myfirstapp;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

    public class CarParkTimer extends AppCompatActivity {
    private static final String TAG3 = "CarParkTimer";
//    private Button doSetStartDate;
//    private Button doSetStartTime;
    private TextView textStartDate;
    private TextView textStartTime;
    private DatePickerDialog dateStartPickerDialog;
    private TimePickerDialog timeStartPickerDialog;
    private TextView textEndDate;
    private TextView textEndTime;
    private DatePickerDialog dateEndPickerDialog;
    private TimePickerDialog timeEndPickerDialog;
    @Override
   // @TargetApi(24)
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG3,"easontesting CarParkTimer onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_park_timer);
        doFindView();
        GregorianCalendar calendar = new GregorianCalendar();
        dateStartPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textStartDate.setText(year + "/" + (monthOfYear+1) + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        timeStartPickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //textStartTime.setText((hourOfDay > 12 ? hourOfDay - 12 : hourOfDay) + ":" +minute + " " + (hourOfDay > 12 ? "PM" : "AM"));
                textStartTime.setText( hourOfDay + ":" +minute );
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        dateEndPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textEndDate.setText(year + "/" + (monthOfYear+1) + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        timeEndPickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //textEndTime.setText((hourOfDay > 12 ? hourOfDay - 12 : hourOfDay) + ":" +minute + " " + (hourOfDay > 12 ? "PM" : "AM"));
                textEndTime.setText( hourOfDay + ":" +minute );
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }
    public void doFindView(){
        textStartDate = (TextView) findViewById(R.id.dateStarttext);
        textStartTime = (TextView) findViewById(R.id.timeStarttext);
        textEndDate = (TextView) findViewById(R.id.dateEndtext);
        textEndTime = (TextView) findViewById(R.id.timeEndtext);
    }
    public void setStartDate(View v){
        dateStartPickerDialog.show();
    }
    public void setStartTime(View v) { timeStartPickerDialog.show(); }
    public void setEndDate(View v){
        dateEndPickerDialog.show();
    }
    public void setEndTime(View v){
        timeEndPickerDialog.show();
    }

    public void calParkingFee(View view){
        Log.e(TAG3,"easontesting CarParkTimer calParkingFee");
        TextView tv_start_date = (TextView) findViewById(R.id.dateStarttext);
        TextView tv_start_time = (TextView) findViewById(R.id.timeStarttext);
        TextView tv_end_date = (TextView) findViewById(R.id.dateEndtext);
        TextView tv_end_time = (TextView) findViewById(R.id.timeEndtext);
        EditText et_charge = (EditText) findViewById(R.id.car_park_charge);
        String str_start_date = tv_start_date.getText().toString();
        String str_start_time = tv_start_time.getText().toString();
        String str_end_date = tv_end_date.getText().toString();
        String str_end_time = tv_end_time.getText().toString();
        String str_charge = et_charge.getText().toString();

        String str_start = str_start_date+" "+str_start_time;
        String str_end = str_end_date+" "+str_end_time;

        SimpleDateFormat unformatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        TextView tv_total_charge_detail = (TextView) findViewById(R.id.car_park_time_view);
        TextView tv_total_charge = (TextView) findViewById(R.id.car_park_time_view_summary);
        String str_total_charge_detail = "";
        String str_total_charge = "";
        try {
            Date date_start = unformatter.parse(str_start);
            Date date_end = unformatter.parse(str_end);
            /* Debug
            str_total_charge_detail += "Date date_start: " + date_start;
            str_total_charge_detail += "\nDate date_end: " + date_end;
            */
            long second_parked = (date_end.getTime() - date_start.getTime())/1000;
            int hour_parked = (int) second_parked/3600 + ((second_parked%3600 > 0)? 1:0);
            int parking_fee = hour_parked * Integer.parseInt(str_charge) ;
            if(parking_fee>=0){
                str_total_charge_detail = "泊車時數(不足一小時亦作一小時計): " + hour_parked
                        +"\n入場時間: " + str_start
                        +"\n離場時間: " + str_end
                        +"\n每小時收費: " + str_charge;
                str_total_charge = "總收費: " + parking_fee;
            }else{
                str_total_charge_detail =  "入場時間須早於離場時間!";
            }
        } catch(ParseException pe) {
            str_total_charge_detail =  "請輪入全部資料!";
        }
        tv_total_charge_detail.setText(str_total_charge_detail);
        tv_total_charge.setText(str_total_charge);
    }
}
