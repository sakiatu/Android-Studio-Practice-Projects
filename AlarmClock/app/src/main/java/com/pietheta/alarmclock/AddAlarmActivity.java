package com.pietheta.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

import static com.pietheta.alarmclock.MainActivity.alarmAdapter;

public class AddAlarmActivity extends AppCompatActivity {
    public static ArrayList<AlarmItem> alarmList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Set Alarm");
        ImageButton cancel = toolbar.findViewById(R.id.cancel);
        ImageButton delete = toolbar.findViewById(R.id.delete);
        ImageButton done = toolbar.findViewById(R.id.done);
        cancel.setOnClickListener(this::onClick);
        done.setOnClickListener(this::onClick);
        delete.setVisibility(View.GONE);
    }

    private void onClick(View view) {
        if (view.getId()==R.id.done) {
            saveAlarm();
            finish();
        }

    }


    private void saveAlarm() {
        int position = alarmList.size();
        alarmList.add(new AlarmItem(getAlarmTime(),getImgResource(),true));
        alarmAdapter.notifyItemInserted(position);
        mt("Alarm at "+getAlarmTime());
    }

    private String getAlarmTime() {
        TimePicker timePicker = findViewById(R.id.timePicker);


        Integer hour = timePicker.getHour();
        Integer minutes = timePicker.getMinute();
        String ext = " AM";
        String hourString;
        String minuteString = minutes.toString();

        if (minutes < 10) minuteString = "0" + minuteString;

        if (hour >= 12) {
            ext = " PM";
            if (hour > 12) hour = hour - 12;
        } else if (hour == 0) hour = 12;

        hourString = Integer.toString(hour);

        if (hourString.length() == 1) hourString = "0" + hourString;

        return hourString + ":" + minuteString + ext;
    }

    private int getImgResource(){
        return R.drawable.icon_alarm_clock;
    }
    private boolean isSwitchOn(){
        return true;
    }
    private void mt(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
