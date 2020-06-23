package com.example.moviecatalogue.model.request;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.moviecatalogue.model.dao.load_movie;

import java.util.ArrayList;

public class crud_tb {

    SQLiteDatabase db;

    public crud_tb(SQLiteDatabase db) {
        this.db = db;
    }

    public void insertData(load_movie loadMovie) {
        String query = "INSERT INTO movie_favorite(id, poster_path, title, release_date, popularity) " +
                "VALUES(" + loadMovie.getId() + ",'" + loadMovie.getPosterPath() + "','"+ loadMovie.getTitle() +"','"+loadMovie.getReleaseDate()+"','"+loadMovie.getPopularity()+"');";
        db.execSQL(query);
    }

    public ArrayList<load_movie> select() {
        ArrayList<load_movie> noname = new ArrayList<>();

        String query = "select *from movie_favorite";
        Cursor cursor = db.rawQuery(query, null);;

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String poster_path = cursor.getString(1);
            String title = cursor.getString(2);
            String release_date = cursor.getString(3);
            Double popularity = cursor.getDouble(4);
            int kode_cast = cursor.getInt(5);
            noname.add(new load_movie(id, poster_path, title, release_date, popularity, kode_cast));
        }

        cursor.close();

        return noname;
    }

    public void deleteData(int id) {
        String query = "DELETE FROM movie_favorite WHERE id = " + id + ";";
        db.execSQL(query);
    }

    public int selectOne(int nomor_id) {
        int id = 0;
        String query = "SELECT *FROM movie_favorite WHERE id="+nomor_id+";";
        Cursor cursor = db.rawQuery(query, null);;

        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }

        cursor.close();
        return id;
    }

}
