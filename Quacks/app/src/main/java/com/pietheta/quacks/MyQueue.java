package com.pietheta.quacks;

import java.util.ArrayList;

public class MyQueue extends Quack {
    ArrayList itemList, historyList;

    public MyQueue(ArrayList itemList, ArrayList historyList) {
        super(itemList, historyList);
        this.itemList = itemList;
        this.historyList = historyList;
    }

    @Override
    void push(String value) {
        itemList.add(value);
        historyList.add("\"" + value + "\"" + " pushed");

    }
}
