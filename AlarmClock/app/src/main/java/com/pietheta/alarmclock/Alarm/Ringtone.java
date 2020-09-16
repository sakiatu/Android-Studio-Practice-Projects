package com.pietheta.alarmclock.Alarm;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;

public class Ringtone {
    private Uri ringUri;
    private Context context;
    private android.media.Ringtone ringtone;

    public Ringtone(Context context) {
        this.context = context;
        ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, ringUri);
    }

    public void setRingtone(String uri) {
        ringtone = RingtoneManager.getRingtone(context, Uri.parse(uri));
    }

    public void play() {
        ringtone.play();
    }

    public void stop() {
        ringtone.stop();
    }
}
