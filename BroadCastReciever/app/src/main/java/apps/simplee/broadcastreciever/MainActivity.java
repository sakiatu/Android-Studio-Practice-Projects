package apps.simplee.broadcastreciever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            boolean value = intent.getBooleanExtra("state", false);
            if (value)
                setIcon(R.drawable.airplane_on);
            else
                setIcon(R.drawable.airplane_off);


        }
    };

    private void setIcon(int img) {
        findViewById(R.id.imgId).setBackground(getDrawable(img));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFlightModeReceiver();
        findViewById(R.id.customButton).setOnClickListener(this::clicked);

    }

    private void clicked(View view) {
        if(view.getId()==R.id.customButton){
            sendBroadcast(new Intent("apps.simplee.action.CUSTOM_BROADCAST"));
        }
    }

    private void initFlightModeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(receiver, filter);
    }
}
