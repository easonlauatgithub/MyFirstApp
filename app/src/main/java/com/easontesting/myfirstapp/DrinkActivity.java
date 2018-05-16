package com.easontesting.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKNO = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        createToast(drinkNo);
        Drink drink = Drink.drinks[drinkNo];

        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getName());
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(drink.getName());
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(drink.getDescription());
    }

    public void createToast(int drinkNo){
        CharSequence txt = "DrinkCategoryActivity.id > Intent > DrinkActivity.drinkNo: "+drinkNo;
        int duration = Toast.LENGTH_SHORT;
        Toast t = Toast.makeText(DrinkActivity.this, txt, duration);
        t.show();
    }
}
