package com.pietheta.alarmclock.Alarm;

public class AlarmItem {

    private String time;
    private String date;
    private boolean isSwitchOn;

    public AlarmItem(String time, String date, boolean isSwitchOn) {
        this.time = time;
        this.date = date;
        this.isSwitchOn = isSwitchOn;
    }

    public String getTime() {
        return time;
    }

    public int getHour() {
        //06:45 PM
        int hour = Integer.parseInt(time.substring(0, 2));
        String ext = time.substring(6, 8); // AM PM

        if (ext.equals("pm")) {
            if (hour != 12) hour = hour + 12;
        } else if (hour == 12) {//12am == 0
            hour = 0;
        }
        return hour;
    }

    public int getMinute() {
        return Integer.parseInt(time.substring(3, 5));
    }

    public boolean isSwitchOn() {
        return isSwitchOn;
    }

    public void setSwitchState(boolean isOn) {
        isSwitchOn = !isOn;
    }

    public String getDate() {
        return date;
    }
}
