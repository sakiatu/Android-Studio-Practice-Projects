package apps.simplee.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("ABCD",""+ intent.getAction());
    }
}

//these lines have been added in manifest
/*
<intent-filter>
<action android:name="android.intent.action.AIRPLANE_MODE"/>
<action android:name="apps.simplee.action.CUSTOM_BROADCAST" />

</intent-filter>*/
