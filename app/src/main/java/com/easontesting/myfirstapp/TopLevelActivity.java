package com.easontesting.myfirstapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TopLevelActivity extends AppCompatActivity {
    /*DEBUG*/private static final String TAG7 = "TopLevelActivity";
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        //Create Listener & Assign Listener to ListView listOptions
        AdapterView.OnItemClickListener listener1 = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View listViewItem, int pos, long id){
                if(pos == 0){
                    Intent i = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(i);
                }else{
                    TextView item = (TextView) listViewItem;
                    CharSequence txt = "Toast - the activity is not supported yet: " + item.getText();
                    Toast t = Toast.makeText(TopLevelActivity.this, txt, Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        };
        ListView listOptions = (ListView) findViewById(R.id.list_options);
        listOptions.setOnItemClickListener(listener1);

        //ListView listFavorites
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        try{
            SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);
            db = helper.getReadableDatabase();
            cursor = db.query("DRINK", new String[]{"_id","NAME"}, "FAVORITE=?", new String[]{Integer.toString(1)},null,null,null);
            //cursor = db.query("DRINK", new String[]{"_id","NAME"}, "FAVORITE=1",null,null,null,null);
            CursorAdapter adapter1 = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                    new String[]{"NAME"}, new int[]{android.R.id.text1},0);
            listFavorites.setAdapter(adapter1);
        }catch ( SQLiteException e ){ Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();}

        AdapterView.OnItemClickListener listener2 = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View listViewItem, int pos, long id){
                    Intent i = new Intent(TopLevelActivity.this, DrinkActivity.class);
                    i.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
                    startActivity(i);
            }
        };
        listFavorites.setOnItemClickListener(listener2);
    }

    public void onRestart(){
        super.onRestart();
        try{
            SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);
            db = helper.getReadableDatabase();
            Cursor newCursor = db.query("DRINK", new String[]{"_id","NAME"}, "FAVORITE=?", new String[]{Integer.toString(1)},null,null,null);
            ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
            CursorAdapter adapter2 = (CursorAdapter) listFavorites.getAdapter();
            adapter2.changeCursor(newCursor);
            cursor = newCursor;
        }catch ( SQLiteException e ){ Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();}
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
