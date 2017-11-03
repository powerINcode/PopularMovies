package com.example.powerincode.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.powerincode.popularmovies.data.PMContract.FavoriteMoviesEntry;


/**
 * Created by powerman23rus on 02.11.17.
 * Enjoy ;)
 */

public class PMDbHelper extends SQLiteOpenHelper {
    private static final int version = 1;
    private static final String DATABASE_NAME = "popular_movies.db";

    public PMDbHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_DB_QUERY = "CREATE TABLE " + FavoriteMoviesEntry.TABLE_NAME + " (" +
                FavoriteMoviesEntry._ID +  " INTEGER NOT NULL UNIQUE," +
                FavoriteMoviesEntry.TABLE_COLUMN_TITLE + " TEXT NOT NULL," +
                FavoriteMoviesEntry.TABLE_COLUMN_POSTER_PATH + " TEXT NOT NULL," +
                FavoriteMoviesEntry.TABLE_COLUMN_GENRES + " TEXT NOT NULL," +
                FavoriteMoviesEntry.TABLE_COLUMN_VOTES + " REAL NOT NULL," +
                FavoriteMoviesEntry.TABLE_COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                FavoriteMoviesEntry.TABLE_COLUMN_ADD_DATE + " NUMERIC NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_DB_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
