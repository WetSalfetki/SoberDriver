package com.mirea.kt.ribo.soberdriver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CalculationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        Toolbar tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();

        Log.d("toolbar", "set support action bar");

        if (ab != null) {
            ab.setHomeButtonEnabled(true);
            Log.d("toolbarHome", "set home button enabled");
            ab.setDisplayHomeAsUpEnabled(true);
            Log.d("toolbarHome", "set display home button enabled");
        }

        EditText et_weight = findViewById(R.id.weight);
        EditText et_height = findViewById(R.id.height);
        EditText et_alcoholStrength = findViewById(R.id.alcoholStrength);
        EditText et_alcoholVolume = findViewById(R.id.alcoholVolume);
        EditText et_time = findViewById(R.id.time);

        RadioGroup genderGroup = findViewById(R.id.genderGroup);

        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("buttonTouch", "button touch");
                if (!et_weight.getText().toString().isEmpty()
                        && !et_height.getText().toString().isEmpty()
                        && !et_alcoholStrength.getText().toString().isEmpty()
                        && !et_alcoholVolume.getText().toString().isEmpty()
                        && !et_time.getText().toString().isEmpty()) {
                    double weight = Double.parseDouble(et_weight.getText().toString());
                    double height = Double.parseDouble(et_height.getText().toString());
                    double alcoholStrength = Double.parseDouble(et_alcoholStrength.getText().toString());
                    double alcoholVolume = Double.parseDouble(et_alcoholVolume.getText().toString());
                    int time = Integer.parseInt(et_time.getText().toString());
                    if (weight > 0 && height > 0 && alcoholStrength >= 0 && alcoholVolume >= 0 && time >= 0) {
                        int radioId = genderGroup.getCheckedRadioButtonId();
                        RadioButton radioButton = findViewById(radioId);
                        String gender = radioButton.getText().toString();

                        double result = calculateConcentration(weight, alcoholVolume, alcoholStrength, time, gender);
                        Log.d("calculate", "BAC value calculated");

                        Intent intent = new Intent(CalculationActivity.this, ResultActivity.class);
                        intent.putExtra("data", result);
                        Log.d("valueAdded", "value added data");
                        startActivity(intent);
                        Log.d("startNewActivity", "go to ResultActivity");
                    } else {
                        Toast.makeText(CalculationActivity.this, R.string.incorrect_data, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(CalculationActivity.this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public double calculateConcentration(double weight, double alcoholVolume, double alcoholStrength, int time, String gender) {
        double genderCoefficient;
        if (gender.equals("Мужской")) {
            genderCoefficient = 0.68;
        } else {
            genderCoefficient = 0.55;
        }
        double result = ((((alcoholVolume * alcoholStrength * 0.1 * 0.8 * 0.806) /
                (weight * genderCoefficient)) - (0.015 * time)) / 100.0);
        if (result < 0) {
            result = 0.0;
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Log.d("buttonHomeTouch", "button home touch");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}