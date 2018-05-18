package com.easontesting.myfirstapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKNO = "";
    /* DEBUG private static final String TAG6 = "DrinkActivity"; */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        try{
            /*DB: Drink.java*/
            //Drink drink = Drink.drinks[drinkNo];
            /*DB: SQLite*/
            StarbuzzDatabaseHelper DBHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = DBHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK", new String[]{"_id","NAME","DESCRIPTION","IMAGE_RESOURCE_ID","FAVORITE"}, "_id=?",
                    new String[]{Integer.toString(drinkNo)}, null, null, null);
            if(cursor.moveToFirst()){
                int idNum = cursor.getInt(0);
                String nameText = cursor.getString(1);
                String descText = cursor.getString(2);
                int imgID = cursor.getInt(3);
                boolean isFavorite = (cursor.getInt(4) == 1);

                ImageView photo = (ImageView) findViewById(R.id.photo);
                TextView name = (TextView) findViewById(R.id.name);
                TextView description = (TextView) findViewById(R.id.description);
                CheckBox favoriteCheckBox = (CheckBox) findViewById(R.id.favorite);

                /*DB: Drink.java*/
                //photo.setImageResource(drink.getImageResourceId());
                //photo.setContentDescription(drink.getName());
                //name.setText(drink.getName());
                //description.setText(drink.getDescription());
                /*DB: SQLite*/
                photo.setImageResource(imgID);
                photo.setContentDescription(nameText);
                name.setText(nameText);
                description.setText(descText);
                favoriteCheckBox.setChecked(isFavorite);

                /* DEBUG
                Log.e(TAG6,"easontesting DrinkActivity onCreate>if(cursor.moveToFirst()){ ["+DBHelper+"], db ["+db+"], cursor ["+cursor+"], drinkNo ["+drinkNo+"],"
                        +"idNum ["+idNum+"], "
                        +"nameText ["+nameText+"], descText ["+descText+"], imgID ["+imgID+"], "
                        +"isFavorite ["+isFavorite+"]"); */
            }
            cursor.close();
            db.close();
        }catch(SQLiteException e){
            CharSequence txt = "DB error: " + e.getMessage();
            Toast.makeText(this, txt, Toast.LENGTH_LONG).show();
        }
    }
    public void onFavoriteClicked(View v){
        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        /*Before using AsyncTask*/
        /*
        CheckBox favoriteCheckBox = (CheckBox) findViewById(R.id.favorite);
        boolean cb_checked = favoriteCheckBox.isChecked();
        ContentValues favoriteContentValues = new ContentValues();
        favoriteContentValues.put("FAVORITE", cb_checked);
        try{
            StarbuzzDatabaseHelper DBHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            //db.update(String table, ContentValues values, String whereClause, String[] whereArgs)
            db.update("DRINK", favoriteContentValues, "_id = ?", new String[]{Integer.toString(drinkNo)});
            db.close();
        }catch(SQLiteException e){
            Toast.makeText(this, "DB error", Toast.LENGTH_LONG).show();
        }
        */
        /*After using AsyncTask*/
        new UpdateDrinkTask().execute(drinkNo);
    }
    //private class UpdateDrinkTask extends AsyncTask<Params, Progress, Result>{
    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean>{
        ContentValues favoriteContentValues = new ContentValues();
        protected void onPreExecute(){
            CheckBox favoriteCheckBox = (CheckBox) findViewById(R.id.favorite);
            boolean cb_checked = favoriteCheckBox.isChecked();
            favoriteContentValues.put("FAVORITE", cb_checked);
        }
        protected Boolean doInBackground(Integer... drinks){
            int drinkNo = drinks[0];
            StarbuzzDatabaseHelper DBHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
            try{
                SQLiteDatabase db = DBHelper.getWritableDatabase();
                //db.update(String table, ContentValues values, String whereClause, String[] whereArgs)
                db.update("DRINK", favoriteContentValues, "_id = ?", new String[]{Integer.toString(drinkNo)});
                db.close();
                return true;
            }catch(SQLiteException e){
                return false;
            }
        }
        protected void onProgressUpdate(Void... values){}
        protected void onPostExecute(Boolean success){
            if(!success){
                Toast.makeText(DrinkActivity.this, "DB error", Toast.LENGTH_SHORT).show();
            }
        }
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
