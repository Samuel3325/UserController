package com.ahadu.usercontroller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class DatabaseHelperClass extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "User_Authentication.db";
    public static final String TABLE_NAME = "Users_Data";
    public static final String Col_2 = "full_name";
    public static final String COL_3 = "user_name";
    public static final String COL_4 = "gender";
    public static final String COL_5 = "phone_no";
    public static final String COL_6 = "email";
    public static final String COL_7 = "password";
    public static final String COL_8 = "date_created";


    public DatabaseHelperClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = " CREATE TABLE "+TABLE_NAME+
                "("
                +Col_2+ " varchar(100),"
                +COL_3+ " varchar(100),"
                +COL_4+ " varchar(100),"
                +COL_5+ " varchar(100),"
                +COL_6+ " varchar(100),"
                +COL_7+ " varchar(100),"
                +COL_8+ " varchar(100)"
                +")";
        db.execSQL(create_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
    public boolean insertData(String fn, String un, String gender, String phone_no, String email, String pass){
        String date = (new Date()).toString();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2,fn);
        contentValues.put(COL_3,un);
        contentValues.put(COL_4,gender);
        contentValues.put(COL_5,phone_no);
        contentValues.put(COL_6,email);
        contentValues.put(COL_7,pass);
        contentValues.put(COL_8, date);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1) {
            return false;
        }else {
            return true;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME ,null);
        return cursor;
    }
    public Cursor getSelectedData(String user_name){
        SQLiteDatabase db = getWritableDatabase();
        Cursor res;
        String filter = COL_3 + " , "+ COL_7+ " ";
        String condition ="'"+user_name+"'"+ " = "+COL_3;
        String query = "SELECT "+ filter + " FROM " + TABLE_NAME + " WHERE " + condition;
        res = db.rawQuery(query,null);
        return res;
    }
    public Cursor getProfileData(String user_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String filter =COL_3+" , "+COL_5+" , "+COL_6;
        String condition = "'"+user_name+"'"+ " = "+COL_3;
        String query = "SELECT "+ filter +" FROM "+TABLE_NAME+" WHERE "+condition;
        cursor = db.rawQuery(query,null);
        return cursor;
    }
    public void delete(String user_name){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = "'"+user_name+"'"+ " = "+COL_3;
        Cursor cursor = db.rawQuery("DELETE FROM "+TABLE_NAME+" WHERE "+condition ,null);
        cursor.close();
    }

}
