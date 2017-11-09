package com.example.sqliteinsert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class myDbAdapter {

    myDbHelper helper;

    public myDbAdapter(Context context) {
        helper = new myDbHelper(context);
    }


    static class myDbHelper extends SQLiteOpenHelper {
        private static final String CREATE_TABLE = "CREATE TABLE " + MainContract.UserEntity.TABLE_NAME + "( "
                + MainContract.UserEntity.UID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + MainContract.UserEntity.USER_NAME + " VARCHAR(225), "
                + MainContract.UserEntity.USER_PWD + " VARCHAR(225));";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + MainContract.UserEntity.TABLE_NAME;

        private Context context;

        public myDbHelper(Context context) {
            super(context, MainContract.DATABASE_NAME, null, MainContract.DATABASE_VERSION);
            this.context = context;
            Message.message(context, "Started...");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {

                db.execSQL(CREATE_TABLE);
                Message.message(context, "TABLE CREATED");
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + MainContract.UserEntity.TABLE_NAME, null);
        return res;
    }

    public long insertData(String name, String password) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MainContract.UserEntity.USER_NAME, name);
        contentValues.put(MainContract.UserEntity.USER_PWD, password);
        long id = db.insert(MainContract.UserEntity.TABLE_NAME, null, contentValues);
        return id;
    }

    public long updateData(String name, String newName) {

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MainContract.UserEntity.USER_NAME, name);
        contentValues.put(MainContract.UserEntity.USER_NAME, newName);

        long id = db.update(MainContract.UserEntity.TABLE_NAME, contentValues, MainContract.UserEntity.USER_NAME + "=?", new String[]{name});
        return id;
    }

    public long deleteData(String name) {

        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.delete(MainContract.UserEntity.TABLE_NAME, MainContract.UserEntity.USER_NAME + "= '" + name + "'", null);
        return id;
    }
}
