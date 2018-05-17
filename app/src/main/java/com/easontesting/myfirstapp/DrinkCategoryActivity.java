package com.easontesting.myfirstapp;

//import android.support.v7.app.AppCompatActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkCategoryActivity extends ListActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_drink_category); /* Use ListActivity, so no need to use layout */
        ListView listViewOfDrinks = getListView();
        /*DB: Drink.java*/
        //ArrayAdapter<Drink> adapter1 = new ArrayAdapter<Drink>(this, android.R.layout.simple_list_item_1, Drink.drinks);
        //listViewOfDrinks.setAdapter(adapter1);
        /*DB: SQLite*/
        try{
            SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);
            db = helper.getReadableDatabase();
            cursor = db.query("DRINK", new String[]{"_id","NAME"}, null,null,null,null,null);

            CursorAdapter adapter2 = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                    new String[]{"NAME"}, new int[]{android.R.id.text1},0);
            listViewOfDrinks.setAdapter(adapter2);
        }catch ( SQLiteException e ){
            Toast.makeText(this, "DB error", Toast.LENGTH_LONG).show();
        }
        /* Use ListActivity onListItemClick(), so no need to use listener */
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
    }
    public void onListItemClick(ListView listView, View listViewItem, int pos, long id){
        Intent i = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        i.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
        startActivity(i);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}

/* How to use DB?
SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);
SQLiteDatabase db = helper.getReadableDatabase();
* INSERT, UPDATE, DELETE
db.insert(String table, String nullColumnHack, ContentValues values)
db.update(String table, ContentValues values, String whereClause, String[] whereArgs)
db.delete(String table, String whereClause, String[] whereArgs)
* DATASOURCE > CursorAdapter > Cursor > ListView
Cursor cursor = db.query( String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy )
Example
Cursor cursor = db.query("DRINK", new String[]{"NAME","DESCRIPTION"}, "NAME=?", new String[]{"Latter"},null,null,null);
Cursor cursor = db.query("DRINK", new String[]{"NAME","DESCRIPTION"}, "_id=?", new String[]{Integer.toString(1)},null,null,null);
Cursor cursor = db.query("DRINK", new String[]{"_id","NAME","FAVORITE"},null,null,null,null, "NAME ASC");
Cursor cursor = db.query("DRINK", new String[]{"_id","NAME","FAVORITE"},null,null,null,null, "FAVORITE DESC,NAME ASC");
Cursor cursor = db.query("DRINK", new String[]{"COUNT(_id) AS count"},null,null,null,null,null);
Cursor cursor = db.query("DRINK", new String[]{"AVG(PRICE) AS price"},null,null,null,null,null);
Cursor cursor = db.query("DRINK", new String[]{"FAVORITE", "COUNT(_id) AS count"},null,null,"FAVORITE",null,null);
CursorAdapter adapter = SimpleCursorAdapter(context, layout, cursor, String[] fromWhichColumns, int[] toWhichView, flags:0)
ListView listViewOfDrinks = getListView();
listViewOfDrinks.setAdapter(adapter2);
* DATASOURCE > TextView,...
if(cursor.moveToFirst()){ //moveToLast(), moveToPrevious(), moveToNext()
    Type a = cursor.getType(_columnPosition);
    boolean b = (cursor.getInt(_columnPosition) == 1);
}
TextView.setText(a);
CheckBox.setChecked(b);
*/
