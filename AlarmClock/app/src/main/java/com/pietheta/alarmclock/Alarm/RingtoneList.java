package com.pietheta.alarmclock.Alarm;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;

import java.util.ArrayList;

public class RingtoneList {
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> uri = new ArrayList<>();

    RingtoneList(Context context) {
        RingtoneManager manager = new RingtoneManager(context);
        Cursor cursor = manager.getCursor();
        int i = 0;
        while (cursor.moveToNext()) {
            names.add(cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX));
            uri.add(manager.getRingtoneUri(i++).toString());
        }
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<String> getUri() {
        return uri;
    }

    public int size() {
        return names.size();
    }
}
