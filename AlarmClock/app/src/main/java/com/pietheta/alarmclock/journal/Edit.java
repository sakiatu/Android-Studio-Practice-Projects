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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pietheta.alarmclock.R;

import java.util.Calendar;

public class Edit extends AppCompatActivity {
    Toolbar toolbar;
    EditText nTitle, nContent;
    Calendar calendar;
    String titleOfNote;
    long nId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        setToolbar();
        Intent i = getIntent();
        nId = i.getLongExtra("ID", 0);
        SimpleDatabase db = new SimpleDatabase(this);
        Note note = db.getNote(nId);
        titleOfNote = note.getTitle();
        title.setText(titleOfNote);

        String content = note.getContent();
        nTitle = findViewById(R.id.journalTitle);
        nTitle.setText(titleOfNote);
        nContent = findViewById(R.id.journalDetails);
        nTitle.addTextChangedListener(new TextWatcher() {
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

        nContent.setText(content);

    }


    private void setToolbar() {
        ImageButton cancel = toolbar.findViewById(R.id.cancel);
        ImageButton delete = toolbar.findViewById(R.id.delete);
        ImageButton save = toolbar.findViewById(R.id.done);
        save.setImageResource(R.drawable.icon_save);//done icon converted to save
        cancel.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        save.setOnClickListener(v -> {
            String title = nTitle.getText().toString();
            String content = nContent.getText().toString();
            if(title.equals("")){
                nTitle.setError("Title required");
            }else{

                Note note = new Note(nId, title, content, getCurrentDate(), getCurrentTime());
                SimpleDatabase sDB = new SimpleDatabase(getApplicationContext());
                long id = sDB.editNote(note);
                gotoJournal();
                Toast.makeText(this, "Note Edited", Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(v -> {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });

    }

    private void gotoJournal() {
        startActivity(new Intent(this, Journal.class));
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
