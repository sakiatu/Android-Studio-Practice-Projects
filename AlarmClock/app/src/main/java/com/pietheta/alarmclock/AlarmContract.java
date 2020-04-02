package com.pietheta.alarmclock;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class AlarmContract {

    public static final String CONTENT_AUTHORITY ="com.pietheta.alarmclock.PROVIDER";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH = "alarm";

    public static final class AlarmEntry implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH;

        public static final String TABLE_NAME= "AlarmList";

        public static final String _ID= BaseColumns._ID;

        public static final String KEY_TIME="time";
        public static final String KEY_MISSION="mission";
        public static final String KEY_RINGTONE="ringtone";
        public static final String KEY_ACTIVE="active";
        public static final String KEY_REPEAT="repeat";
    }























}
