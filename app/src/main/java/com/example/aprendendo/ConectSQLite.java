package com.example.aprendendo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConectSQLite extends SQLiteOpenHelper {

    private static final String name = "tarefas.db";
    private static final int version = 1;


    public ConectSQLite(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tarefas(id integer primary key autoincrement, nome varchar(30), descricao varchar(100))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
