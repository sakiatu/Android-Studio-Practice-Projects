package com.pietheta.quacks;

import java.util.ArrayList;

public abstract class Quack {

    private ArrayList itemList, historyList;

    public Quack(ArrayList itemList, ArrayList historyList) {
        this.itemList = itemList;
        this.historyList = historyList;
    }

    abstract void push(String value);

    void pop() {
        itemList.remove(0);
        historyList.add("\"" + itemList.get(0) + "\"" + " popped");
    }

    boolean peek() {
        if (!itemList.isEmpty()) {
            historyList.add("peek: " + itemList.get(0));
            return true;
        }
        return false;
    }

    void emptyCheck() {
        historyList.add(itemList.isEmpty() ? "Quack is empty" : "Quack is not empty");
    }

    void showSize() {
        historyList.add("size: " + itemList.size());
    }

    void clear() {
        historyList.clear();
        itemList.clear();
    }
}
