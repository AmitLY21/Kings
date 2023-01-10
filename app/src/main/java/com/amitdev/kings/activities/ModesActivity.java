package com.amitdev.kings.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.amitdev.kings.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import common.Constants;
import common.TinyDB;

public class ModesActivity extends AppCompatActivity {
    enum eMode {REG, PREMIUM}

    private MaterialButton btnBack;
    private MaterialButton btnStartRegKings;
    private MaterialButton btnStartPremiumKings;
    private MaterialButton btnAddCustomsMissions;
    private MaterialTextView lblPremium;
    private ArrayList<String> playerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modes);
        TinyDB tinyDB = new TinyDB(this);
        findViews();
        Intent i = getIntent();
        this.playerList = i.getStringArrayListExtra("playerList");
        disablePremiumPlan();

        //if user not premium grey out buttons for premium plan
        if (tinyDB.getBoolean(Constants.ULTIMATE) || tinyDB.getBoolean(Constants.TWIST)) {
            Log.d(Constants.TAG, "onCreate: enablePremiumPlan");
            enablePremiumPlan();
        }

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
            finish();
        });

        btnStartRegKings.setOnClickListener(view -> {
            moveToGameActivity(eMode.REG, btnStartRegKings.getText().toString());
        });

        btnStartPremiumKings.setOnClickListener(view -> {
            moveToGameActivity(eMode.PREMIUM, btnStartPremiumKings.getText().toString());
        });

        btnAddCustomsMissions.setOnClickListener(view -> {
            moveToCustomsActivity();
        });

    }

    private void enablePremiumPlan() {
        lblPremium.setText("Premium Plan");
        lblPremium.setTextSize(18);
        btnAddCustomsMissions.setAlpha(1f);
        btnAddCustomsMissions.setEnabled(true);
        btnStartPremiumKings.setAlpha(1f);
        btnStartPremiumKings.setEnabled(true);
    }

    private void disablePremiumPlan() {
        lblPremium.setText("No Premium Plan!\nYou can purchase at the Premium Section");
        lblPremium.setTextSize(14);
        btnAddCustomsMissions.setAlpha(.5f);
        btnAddCustomsMissions.setEnabled(false);
        btnStartPremiumKings.setAlpha(.5f);
        btnStartPremiumKings.setEnabled(false);
    }

    private void moveToCustomsActivity() {
        Intent i = new Intent(this, AddCustomsMissionActivity.class);
        startActivity(i);
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
        lblPremium = findViewById(R.id.lblPremium);
    }
}