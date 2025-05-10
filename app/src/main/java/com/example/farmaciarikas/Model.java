package com.example.farmaciarikas;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.SQLException;

public abstract class Model<M extends Model<M>> {
    protected static SQLiteOpenHelper dbHelper;   // se mantiene

    public static void init(SQLiteOpenHelper h) { // invocado una sola vez
        dbHelper = h;
    }
    protected final ContentValues valores = new ContentValues();


    // Métodos

    // Métodos CRUD
    abstract long insert() throws SQLException;
    abstract long update() throws SQLException;
    abstract long delete() throws SQLException;

    // Logic
    abstract boolean exists();
    public ContentValues getContentValues(){
        return valores;
    }
}
