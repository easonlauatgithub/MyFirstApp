package com.easontesting.myfirstapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Iterator;
import java.util.List;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String message = intent.getStringExtra(Intent.EXTRA_TEXT);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    private BeerExpert expert = new BeerExpert();
    public void onClickFindBeer(View view){
        TextView brands = (TextView) findViewById(R.id.brands);
        Spinner color = (Spinner) findViewById(R.id.color);
        String beerType = String.valueOf(color.getSelectedItem());
        //brands.setText(beerType);
        List<String> brandList = expert.getBrands(beerType);
        StringBuilder b = new StringBuilder();
        //for(String brand: brandList){
        for(Iterator<String> i = brandList.iterator(); i.hasNext();){
            //brandsFormatted.append(brand).append("\n");
            b.append(i.next()).append("\n");
        }
        brands.setText(b);

    }
}
