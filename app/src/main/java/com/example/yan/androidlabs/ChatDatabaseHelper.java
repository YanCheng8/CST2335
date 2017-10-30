package com.example.yan.androidlabs;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;


public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME= "Message.db";
    private static final int VERSION_NUM = 1;
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "name";
    public static String TABLE_NAME = "ChatInfo";
    protected static final String ACTIVITY_NAME = "ChatDatabaseHelper";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MSG = "CREATE TABLE " + TABLE_NAME  + "("
                + KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KEY_MESSAGE + " TEXT )";
        db.execSQL(CREATE_TABLE_MSG);
        Log.i(ACTIVITY_NAME, "Calling onCreate");
        // db.execSQL("create table   "+  TABLE_NAME +(KEY_ID +"   INTEGER AUTOINCREMENT not null,   "+KEY_MESSAGE+"    text   )"));
    }

    /*
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                "(KEY_ID integer primary key autoincrement, " +
                "KEY_MESSAGE text)");
        Log.i(ACTIVITY_NAME, "Calling onCreate");
    }
    */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        Log.i(ACTIVITY_NAME, "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
            Log.i("Database ", "onOpen was called");
    }
}

