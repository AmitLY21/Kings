package com.amitdev.kings.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.amitdev.kings.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ModesActivity extends AppCompatActivity {
    enum eMode {REG, PREMIUM}

    ;
    private MaterialButton btnBack;
    private MaterialButton btnStartRegKings;
    private MaterialButton btnStartPremiumKings;
    private MaterialButton btnAddCustomsMissions;
    private ArrayList<String> playerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modes);
        findViews();

        Intent i = getIntent();
        this.playerList = i.getStringArrayListExtra("playerList");

        btnBack.setOnClickListener(view -> {
            finish();
        });

        btnStartRegKings.setOnClickListener(view -> {
            moveToGameActivity(eMode.REG, btnStartRegKings.getText().toString());
        });

        btnStartPremiumKings.setOnClickListener(view -> {
            moveToGameActivity(eMode.PREMIUM, btnStartPremiumKings.getText().toString());
        });

        btnAddCustomsMissions.setOnClickListener(view -> {
            moveToCustomsActivity(eMode.PREMIUM);
        });

    }

    private void moveToCustomsActivity(eMode mode) {
        Intent i = new Intent(this, AddCustomsMissionActivity.class);
        i.putExtra("Mode", mode.name());
        startActivity(i);
        finish();
    }

    private void moveToGameActivity(eMode mode, String gameName) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("Mode", mode.name());
        i.putExtra("gameName", gameName);
        if (!playerList.isEmpty())
            i.putExtra("playerList", playerList);
        startActivity(i);
        finish();
    }

    private void findViews() {
        btnBack = findViewById(R.id.btnBack);
        btnStartRegKings = findViewById(R.id.btnStartRegKings);
        btnStartPremiumKings = findViewById(R.id.btnStartPremiumKings);
        btnAddCustomsMissions = findViewById(R.id.btnAddCustomsMissions);
    }
}