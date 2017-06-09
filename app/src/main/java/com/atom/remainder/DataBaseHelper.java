package com.atom.remainder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nelson Andrew on 03-02-2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "EventDatabase.db";
    public static final String TABLE_NAME = "EventList";
    public static final String Col_1 = "ID";
    public static final String Col_2 = "NAME";
    public static final String Col_3 = "DESCRIPTION";
    public static final String Col_4 = "DATE";
    public static final String Col_5 = "TIME";
    public static final String Col_6 = "LOCATION";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT ,DESCRIPTION TEXT ,DATE TEXT ,TIME TEXT,LOCATION TEXT )";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);

    }

    public boolean addData(String name,String description,String date,String time,String location ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2,name);
        contentValues.put(Col_3,description);
        contentValues.put(Col_4,date);
        contentValues.put(Col_5,time);
        contentValues.put(Col_6,location);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT ID,NAME FROM " + TABLE_NAME,null);
        return data;
    }
    public Cursor getRow(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE ID = ?", new String[]{id});
        return cursor;

    }

    public void DeleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME);
        db.close();
    }

    public Integer DeleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Id = ?", new String[]{id});
    }

    public boolean UpdateData(String id,String name,String description,String date,String time,String location){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1,id);
        contentValues.put(Col_2,name);
        contentValues.put(Col_3,description);
        contentValues.put(Col_4,date);
        contentValues.put(Col_5,time);
        contentValues.put(Col_6,location);

        db.update(TABLE_NAME,contentValues,"ID = ?",new String[] {id});
        return true;
    }

}
