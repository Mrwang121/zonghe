package com.example.zonghe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper {
    private static final String DATABASE_NAME = "datastorage";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String ID = "_id";
    private static final String USERNAME = "username";
    private static final String USERPASSWORD = "userpassword";
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    private static class DBOpenHelper extends SQLiteOpenHelper{
        private static final String CREATE_TABLE = "create table "+TABLE_NAME+"("+ID+" integer primary key autoincrement, "
                +USERNAME+" text not null, "+USERPASSWORD+" text not null);";

        public DBOpenHelper(Context context) {
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
            onCreate(sqLiteDatabase);
        }

    }
    public DBHelper(Context context){
        helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }
    public void insert(User user){
        ContentValues values = new ContentValues();
        values.put(USERNAME,user.getUsername());
        values.put(USERPASSWORD,user.getStrPassword());
        db.insert(TABLE_NAME,null,values);
    }
    public User query(String name){
        User user = new User();
        Cursor cursor = db.query(TABLE_NAME,new String[]{USERNAME,USERPASSWORD},
                "username=?",new String[]{name},null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            user.setUsername(cursor.getString(0));
            user.setUserpassword(cursor.getString(1));
            return user;
        }
        cursor.close();
        return null;
    }
}
