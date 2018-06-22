package com.exama.exam;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlsSQL extends SQLiteOpenHelper {




    public ControlsSQL(Context context) {
        // конструктор суперкласса
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // создаем таблицу с полями
        db.execSQL("create table table1 ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "login text unique,"
                + "birthday text,"
                + "password text" + ");");

        db.execSQL("create table table2 ("
                + "id integer primary key autoincrement,"
                + "image text,"
                + "title text unique,"
                + "description text" + ");");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}