package com.easontesting.myfirstapp;

//import android.support.v7.app.AppCompatActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkCategoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_drink_category); /* Use ListActivity, so no need to use layout */
        /*  ListView > Adapter > DataSource
            ListView > Array Adapter > Drink.java drinks
            new ArrayAdapter<Drink>(context, layout, array); */
        ListView listViewOfDrinks = getListView();
        ArrayAdapter<Drink> adapter1 = new ArrayAdapter<Drink>(this, android.R.layout.simple_list_item_1, Drink.drinks);
        listViewOfDrinks.setAdapter(adapter1);
        /* Create Listener and assign to ListView
        AdapterView.OnItemClickListener listener2 = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View listViewItem, int pos, long id){
                if(pos == 10){
                    Intent i = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
                    startActivity(i);
                }else{
                    TextView item = (TextView) listViewItem;
                    CharSequence txt = "Toast - the activity is not supported yet: " + item.getText();
                    int duration = Toast.LENGTH_SHORT;
                    Toast t = Toast.makeText(DrinkCategoryActivity.this, txt, duration);
                    t.show();
                }
            }
        };
        listViewOfDrinks.setOnItemClickListener(listener2);
        */
    }/* onCreate */
    public void onListItemClick(ListView listView, View listViewItem, int pos, long id){
        Intent i = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        i.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
        startActivity(i);
    }
}
