package com.mirea.kt.ribo.soberdriver;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("startApp", "app started");

        EditText etLogin = findViewById(R.id.etLogin);
        EditText etPassword = findViewById(R.id.etPassword);
        Button button = findViewById(R.id.btStart);

        button.setOnClickListener(v -> {
            Log.d("buttonTouch", "button touch");
            String lgn = etLogin.getText().toString();
            String pwd = etPassword.getText().toString();
            if (!lgn.isEmpty() && !pwd.isEmpty()) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("lgn", lgn);
                hashMap.put("pwd", pwd);
                hashMap.put("g", "RIBO-04-22");
                HTTPRunnable httpRunnable = new HTTPRunnable("https://android-for-students.ru/coursework/login.php", hashMap);
                Thread th = new Thread(httpRunnable);
                th.start();
                try {
                    th.join();
                    Log.d("postRequest", "post request sent");
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                } finally {
                    try {
                        JSONObject jsonObject = new JSONObject(httpRunnable.getResponseBody());
                        Log.d("jsonReceived", "json received");
                        int result = jsonObject.getInt("result_code");
                        if (result == 1) {
                            Log.d("studentIsExist", "student exist");
                            StudentInformationDialog dialog = new StudentInformationDialog();
                            Bundle bundle = new Bundle();
                            bundle.putString("title", jsonObject.getString("title"));
                            Log.d("valueAdded", "value added title");
                            bundle.putString("task", jsonObject.getString("task"));
                            Log.d("valueAdded", "value added task");
                            dialog.setArguments(bundle);
                            dialog.show(getSupportFragmentManager(), "dialog");
                        } else if (result == -1) {
                            Toast.makeText(getApplicationContext(), R.string.incorrect_data,
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | NullPointerException exception) {
                        Toast.makeText(getApplicationContext(), R.string.the_server_is_not_responding,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.fill_fields,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}