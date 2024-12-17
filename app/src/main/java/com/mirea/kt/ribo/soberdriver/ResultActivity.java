package com.mirea.kt.ribo.soberdriver;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        DBManager dbManager = new DBManager(new MyAppSQLiteHelper(getApplicationContext(), "records.db", null, 1));

        TextView title = findViewById(R.id.title);
        TextView result = findViewById(R.id.result);
        ImageView imageView = findViewById(R.id.imageView);

        DecimalFormat format = new DecimalFormat("#.###");

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        double calculation_result = bundle.getDouble("data");

        String message = title.getText().toString() + " " + format.format(calculation_result);
        title.setText(message);

        if (calculation_result < 0.05) {
            result.setText(R.string.good_result);
            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.green_back));
            result.setTextColor(getResources().getColor(R.color.green));
            imageView.setImageResource(R.drawable.shield_check);
        } else if (calculation_result >= 0.05 && calculation_result < 0.3) {
            result.setText(R.string.medium_result);
            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.orange_back));
            result.setTextColor(getResources().getColor(R.color.orange));
            imageView.setImageResource(R.drawable.shield_warning);
        } else {
            result.setText(R.string.bad_result);
            findViewById(R.id.main).setBackgroundColor(getResources().getColor(R.color.red_back));
            result.setTextColor(getResources().getColor(R.color.red));
            imageView.setImageResource(R.drawable.shield_error);
        }

        Format dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        dbManager.insertRecord(calculation_result, dateFormat.format(Calendar.getInstance().getTime()));

        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DisplayRecordsActivity.class));
                finish();
            }
        });
    }
}