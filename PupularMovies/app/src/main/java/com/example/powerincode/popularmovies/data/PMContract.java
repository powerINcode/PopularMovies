package com.example.powerincode.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

/**
 * Created by powerman23rus on 02.11.17.
 * Enjoy ;)
 */

public class PMContract {

    public static final String AUTHORITY = "com.example.powerincode.popularmovies";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITE = "FAVORITE";

    public static class FavoriteMoviesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String TABLE_NAME = "favorite_movie";

        public static final int TABLE_COLUMN_ID_INDEX = 0;
        public static final int TABLE_COLUMN_TITLE_INDEX = 1;
        public static final int TABLE_COLUMN_POSTER_PATH_INDEX = 2;
        public static final int TABLE_COLUMN_GENRES_INDEX = 3;
        public static final int TABLE_COLUMN_VOTES_INDEX = 4;
        public static final int TABLE_COLUMN_DESCRIPTION_INDEX = 5;
        public static final int TABLE_COLUMN_ADD_DATE_INDEX = 6;

        public static final String TABLE_COLUMN_TITLE = "title";
        public static final String TABLE_COLUMN_POSTER_PATH = "poster_path";
        public static final String TABLE_COLUMN_GENRES = "genres";
        public static final String TABLE_COLUMN_VOTES = "votes";
        public static final String TABLE_COLUMN_DESCRIPTION = "description";
        public static final String TABLE_COLUMN_ADD_DATE = "add_date";
    }
}
