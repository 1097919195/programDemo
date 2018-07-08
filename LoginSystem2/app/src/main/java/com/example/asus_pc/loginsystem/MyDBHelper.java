package com.example.asus_pc.loginsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHelper extends SQLiteOpenHelper {
    private Context mContext;
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version)
        {
            super(context,name,cursorFactory,version);
            mContext = context;
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userData(id integer primary key autoincrement,name text,password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
