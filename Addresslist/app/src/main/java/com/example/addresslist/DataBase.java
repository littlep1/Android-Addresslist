package com.example.addresslist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_PERSON = "create table person ("
            + "id integer primary key autoincrement,"
            + "name varchar(20),"
            + "tel varchar(20),"
            + "email varchar(30),"
            + "company varchar(30) )";
    private static final String DROP_TABLE_PERSON = "drop table if exists person";

    public DataBase(Context context) {
        super(context, "person.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_PERSON);
        onCreate(db);
    }
}
