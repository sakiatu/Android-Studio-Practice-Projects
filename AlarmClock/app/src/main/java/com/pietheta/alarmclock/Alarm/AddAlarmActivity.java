package com.pietheta.alarmclock.Alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pietheta.alarmclock.BottomSheetDialog;
import com.pietheta.alarmclock.R;

import static com.pietheta.alarmclock.MainActivity.alarmAdapter;
import static com.pietheta.alarmclock.MainActivity.alarmList;

public class AddAlarmActivity extends AppCompatActivity implements BottomSheetDialog.OnClickListener {

    AlarmDbHelper helper = new AlarmDbHelper(this);
    public static int position;
    public static final int NEW_POSITION = -1;

    TextView mission_type, repeat_type, ringtone;
    ImageView missionIcon;
    BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", NEW_POSITION);
        repeat_type = findViewById(R.id.repeatValue_text);
        mission_type = findViewById(R.id.missionValue_text);
        ringtone = findViewById(R.id.ringtoneValue_text);
        missionIcon = findViewById(R.id.missionValue_image);

        setToolbar();
        setCurrentData();
        //buttons
        findViewById(R.id.setMission_layout).setOnClickListener(this::onClick);
        findViewById(R.id.setRingtone_layout).setOnClickListener(this::onClick);
        findViewById(R.id.setRepeat_layout).setOnClickListener(this::onClick);
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
        if (position == NEW_POSITION) {
            delete.setVisibility(View.GONE);
        } else {
            title.setText("Edit Alarm");
            delete.setOnClickListener(this::onClick);
        }
    }

    private void setCurrentData() {
        if (position != NEW_POSITION) {
            setCurrentTime();
            repeat_type.setText(helper.getRepeatType(position));
            mission_type.setText(helper.getMissionType(position));
            ringtone.setText(helper.getRingtone(position));

            int mission_icon = alarmList.get(position).getImageResource();
            missionIcon.setImageResource(mission_icon);
            missionIcon.setColorFilter(this.getResources().getColor(R.color.colorAccent));

        }
    }

    private void setCurrentTime() {
        if (position != NEW_POSITION) {
            TimePicker timePicker = findViewById(R.id.timePicker);

            timePicker.setHour(alarmList.get(position).getHour());
            timePicker.setMinute(alarmList.get(position).getMinute());

        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.done) {
            saveAlarm();
            finish();
        }
        if (view.getId() == R.id.cancel) {
            onBackPressed();
        }
        if (view.getId() == R.id.delete) {
            showAlert();
        }
        if (view.getId() == R.id.setMission_layout) {
            startActivity(new Intent(this, Missions.class));
            // mt("set mission");
        }
        if (view.getId() == R.id.setRingtone_layout) {
            mt("set ringtone");

        }
        if (view.getId() == R.id.setRepeat_layout) {
            showBottomSheet();
        }
    }

    private void showBottomSheet() {

        dialog = new BottomSheetDialog();
        dialog.show(getSupportFragmentManager(), "repeatTypes");

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.repeat_bottom_sheet_dialog, null);
        TextView once = view.findViewById(R.id.once_repeat);
        TextView everyday = view.findViewById(R.id.everyday_repeat);
        String type = repeat_type.getText().toString();

       /*
        switch (type) {
            case "Once":
                once.setTextColor(getResources().getColor(R.color.colorAccent));
                mt("aaa");
                break;
            case "Everyday":
                everyday.setTextColor(getResources().getColor(R.color.colorAccent));
                mt("bb");
                break;
        }
*/
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        builder.setView(layout);
        AlertDialog dialog =  builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        TextView title = layout.findViewById(R.id.dialogTitle);
        TextView msg = layout.findViewById(R.id.dialogMessage);
        title.setText("Delete");
        msg.setText("Delete Alarm?");

        Button delete = layout.findViewById(R.id.discard);
        Button cancel = layout.findViewById(R.id.cancel);
        delete.setText("DELETE");
        delete.setOnClickListener(v -> {
           helper.deleteAlarm(position);
           alarmList.remove(position);
           alarmAdapter.notifyDataSetChanged();
           finish();
       });
        cancel.setOnClickListener(v -> dialog.dismiss());
    }

    private void saveAlarm() {
        if (position == NEW_POSITION) {

            String alarmTime = getAlarmTime();
            String missionType = mission_type.getText().toString();
            String ringtone = "ringtone";
            String repeatType = repeat_type.getText().toString();
            String activeState = "true";
            int missionIcon = getImgResource();

            helper.addAlarm(alarmTime, missionType, ringtone, repeatType, activeState);
            alarmList.add(new AlarmItem(alarmTime, missionIcon, true));
        } else {
            String alarmTime = getAlarmTime();
            //String missionType = helper.getMissionType(position);
            // String repeatType = helper.getRepeatType(position);
            String missionType = mission_type.getText().toString();
            String ringtone = helper.getRingtone(position);
            String repeatType = repeat_type.getText().toString();
            String activeState = "true";//update makes it true

            helper.updateAlarm(position, alarmTime, missionType, ringtone, repeatType, activeState);
            alarmList.remove(position);
            alarmList.add(position, new AlarmItem(getAlarmTime(), getImgResource(), true));
        }
        alarmAdapter.notifyDataSetChanged();

        mt("Alarm at " + getAlarmTime());
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

    private int getImgResource() {
        return R.drawable.icon_alarm_clock;
    }

    private void mt(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClicked(View view) {

        if (view.getId() == R.id.once_repeat) {
            repeat_type.setText("Once");
           // helper.updateRepeat(position, "Once");
            dialog.dismiss();
        }
        if (view.getId() == R.id.everyday_repeat) {
            repeat_type.setText("Everyday");
          //  helper.updateRepeat(position, "Everyday");
            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        showOnBackDialog(layout);
        layout.findViewById(R.id.discard).setOnClickListener(v -> super.onBackPressed());
    }

    private void showOnBackDialog(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        TextView title = view.findViewById(R.id.dialogTitle);
        TextView message = view.findViewById(R.id.dialogMessage);
        title.setText("Discard");
        message.setText("Discard changes?");

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
        view.findViewById(R.id.cancel).setOnClickListener(v -> alertDialog.dismiss());
    }
}