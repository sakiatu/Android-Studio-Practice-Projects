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

    public int getImageResource() {
        return imageResource;
    }

    public boolean isSwitchOn() {
        return isSwitchOn;
    }

    public void setSwitchState(){

        if(isSwitchOn){
            isSwitchOn=false;
        }
        else{
            time ="OFF";
            isSwitchOn=true;

        }
    }
}
