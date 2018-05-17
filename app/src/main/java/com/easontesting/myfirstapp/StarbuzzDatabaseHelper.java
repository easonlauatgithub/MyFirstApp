package com.easontesting.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "starbuzz";
    private static final String TB_NAME = "DRINK";
    private static final int DB_VERSION = 3;
    StarbuzzDatabaseHelper(Context context){
/* SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
Create a helper object to create, open, and/or manage a database. */
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDataBase(db, 0, DB_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateMyDataBase(db, oldVersion, newVersion);
    }
    private void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            String sql =
                    "CREATE TABLE "+TB_NAME+" (" +
                            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "NAME TEXT," +
                            "DESCRIPTION TEXT," +
                            "IMAGE_RESOURCE_ID INTEGER);";
            db.execSQL(sql);
/* SQLiteDatabase method
insert(String table, String nullColumnHack, ContentValues values)
update(String table, ContentValues values, String whereClause, String[] whereArgs)
delete(String table, String whereClause, String[] whereArgs)
*/
            ContentValues espressoContentValues = new ContentValues();
            espressoContentValues.put("NAME", "Espresso");
            db.insert(TB_NAME, null, espressoContentValues);
            espressoContentValues.put("DESCRIPTION", "Espresso1311 is ...");
            espressoContentValues.put("IMAGE_RESOURCE_ID", R.drawable.coffee_espresso);
            String whereClause = "NAME = ?";
            String[] whereArgs = {"Espresso"};
            db.update(TB_NAME, espressoContentValues, whereClause, whereArgs);
            db.delete(TB_NAME, whereClause, whereArgs);

            ContentValues latteContentValues = new ContentValues();
            latteContentValues.put("NAME", "Latte");
            latteContentValues.put("DESCRIPTION", "Latte is ...");
            latteContentValues.put("IMAGE_RESOURCE_ID", R.drawable.coffee_latte);
            db.insert(TB_NAME, null, latteContentValues);

            ContentValues cappuccinoContentValues = new ContentValues();
            cappuccinoContentValues.put("NAME", "Cappuccino");
            cappuccinoContentValues.put("DESCRIPTION", "Cappuccino is ...");
            cappuccinoContentValues.put("IMAGE_RESOURCE_ID", R.drawable.coffee_cappuccino);
            db.insert(TB_NAME, null, cappuccinoContentValues);

            ContentValues mochaContentValues = new ContentValues();
            mochaContentValues.put("NAME", "Mocha");
            mochaContentValues.put("DESCRIPTION", "Mocha is ...");
            mochaContentValues.put("IMAGE_RESOURCE_ID", R.drawable.coffee_mocha);
            db.insert(TB_NAME, null, mochaContentValues);

            //insertDrink(db, TB_NAME, "Latte", "Latter is ...", R.drawable.coffee_latte);
            //insertDrink(db, TB_NAME, "Cappuccino", "Cappuccino is ...", R.drawable.coffee_cappuccino);
            //insertDrink(db, TB_NAME, "Mocha", "Mocha is ...", R.drawable.coffee_mocha);
        }
        if(oldVersion < 2) {
            String sql =
                    "ALTER TABLE "+TB_NAME+" ADD COLUMN FAVORITE NUMERIC;";
            db.execSQL(sql);
        }
    }
    public void dropTable(SQLiteDatabase db){
        String sql =
                "DROP TABLE IF EXISTS "+TB_NAME;
        db.execSQL(sql);
    }
    private static void insertDrink(SQLiteDatabase db, String tb, String name, String description, int resourceId){
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert(tb, null, drinkValues);
    }

}
