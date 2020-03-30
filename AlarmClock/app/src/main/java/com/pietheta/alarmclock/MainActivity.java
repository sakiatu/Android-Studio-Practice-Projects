package com.pietheta.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import static com.pietheta.alarmclock.AddAlarmActivity.alarmList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerAlarm;
    public static AlarmAdapter alarmAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        setList();

        findViewById(R.id.fab_main).setOnClickListener(v -> startActivity(new Intent(this, AddAlarmActivity.class)));


    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageButton cancel = toolbar.findViewById(R.id.cancel);
        ImageButton delete = toolbar.findViewById(R.id.delete);
        ImageButton done = toolbar.findViewById(R.id.done);
        title.setText(R.string.app_name);
        cancel.setVisibility(View.GONE);
        done.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
    }

    private void setList() {
        recyclerAlarm = findViewById(R.id.alarmItemList);
        recyclerAlarm.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        alarmAdapter = new AlarmAdapter(alarmList);
        recyclerAlarm.setLayoutManager(layoutManager);
        recyclerAlarm.setAdapter(alarmAdapter);

        alarmAdapter.setOnItemClickListener(new AlarmAdapter.OnItemclickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getApplicationContext(), AddAlarmActivity.class));
            }

            @Override
            public void isSwitchOn(int position) {
                alarmList.get(position).setSwitchState();

                alarmAdapter.notifyItemChanged(position);
            }
        });


    }
}
