package com.mirea.kt.ribo.soberdriver;

public class Record {
    public double result;
    public String time;

    public Record(double result, String time) {
        this.result = result;
        this.time = time;
    }

    public double getResult() {
        return result;
    }

    public String getTime() {
        return time;
    }
}