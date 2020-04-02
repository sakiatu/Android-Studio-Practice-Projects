package com.pietheta.alarmclock;

import android.widget.TextView;
import android.widget.Toast;

public class AlarmItem {

    private String time;
    private int imageResource;
    private boolean isSwitchOn;

    public AlarmItem(String time, int imageResource, boolean isSwitchOn) {
        this.time = time;
        this.imageResource = imageResource;
        this.isSwitchOn = isSwitchOn;
    }

    public String getTime() {
        return time;
    }

    public int getHour() {
        //06:45 PM
        int hour = Integer.valueOf(time.substring(0, 2));
        String ext = time.substring(6, 8); // AM PM

        if (ext.equals("PM")) {
            if (hour != 12) hour = hour + 12;
        } else if (hour == 12) {//12am == 0
            hour = 0;
        }
        return hour;
    }

    public int getMinute() {
        return Integer.valueOf(time.substring(3, 5));
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isSwitchOn() {
        return isSwitchOn;
    }

    public void setSwitchState(boolean isOn) {

        isSwitchOn = !isOn;
    }
}
