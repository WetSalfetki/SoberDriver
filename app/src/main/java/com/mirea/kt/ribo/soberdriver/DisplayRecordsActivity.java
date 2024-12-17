package com.mirea.kt.ribo.soberdriver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DisplayRecordsActivity extends AppCompatActivity {

    private DBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_records);

        Toolbar tb = findViewById(R.id.tb);

        setSupportActionBar(tb);
        Log.d("toolbar", "set support action bar");

        dbManager = new DBManager(new MyAppSQLiteHelper(this, "records.db", null, 1));

        List<Record> recordList = dbManager.getAllRecords();
        Log.d("RecordsReceived", "Records received from database");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecordAdapter recordAdapter = new RecordAdapter(recordList);
        Log.d("adapter", "adapter created");
        recyclerView.setAdapter(recordAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Record> recordList = dbManager.getAllRecords();
        Log.d("RecordsReceived", "Records received from database");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Log.d("layoutManager", "vertical display");
        RecordAdapter recordAdapter = new RecordAdapter(recordList);
        Log.d("adapter", "adapter created");
        recyclerView.setAdapter(recordAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_display_records, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_record) {
            startActivity(new Intent(this, CalculationActivity.class));
            Log.d("startCalcActivity", "start CalculationActivity");
            return true;
        } else if (item.getItemId() == R.id.clear_records) {
            dbManager.deleteAllRecords();
            Log.d("RecordsDeleted", "Records deleted from database");
            onResume();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}