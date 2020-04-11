package com.pietheta.quacks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int STACK = 0;
    private static final int QUEUE = 1;
    ListView itemList, historyList;
    ArrayList items, history;
    String[] quackItems= {"Stack","Queue"};
    EditText editText;
    ArrayAdapter mainAdapter, historyAdapter;
    Spinner quackSpinner;
    Quack quack;
    ArrayAdapter<CharSequence> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<String>();
        history = new ArrayList<String>();
        setViews();
        setLists();
        setSpinner();
    }

    private void setSpinner() {
        quackSpinner = findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<CharSequence>(this,R.layout.spinner_layout,R.id.item_text,quackItems);
       // spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      //  spinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        // Associate the ArrayAdapter with the Spinner.
        quackSpinner.setAdapter(spinnerAdapter);
        // Set the default selection of the quackSpinner to be "add".
        quackSpinner.setSelection(spinnerAdapter.getPosition("Stack"));
        quackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setQuack(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setQuack(STACK);

            }
        });
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
        editText = findViewById(R.id.value);
        findViewById(R.id.push).setOnClickListener(this::onClick);
        findViewById(R.id.pop).setOnClickListener(this::onClick);
        findViewById(R.id.peek).setOnClickListener(this::onClick);
        findViewById(R.id.empty).setOnClickListener(this::onClick);
        findViewById(R.id.size).setOnClickListener(this::onClick);
        findViewById(R.id.clear).setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        if (view.getId() == R.id.push) {
            String string = getEditTextValue();
            if (string.equals("")) {
                msg("Input text!");
            } else {
                quack.push(string);
                editText.setText("");
                itemList.setTranscriptMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
            }
        }
        if (view.getId() == R.id.pop) {
            if (mainAdapter.getCount() != 0) {
                quack.pop();
                refreshList();
            } else
                msg("Quack is empty");
        }
        if (view.getId() == R.id.peek) {
            if (quack.peek()) refreshList();
            else msg("Quack is empty\nNo peek editText");
        }
        if (view.getId() == R.id.empty) {
            quack.emptyCheck();
        }
        if (view.getId() == R.id.size) {
            quack.showSize();
        }
        if (view.getId() == R.id.clear) {
            if (historyAdapter.getCount() != 0) {
                quack.clear();
                msg("Quack and history have been cleared");
            } else
                msg("Nothing to clear");
        }
        refreshList();
    }

    private void refreshList() {
        mainAdapter.notifyDataSetChanged();
        historyAdapter.notifyDataSetChanged();
    }

    private <M> void msg(M msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }

    private String getEditTextValue() {
        return editText.getText().toString();
    }

    void setQuack(int QUACK) {
        if (QUACK == STACK)
            quack = new MyStack(items, history);
        else if (QUACK == QUEUE)
            quack = new MyQueue(items, history);
    }
}