package com.easontesting.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class LayoutActivity extends AppCompatActivity {
    private static final String TAG4 = "LayoutActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        createToast(null);
    }
    /* Other components */
    public void onToggleButtonClicked(View view){
        createToast(view);
        boolean on = ((ToggleButton) view).isChecked();
        if(on){
            Log.e(TAG4,"easontesting LayoutActivity Toggle on");
        }else{
            Log.e(TAG4,"easontesting LayoutActivity Toggle off");
        }
    }
    public void onSwitchButtonClicked(View view){
        createToast(view);
        boolean on = ((Switch) view).isChecked();
        if(on){
            Log.e(TAG4,"easontesting LayoutActivity Switch on");
        }else{
            Log.e(TAG4,"easontesting LayoutActivity Switch off");
        }
    }
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()){
            case R.id.checkbox_milk:
                if(checked)
                    Log.e(TAG4,"easontesting LayoutActivity Checkbox milk checked");
                else
                    Log.e(TAG4,"easontesting LayoutActivity Checkbox milk unchecked");
                break;
            case R.id.checkbox_sugar:
                if(checked)
                    Log.e(TAG4,"easontesting LayoutActivity Checkbox sugar checked");
                else
                    Log.e(TAG4,"easontesting LayoutActivity Checkbox sugar unchecked");
                break;
        }
    }
    public void onCheckboxButtonClicked(View view){
        createToast(view);
        CheckBox cb1 = (CheckBox) findViewById(R.id.checkbox_milk);
        CheckBox cb2 = (CheckBox) findViewById(R.id.checkbox_sugar);
        boolean cb1_checked = cb1.isChecked();
        boolean cb2_checked = cb2.isChecked();
        if(cb1_checked){
            Log.e(TAG4,"easontesting LayoutActivity Checkbox milk checked");
        }else{
            Log.e(TAG4,"easontesting LayoutActivity Checkbox milk unchecked");
        }
        if(cb2_checked){
            Log.e(TAG4,"easontesting LayoutActivity Checkbox sugar checked");
        }else{
            Log.e(TAG4,"easontesting LayoutActivity Checkbox sugar unchecked");
        }
    }
    public void onRadioButtonClicked(View view){
        createToast(view);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        int rg_id = rg.getCheckedRadioButtonId();
        switch(rg_id){
            case R.id.radio_cavemen:
                    Log.e(TAG4,"easontesting LayoutActivity Radiobutton cavemen checked");
                break;
            case R.id.radio_astronauts:
                    Log.e(TAG4,"easontesting LayoutActivity Radiobutton astronauts checked");
                break;
        }
    }
    public void onRadioGroupButtonClicked(View view){
        createToast(view);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        int rg_id = rg.getCheckedRadioButtonId();
        if(rg_id == -1){
            Log.e(TAG4,"easontesting LayoutActivity Radiobutton unchecked");
        }else{
            RadioButton rb1 = (RadioButton) findViewById(rg_id);
            if(rg_id == R.id.radio_cavemen){
                Log.e(TAG4,"easontesting LayoutActivity Radiobutton radio_cavemen "+rb1+" checked");
            }
            if(rg_id == R.id.radio_astronauts){
                Log.e(TAG4,"easontesting LayoutActivity Radiobutton radio_astronauts "+rb1+" checked");
            }
        }
    }
    public void onSpinnerButtonClicked(View view){
        createToast(view);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String string = String.valueOf( spinner.getSelectedItem() );
            Log.e(TAG4,"easontesting LayoutActivity Spinner "+string+" is selected");
    }
    public void createToast(View v){
        CharSequence txt = "Toast - onCreate LayoutActivity";
        if(v!=null) {
            txt = "Toast - " + v.getClass().getSimpleName();
        }
        int duration = Toast.LENGTH_SHORT;
        Toast t = Toast.makeText(this, txt, duration);
        t.show();
    }
}
