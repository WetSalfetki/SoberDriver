package com.mirea.kt.ribo.soberdriver;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBManager {
    private final SQLiteOpenHelper sqLiteOpenHelper;

    public DBManager(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    public void insertRecord(double value, String time) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("value", value);
        values.put("time", time);
        db.insert("TABLE_RECORDS", null, values);
        db.close();
    }

    public ArrayList<Record> getAllRecords() {
        ArrayList<Record> records = new ArrayList<>();
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("TABLE_RECORDS",
                null, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                double value = cursor.getDouble(cursor.getColumnIndexOrThrow("value"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                records.add(new Record(value, time));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return records;
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        db.delete("TABLE_RECORDS", null, null);
        db.close();
    }
}