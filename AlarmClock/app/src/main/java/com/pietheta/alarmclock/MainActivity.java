package com.pietheta.alarmclock;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<AlarmItem> alarmList = new ArrayList<>();
    private RecyclerView recyclerAlarm;
    public static AlarmAdapter alarmAdapter;
    private RecyclerView.LayoutManager layoutManager;
    AlarmDbHelper helper = new AlarmDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        loadData();
        setList();
        refreshList();
        findViewById(R.id.fab_main).setOnClickListener(v -> startActivity(new Intent(this, AddAlarmActivity.class)));


    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageButton cancel = toolbar.findViewById(R.id.cancel);
        ImageButton delete = toolbar.findViewById(R.id.delete);
        ImageButton countdown = toolbar.findViewById(R.id.done);
        countdown.setImageResource(R.drawable.icon_countdown);//done icon converted to countdown
        title.setText(R.string.app_name);
        cancel.setVisibility(View.GONE);
        countdown.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        countdown.setOnClickListener(v -> startActivity(new Intent(this, CountDown.class)));
    }

    public void loadData() {
        Cursor cursor = helper.showAllData();
        alarmList.clear();

        while (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndex(AlarmDbHelper.KEY_TIME));
            String active = cursor.getString(cursor.getColumnIndex(AlarmDbHelper.KEY_ACTIVE));
            String mission = cursor.getString(cursor.getColumnIndex(AlarmDbHelper.KEY_MISSION));

            int mission_icon = getMissionIcon(mission);
            boolean isOn = active.equals("true");

            alarmList.add(new AlarmItem(time, mission_icon, isOn));
        }

    }

    private void setList() {
        recyclerAlarm = findViewById(R.id.alarmItemList);
        recyclerAlarm.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        alarmAdapter = new AlarmAdapter(this, alarmList);
        recyclerAlarm.setLayoutManager(layoutManager);
        recyclerAlarm.setAdapter(alarmAdapter);

        alarmAdapter.setOnItemClickListener(new AlarmAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), AddAlarmActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }

            @Override
            public void onClickSwitch(int position) {
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
                handler.postDelayed(this, 60000);
            }
        }, DateUtils.MINUTE_IN_MILLIS - System.currentTimeMillis() % DateUtils.MINUTE_IN_MILLIS);
    }

    private int getMissionIcon(String mission) {

        switch (mission) {
            case "math":
                return R.drawable.ic_delete_red;
            case "stringMatching":
                return R.drawable.icon_add;
            case "plugInCharger":
                return R.drawable.icon_done;
            default:
                return R.drawable.icon_alarm_clock;
        }
    }

}
