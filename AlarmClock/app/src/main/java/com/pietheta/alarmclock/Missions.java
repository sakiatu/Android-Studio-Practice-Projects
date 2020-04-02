package com.pietheta.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class Missions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions);

        AlarmDbHelper helper = new AlarmDbHelper(this);
        Cursor cursor = helper.showAllData();

        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()){
            builder.append(cursor.getString(0)+" ");
            builder.append(cursor.getString(1)+" ");
            builder.append(cursor.getString(2)+" ");
            builder.append(cursor.getString(3)+" ");
            builder.append(cursor.getString(4)+" ");
            builder.append(cursor.getString(5)+"\n");
        }
      TextView textView=  findViewById(R.id.text);

        textView.setText(builder.toString());
    }

}
