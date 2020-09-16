package com.pietheta.alarmclock.Alarm;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pietheta.alarmclock.BottomSheetDialog;
import com.pietheta.alarmclock.R;

import java.util.Calendar;

import static com.pietheta.alarmclock.MainActivity.alarmAdapter;
import static com.pietheta.alarmclock.MainActivity.alarmList;

public class AddAlarmActivity extends AppCompatActivity implements BottomSheetDialog.OnClickListener {

    AlarmDbHelper helper = new AlarmDbHelper(this);
    public static int position;
    public static final int NEW_POSITION = -1;

    TextView date_of_alarm, ringtone_tv;
    BottomSheetDialog dialog;
    String ringtoneUri, ringtone;
    EditText labelEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", NEW_POSITION);
        date_of_alarm = findViewById(R.id.repeatValue_text);
        ringtone_tv = findViewById(R.id.ringtoneValue_text);
        labelEdt = findViewById(R.id.labelValue_edt);

        setToolbar();
        setCurrentData();
        //buttons
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
            date_of_alarm.setText(helper.getRepeatType(position));
            ringtone_tv.setText(helper.getRingtoneName(position));
            labelEdt.setText(helper.getLabel(position));
        } else {
            date_of_alarm.setText(TimeFormatter.getCurrentDate());
            ringtone_tv.setText("Default");
            ringtone = "Default";
            ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this.getApplicationContext(), RingtoneManager.TYPE_RINGTONE).toString();
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
        if (view.getId() == R.id.setRingtone_layout) {
            showSelectRingtoneDialog();
        }
        if (view.getId() == R.id.setRepeat_layout) {
            showDatePicker();
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DATE);
        DatePickerDialog dialog = new DatePickerDialog(this, ((view, year, month, dayOfMonth) -> {
            date_of_alarm.setText(TimeFormatter.formatDate(dayOfMonth, month, year));
        })
                , mYear, mMonth, mDay);

        dialog.show();
    }

    private void showSelectRingtoneDialog() {
        RingtoneDialog dialog = new RingtoneDialog();
        dialog.show(getSupportFragmentManager(), RingtoneDialog.RINGTONE_DIALOG);
        dialog.setOnRingtoneClickListener((name, uri) -> {
            ringtone_tv.setText(name);
            ringtone = name;
            ringtoneUri = uri;
        });
    }

    private void showBottomSheet() {

        dialog = new BottomSheetDialog();
        dialog.show(getSupportFragmentManager(), "repeatTypes");
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        builder.setView(layout);
        AlertDialog dialog = builder.create();
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
            String repeatType = date_of_alarm.getText().toString();
            String activeState = "true";

            helper.addAlarm(alarmTime, ringtone, ringtoneUri, repeatType, activeState, labelEdt.getText().toString());
            alarmList.add(new AlarmItem(alarmTime, date_of_alarm.getText().toString(), true));
        } else {
            String alarmTime = getAlarmTime();
            String repeatType = date_of_alarm.getText().toString();
            String activeState = "true";//update makes it true

            helper.updateAlarm(position, alarmTime, ringtone, ringtoneUri, repeatType, activeState, labelEdt.getText().toString());
            alarmList.remove(position);
            alarmList.add(position, new AlarmItem(getAlarmTime(), date_of_alarm.getText().toString(), true));
        }
        alarmAdapter.notifyDataSetChanged();

        showToast("Alarm at " + getAlarmTime());
    }

    private String getAlarmTime() {
        TimePicker timePicker = findViewById(R.id.timePicker);
        return TimeFormatter.formatTime(timePicker.getHour(), timePicker.getMinute());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClicked(View view) {

        if (view.getId() == R.id.once_repeat) {
            date_of_alarm.setText("Once");
            // helper.updateRepeat(position, "Once");
            dialog.dismiss();
        }
        if (view.getId() == R.id.everyday_repeat) {
            date_of_alarm.setText("Everyday");
            //  helper.updateRepeat(position, "Everyday");
            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        View layout = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        showOnBackDialog(layout);
        layout.findViewById(R.id.discard).setOnClickListener(v -> finish());
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