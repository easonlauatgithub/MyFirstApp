package com.easontesting.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PWOSDatabaseHelper extends SQLiteOpenHelper {
    private final String tag = "DatabaseHelper";
    public static final String DB_NAME = "android";
    public static final String TABLE_NAME = "names";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STATUS = "status";
    private static final int DB_VERSION = 2;
    public PWOSDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.w(tag, "easontesting "+tag+" DatabaseHelper 1");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w(tag, "easontesting "+tag+" onCreate 1");
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME +" VARCHAR, "
                + COLUMN_STATUS +" TINYINT);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(tag, "easontesting "+tag+" onUpgrade 1");
        String sql = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
    /* 0 means synced, 1 means not synced*/
    public boolean addName(String name, int status) {
        Log.w(tag, "easontesting "+tag+" addName 1, name:"+name+", status:"+status);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_STATUS, status);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }
    /*
    * This method taking two arguments
    * first one is the id of the name for which
    * we have to update the sync status
    * and the second one is the status that will be changed
    * */
    public boolean updateNameStatus(int id, int status) {
        Log.w(tag, "easontesting "+tag+" updateNameStatus 1, id:"+id+", status:"+status);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }
    /*return all the name stored in sqlite*/
    public Cursor getNames() {
        Log.w(tag, "easontesting "+tag+" getNames 1");
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    /*return all the unsynced name */
    public Cursor getUnsyncedNames() {
        Log.w(tag, "easontesting "+tag+" getUnsyncedNames 1");
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
}
