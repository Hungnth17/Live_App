package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    Button live_btn;
    TextInputEditText liveIdInput, nameInput;
    String liveId, name,userId;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sharedPreferences =getSharedPreferences("name_pref", MODE_PRIVATE);

        live_btn = findViewById(R.id.live_btn);
        liveIdInput = findViewById(R.id.live_id_input);
        nameInput = findViewById(R.id.live_name_input);

        nameInput.setText(sharedPreferences.getString("name",""));

        liveIdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                liveId = liveIdInput.getText().toString();
                if(liveId.length()==0){
                    live_btn.setText("Start new live");
                }else{
                    live_btn.setText("Join a live");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        live_btn.setOnClickListener(v -> {
            name = nameInput.getText().toString();
            if (name.isEmpty()){
                nameInput.setError("Name is required");
                nameInput.requestFocus();
                return;
            }

            liveId = liveIdInput.getText().toString();

            if (liveId.length() > 0 && liveId.length() != 5) {
                liveIdInput.setError("Invalid Live Id");
                liveIdInput.requestFocus();
                return;
            }

            startMetting();
        });
    }

    void  startMetting(){

        sharedPreferences.edit().putString("name", name).apply();
        //Log.i("LOG","Start metting");

        boolean isHost = true;
        if(liveId.length()==5)
            isHost = false;
        else
            liveId = generateLiveId();

        userId = UUID.randomUUID().toString();

        Intent intent = new Intent( MainActivity.this, LiveActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("name", name);
        intent.putExtra("live_id", liveId);
        intent.putExtra("host", isHost);
        startActivity(intent);
    }

    String generateLiveId(){
        StringBuilder id = new StringBuilder();
        while ( id.length()!=5){
            int random = new Random().nextInt(10);
            id.append(random);
        }
        return id.toString();
    }
}