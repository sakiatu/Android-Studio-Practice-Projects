package com.pietheta.alarmclock.journal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pietheta.alarmclock.R;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Journal extends AppCompatActivity {
    RecyclerView recyclerView;
    public static Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView secondaryText;
    SimpleDatabase simpleDatabase;
    List<Note> allNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        setToolbar();
        secondaryText = findViewById(R.id.secondaryText);
        simpleDatabase = new SimpleDatabase(this);
        allNotes = simpleDatabase.getAllNotes();
        displayList(allNotes);
        if (!allNotes.isEmpty()) {
            secondaryText.setVisibility(View.GONE);
        }
    }

    private void setToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageButton cancel = toolbar.findViewById(R.id.cancel);
        ImageButton delete = toolbar.findViewById(R.id.delete);
        ImageButton add = toolbar.findViewById(R.id.done);
        title.setText(R.string.journalTitle);
        cancel.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        add.setVisibility(View.VISIBLE);
        add.setImageResource(R.drawable.icon_add);//done icon converted to add
        add.setOnClickListener(this::onClicked);
        cancel.setOnClickListener(v -> finish());
    }

    private void onClicked(View view) {
        if (view.getId() == R.id.done) {
            Toast.makeText(this, "Add New Note", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AddJournal.class));
        }
    }

    private void displayList(List<Note> allNotes) {
        recyclerView = findViewById(R.id.allNotesList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new Adapter(this, allNotes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Note> getAllNotes = simpleDatabase.getAllNotes();
        if (!getAllNotes.isEmpty()) {
            secondaryText.setVisibility(View.GONE);
            displayList(getAllNotes);
        }
    }
}