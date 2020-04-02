package com.pietheta.alarmclock;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class AlarmProvider extends ContentProvider {
    public AlarmProvider() {
    }

    public static final int ALARM = 100;
    public static final int ALARM_ID = 110;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AlarmContract.CONTENT_AUTHORITY, AlarmContract.PATH, ALARM);
        uriMatcher.addURI(AlarmContract.CONTENT_AUTHORITY, AlarmContract.PATH + "/#", ALARM_ID);
    }

    private AlarmDbHelper helper;


    @Override
    public boolean onCreate() {
        helper = new AlarmDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = helper.getReadableDatabase();

        Cursor cursor = null;

        int match_value = uriMatcher.match(uri);
        switch (match_value) {
            case ALARM:
                cursor = database.query(AlarmContract.AlarmEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ALARM_ID:
                selection = AlarmContract.AlarmEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(AlarmContract.AlarmEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = helper.getWritableDatabase();

        int rowsDeleted = 0;

        int match_value = uriMatcher.match(uri);
        switch (match_value) {
            case ALARM:
                rowsDeleted = database.delete(AlarmContract.AlarmEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ALARM_ID:
                selection = AlarmContract.AlarmEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(AlarmContract.AlarmEntry.TABLE_NAME, selection, selectionArgs);
                break;
        }
        if (rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {

        int match_value = uriMatcher.match(uri);
        switch (match_value){
            case ALARM:
                return AlarmContract.AlarmEntry.CONTENT_LIST_TYPE;
            case ALARM_ID:
                return AlarmContract.AlarmEntry.CONTENT_ITEM_TYPE;
                default:
                    throw new IllegalStateException("Unknown uri");
        }


    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int match_value = uriMatcher.match(uri);
        switch (match_value) {
            case ALARM:
                return insertAlarm(uri, values);
            default:
                throw new IllegalArgumentException("Insertion failed");
        }

    }

    private Uri insertAlarm(Uri uri, ContentValues values) {
        SQLiteDatabase database = helper.getReadableDatabase();

        long id = database.insert(AlarmContract.AlarmEntry.TABLE_NAME, null, values);
        if (id == -1) {
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match_value = uriMatcher.match(uri);
        switch (match_value) {
            case ALARM:
                return updateAlarm(uri, values,selection,selectionArgs);
            case ALARM_ID:
                selection = AlarmContract.AlarmEntry._ID+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            default:
                throw new IllegalArgumentException("update failed");
        }
    }

    private int updateAlarm(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if(values.size()==0){
            return 0;
        }
        SQLiteDatabase database = helper.getWritableDatabase();
        int rowUpdated = database.update(AlarmContract.AlarmEntry.TABLE_NAME,values,selection,selectionArgs);
        if (rowUpdated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowUpdated;
    }
}
