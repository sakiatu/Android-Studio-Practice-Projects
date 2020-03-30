package com.pietheta.recyclerview;

public class ExampleItem {

    private int imageResource;
    private String line1,line2;

    public ExampleItem(int imageResource, String line1, String line2) {
        this.imageResource = imageResource;
        this.line1 = line1;
        this.line2 = line2;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public void changeLine1(String string){
        line1=string;
    }
}
