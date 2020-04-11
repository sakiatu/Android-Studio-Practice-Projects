package com.pietheta.quacks;

import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MyStack extends Quack {
    ArrayList itemList, historyList;

    public MyStack(ArrayList itemList, ArrayList historyList) {
        super(itemList, historyList);
        this.itemList = itemList;
        this.historyList = historyList;
    }

    @Override
    void push(String value) {
        itemList.add(0, value);
        historyList.add("\"" + value + "\"" + " pushed");

    }
}
