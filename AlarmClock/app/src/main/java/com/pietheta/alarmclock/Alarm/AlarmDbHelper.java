package com.pietheta.alarmclock.Alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pietheta.alarmclock.Alarm.AlarmContract;

public class AlarmDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alarm.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "AlarmList";

    public static final String ID = "_id";

    public static final String KEY_TIME = "time";
    public static final String KEY_MISSION = "mission";
    public static final String KEY_RINGTONE = "ringtone";
    public static final String KEY_ACTIVE = "active";
    public static final String KEY_REPEAT = "repeat";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID + " integer primary key , " +
            KEY_TIME + " text not null, " +
            KEY_MISSION + " text not null, " +
            KEY_RINGTONE + " text not null, " +
            KEY_REPEAT + " text not null, " +
            KEY_ACTIVE + " text not null " + ");";

    private static final String DROP_TABLE = " DROP TABLE IF EXISTS " + AlarmContract.AlarmEntry.TABLE_NAME;
    private static final String SELECT_ALL = " SELECT * FROM " + TABLE_NAME;


    public AlarmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //*****************************     All methods     *************************//

    public long addAlarm(String alarmTime, String mission, String ringtone, String repeat, String active) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_TIME, alarmTime);
        contentValues.put(KEY_MISSION, mission);
        contentValues.put(KEY_RINGTONE, ringtone);
        contentValues.put(KEY_REPEAT, repeat);
        contentValues.put(KEY_ACTIVE, active);

        long n = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return n;
    }


    //update data
    public long updateAlarm(int position, String alarmTime, String mission, String ringtone, String repeat, String active) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_TIME, alarmTime);
        contentValues.put(KEY_MISSION, mission);
        contentValues.put(KEY_RINGTONE, ringtone);
        contentValues.put(KEY_REPEAT, repeat);
        contentValues.put(KEY_ACTIVE, active);

        int n = sqLiteDatabase.update(TABLE_NAME, contentValues, ID + "=?", new String[]{getId(position)});
        sqLiteDatabase.close();
        return n;

    }


  /*  public long updatePresent(String id,String present)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID,id);
        contentValues.put(PRESENT,present);

        long x = sqLiteDatabase.update(TABLE_NAME,contentValues,ID+"=?",new String[]{id});
        return x;
    }
*/

    //delete data
    public int deleteAlarm(int position) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        int n = sqLiteDatabase.delete(TABLE_NAME, ID + "=?", new String[]{getId(position)});
        sqLiteDatabase.close();
        return n;

    }

    //show all data
    public Cursor showAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL, null);
        return cursor;

    }


    //delete row
    public Cursor selectAlarm(String position) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.query(TABLE_NAME, null, ID + "=?", new String[]{position}, null, null, null);

    }

    public String getId(int position) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from AlarmList limit 1 offset " + position, null);
        if (cursor.moveToFirst())
            return cursor.getString(0);
        return null;
    }


    public void updateActive(int position, boolean state) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_ACTIVE, String.valueOf(state));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID + "=?", new String[]{getId(position)});
        sqLiteDatabase.close();

    }

    public void updateRepeat(int position, String repeat_type) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_REPEAT, repeat_type);

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID + "=?", new String[]{getId(position)});
        sqLiteDatabase.close();

    }

    public String getAlarmTime(int position) {
        String time = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from AlarmList limit 1 offset " + position, null);
        if (cursor.moveToFirst())
            time = cursor.getString(cursor.getColumnIndex(KEY_TIME));
        return time;
    }

    public String getMissionType(int position) {
        String mission = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from AlarmList limit 1 offset " + position, null);
        if (cursor.moveToFirst())
            mission = cursor.getString(cursor.getColumnIndex(KEY_MISSION));
        return mission;

    }

    public String getRingtone(int position) {
        String ringtone = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from AlarmList limit 1 offset " + position, null);
        if (cursor.moveToFirst())
            ringtone = cursor.getString(cursor.getColumnIndex(KEY_RINGTONE));
        return ringtone;
    }

    public String getRepeatType(int position) {
        String repeatType = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from AlarmList limit 1 offset " + position, null);
        if (cursor.moveToFirst())
            repeatType = cursor.getString(cursor.getColumnIndex(KEY_REPEAT));
        return repeatType;
    }

    public String getActiveState(int position) {
        String activeState = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from AlarmList limit 1 offset " + position, null);
        if (cursor.moveToFirst())
            activeState= cursor.getString(cursor.getColumnIndex(KEY_ACTIVE));
        return activeState;
    }
}
