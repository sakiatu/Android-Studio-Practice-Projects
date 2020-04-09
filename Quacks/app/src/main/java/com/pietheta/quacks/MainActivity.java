package com.pietheta.quacks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView itemList, historyList;
    ArrayList items, history;
    EditText value;
    ArrayAdapter mainAdapter, historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<String>();
        history = new ArrayList<String>();
        setViews();
        setLists();


    }

    private void setLists() {
        mainAdapter = new ArrayAdapter<String>(this, R.layout.main_list_item, R.id.main_item_text, items);
        historyAdapter = new ArrayAdapter<String>(this, R.layout.history_list_item, R.id.history_item_text, history);
        itemList.setAdapter(mainAdapter);
        historyList.setAdapter(historyAdapter);
    }

    private void setViews() {
        itemList = findViewById(R.id.mainList);
        historyList = findViewById(R.id.historyList);
        value = findViewById(R.id.value);
        findViewById(R.id.push).setOnClickListener(this::onClick);
        findViewById(R.id.pop).setOnClickListener(this::onClick);
        findViewById(R.id.peek).setOnClickListener(this::onClick);
        findViewById(R.id.empty).setOnClickListener(this::onClick);
        findViewById(R.id.size).setOnClickListener(this::onClick);
        findViewById(R.id.clear).setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        if (view.getId() == R.id.push) push();
        if (view.getId() == R.id.pop) pop();
        if (view.getId() == R.id.peek) peek();
        if (view.getId() == R.id.empty) emptyCheck();
        if (view.getId() == R.id.size) showSize();
        if (view.getId() == R.id.clear) clear();
    }

    private void showSize() {
        history.add("size: " + mainAdapter.getCount());
        historyAdapter.notifyDataSetChanged();

    }

    private void emptyCheck() {
        if (mainAdapter.getCount() != 0) {

            history.add("Stack is not empty");
            historyAdapter.notifyDataSetChanged();
        } else {
            history.add("Stack is empty");
            historyAdapter.notifyDataSetChanged();
        }
    }

    private void peek() {
        if (mainAdapter.getCount() != 0) {

            history.add("peek: " + items.get(0));
            historyAdapter.notifyDataSetChanged();
        } else
            msg("Stack is empty\nNo peek value");
    }

    private void push() {
        String string = value.getText().toString();
        if (!string.equals("")) {
            items.add(string);
            history.add("\"" + string + "\"" + " pushed");
            itemList.setTranscriptMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
            mainAdapter.notifyDataSetChanged();
            historyAdapter.notifyDataSetChanged();
            value.setText("");
        } else msg("value required");
    }

    private void pop() {
        if (mainAdapter.getCount() != 0) {
            history.add("\"" + items.get(0) + "\"" + " popped");
            items.remove(0);
            mainAdapter.notifyDataSetChanged();
            historyAdapter.notifyDataSetChanged();
        } else
            msg("Stack is empty");
    }


    private void clear() {
        if (historyAdapter.getCount() != 0) {
            history.clear();
            items.clear();
            mainAdapter.notifyDataSetChanged();
            historyAdapter.notifyDataSetChanged();
            msg("Stack and history have been cleared");
        } else
            msg("Nothing to clear");
    }

    private <M> void msg(M msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }
}