package com.example.moviecatalogue.controller.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class sqlite_helper extends SQLiteOpenHelper {

    private static final String dbName = "movieCatalogue";
    private static final int dbVersion = 1;

    public sqlite_helper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_movie = "CREATE TABLE movie_favorite(id INT PRIMARY KEY, poster_path VARCHAR(50), title VARCHAR(20)," +
                "release_date VARCHAR(30), popularity DOUBLE)";
        db.execSQL(tb_movie);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
