package com.pietheta.alarmclock.journal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pietheta.alarmclock.R;

import java.util.Calendar;

public class AddJournal extends AppCompatActivity {
    EditText journalTitle, journalDetails;
    Calendar calendar;

    Toolbar toolbar;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.toolbar_title);
        setToolbar();
        journalDetails = findViewById(R.id.journalDetails);
        journalTitle = findViewById(R.id.journalTitle);

        journalTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) title.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    private void setToolbar() {

        title.setText("New Note");
        ImageButton cancel = toolbar.findViewById(R.id.cancel);
        ImageButton delete = toolbar.findViewById(R.id.delete);
        ImageButton save = toolbar.findViewById(R.id.done);
        save.setImageResource(R.drawable.icon_save);//done icon converted to save
        cancel.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        save.setOnClickListener(v -> {
            if (journalTitle.getText().length() != 0) {
                String title = journalTitle.getText().toString();
                String details = journalDetails.getText().toString();
                Note note = new Note(title, details, getCurrentDate(), getCurrentTime());
                SimpleDatabase database = new SimpleDatabase(this);
                long id = database.addNote(note);
                Note check = database.getNote(id);
                Log.d("inserted", "Note: " + id + " -> Title:" + check.getTitle() + " Date: " + check.getDate());
                Toast.makeText(this, "Note Saved.", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                journalTitle.setError("Title required");
            }
        });
        cancel.setOnClickListener(v -> {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            finish();
        });
    }


    private String getCurrentTime() {

        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
        Integer minutes = calendar.get(Calendar.MINUTE);
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

    private String getCurrentDate() {
        String date;
        calendar = Calendar.getInstance();
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        if (day.length() == 1) day = "0" + day;
        if (month.length() == 1) month = "0" + month;

        date = day + "/" + month + "/" + year;
        return date;
    }
}
