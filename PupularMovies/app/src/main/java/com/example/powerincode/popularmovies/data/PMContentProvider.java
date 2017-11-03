package com.example.powerincode.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.powerincode.popularmovies.data.PMContract.FavoriteMoviesEntry;

/**
 * Created by powerman23rus on 02.11.17.
 * Enjoy ;)
 */

public class PMContentProvider extends ContentProvider {
    public static final String TAG = PMContentProvider.class.getSimpleName();
    public static final int FAVORITES = 100;
    public static final int FAVORITES_WITH_ID = 101;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PMContract.AUTHORITY, PMContract.PATH_FAVORITE, FAVORITES);
        uriMatcher.addURI(PMContract.AUTHORITY, PMContract.PATH_FAVORITE + "/#", FAVORITES_WITH_ID);

        return uriMatcher;
    }

    private PMDbHelper mDbHelper;
    private final UriMatcher mUriMatcher = getUriMatcher();

    @Override
    public boolean onCreate() {
        mDbHelper = new PMDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        try {
            switch (mUriMatcher.match(uri)) {
                case FAVORITES:
                    return mDbHelper.getReadableDatabase()
                            .query(FavoriteMoviesEntry.TABLE_NAME, columns, selection, selectionArgs,
                                    null, null, FavoriteMoviesEntry.TABLE_COLUMN_ADD_DATE);
                case FAVORITES_WITH_ID:
                    long id = ContentUris.parseId(uri);
                    return mDbHelper.getReadableDatabase()
                            .query(FavoriteMoviesEntry.TABLE_NAME, columns, "_id = ?", new String[] { String.valueOf(id) },
                                    null, null, FavoriteMoviesEntry.TABLE_COLUMN_ADD_DATE);
                default:
                    throw new UnsupportedOperationException("Unknown Uri: " + uri);
            }
        } catch(Exception e) {
            Log.e(TAG, "Unable to select favorite movies: " + uri);
            return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        try{
            switch (mUriMatcher.match(uri)) {
                case FAVORITES:
                    long id = mDbHelper.getWritableDatabase()
                            .insert(FavoriteMoviesEntry.TABLE_NAME, null, contentValues);

                    if (id > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                        return ContentUris.withAppendedId(FavoriteMoviesEntry.CONTENT_URI, id);
                    } else {
                        throw new android.database.sqlite.SQLiteException("Failed to insert favorite movie: " + uri);
                    }
                default:
                    throw new UnsupportedOperationException("Unknown Uri: " + uri);
            }
        } catch(Exception e) {
            Log.e(TAG, "Unable to insert favorite movies: " + uri);
            return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        try {
            switch (mUriMatcher.match(uri)) {
                case FAVORITES_WITH_ID:
                    long favoriteMovieId = ContentUris.parseId(uri);
                    int delete = mDbHelper.getWritableDatabase()
                            .delete(FavoriteMoviesEntry.TABLE_NAME, "_id = ?", new String[]{String.valueOf(favoriteMovieId)});

                    if (delete > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                        return delete;
                    } else {
                        throw new android.database.sqlite.SQLiteException("Unable to delete favorite movie:" + uri);
                    }

                default: throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        } catch(Exception e) {
            Log.e(TAG, "Unable to delete favorite movies: " + uri);
            return -1;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
