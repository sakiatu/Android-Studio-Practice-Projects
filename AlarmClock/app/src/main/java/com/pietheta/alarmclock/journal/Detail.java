package com.pietheta.alarmclock.journal;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pietheta.alarmclock.R;

public class Detail extends AppCompatActivity {
    long id;
    String titleOfNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();
        id = i.getLongExtra("ID",0);

        SimpleDatabase db = new SimpleDatabase(this);
        Note note = db.getNote(id);
        titleOfNote = note.getTitle();
        setToolbar();
        TextView details = findViewById(R.id.noteDesc);
        details.setText(note.getContent());
        details.setMovementMethod(new ScrollingMovementMethod());

        findViewById(R.id.fab).setOnClickListener(v->{
                SimpleDatabase database = new SimpleDatabase(getApplicationContext());
                database.deleteNote(id);
                Toast.makeText(getApplicationContext(),"Note Deleted",Toast.LENGTH_SHORT).show();
                gotoJournal();
        });
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageButton cancel = toolbar.findViewById(R.id.cancel);
        ImageButton delete = toolbar.findViewById(R.id.delete);
        ImageButton edit = toolbar.findViewById(R.id.done);
        edit.setImageResource(R.drawable.icon_edit);//done icon converted to edit
        title.setText(titleOfNote);
        cancel.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        edit.setOnClickListener(v -> {
            Intent i = new Intent(this,Edit.class);
            i.putExtra("ID",id);
            startActivity(i);
        });
        cancel.setOnClickListener(v->finish());
    }

    private void gotoJournal() {
        startActivity(new Intent(this,Journal.class));
    }
}
