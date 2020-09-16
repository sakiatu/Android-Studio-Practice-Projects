package com.pietheta.alarmclock;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pietheta.alarmclock.Alarm.AddAlarmActivity;
import com.pietheta.alarmclock.Alarm.AlarmAdapter;
import com.pietheta.alarmclock.Alarm.AlarmDbHelper;
import com.pietheta.alarmclock.Alarm.AlarmItem;
import com.pietheta.alarmclock.Alarm.Ringtone;
import com.pietheta.alarmclock.Alarm.TimeFormatter;
import com.pietheta.alarmclock.countdown.CountDown;
import com.pietheta.alarmclock.journal.Journal;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<AlarmItem> alarmList = new ArrayList<>();
    public static AlarmAdapter alarmAdapter;
    private RecyclerView.LayoutManager layoutManager;
    AlarmDbHelper helper = new AlarmDbHelper(this);
    Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        loadData();
        setList();
        refreshList();
        findViewById(R.id.fab_main)
                .setOnClickListener(v -> startActivity(new Intent(this, AddAlarmActivity.class)));

        ringtone = new Ringtone(this);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageButton cancel = toolbar.findViewById(R.id.cancel);
        ImageButton journal = toolbar.findViewById(R.id.delete);
        ImageButton countdown = toolbar.findViewById(R.id.done);
        countdown.setImageResource(R.drawable.icon_countdown);//done icon converted to countdown
        journal.setImageResource(R.drawable.icon_journal);//delete icon converted to journal
        title.setText(R.string.app_name);
        cancel.setVisibility(View.GONE);
        countdown.setVisibility(View.VISIBLE);
        journal.setVisibility(View.VISIBLE);
        countdown.setOnClickListener(v -> startActivity(new Intent(this, CountDown.class)));
        journal.setOnClickListener(v -> startActivity(new Intent(this, Journal.class)));
    }

    public void loadData() {
        Cursor cursor = helper.showAllData();
        alarmList.clear();

        while (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndex(AlarmDbHelper.KEY_TIME));
            String date = cursor.getString(cursor.getColumnIndex(AlarmDbHelper.KEY_DATE));
            String active = cursor.getString(cursor.getColumnIndex(AlarmDbHelper.KEY_ACTIVE));

            boolean isOn = active.equals("true");

            alarmList.add(new AlarmItem(time, date, isOn));
        }

    }

    private void setList() {
        RecyclerView recyclerAlarm = findViewById(R.id.alarmItemList);
        recyclerAlarm.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        alarmAdapter = new AlarmAdapter(this, alarmList);
        recyclerAlarm.setLayoutManager(layoutManager);
        recyclerAlarm.setAdapter(alarmAdapter);

        alarmAdapter.setOnItemClickListener(new AlarmAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }

            @Override
            public void setOnSwitchClick(int position) {
                boolean isOn = alarmList.get(position).isSwitchOn();
                alarmList.get(position).setSwitchState(isOn);
                helper.updateActive(position, !isOn);
                alarmAdapter.notifyItemChanged(position);
            }
        });
    }

    private void refreshList() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alarmAdapter.notifyDataSetChanged();
                for (int i = 0; i < alarmList.size(); i++) {
                    if (alarmList.get(i).isSwitchOn() &&
                            alarmList.get(i).getTime().equals(TimeFormatter.getCurrentTime()) &&
                            alarmList.get(i).getDate().equals(TimeFormatter.getCurrentDate())) {
                        ringtone.setRingtone(helper.getRingtoneUri(i));
                        ringtone.play();
                        alarmList.get(i).setSwitchState(true);
                        helper.updateActive(i, false);
                        alarmAdapter.notifyItemChanged(i);
                        showAlarmDialog(i);
                        break;
                    }
                }
                handler.postDelayed(this, 60000);
            }
        }, DateUtils.MINUTE_IN_MILLIS - System.currentTimeMillis() % DateUtils.MINUTE_IN_MILLIS);
    }

    private void showAlarmDialog(int i) {
        String time = alarmList.get(i).getTime();
        String label = helper.getLabel(i);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(time)
                .setMessage(label)
                .setPositiveButton("Stop", (a, b) -> ringtone.stop())
                .setCancelable(false).create().show();
    }
}
