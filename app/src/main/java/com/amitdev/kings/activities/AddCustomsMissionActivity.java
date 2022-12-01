package com.amitdev.kings.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amitdev.kings.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import GameLogic.CustomCards;
import common.Constants;

public class AddCustomsMissionActivity extends AppCompatActivity {
    private MaterialButton btnAddMission;
    private MaterialButton btnBackMissions;
    private MaterialButton btnDeleteAllMissions;
    private TextInputLayout edtMissionDescription;
    private CustomCards customCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_customs_mission);
        customCards = new CustomCards(this);

        setVarsFromIntent();
        findViews();

        btnBackMissions.setOnClickListener(view -> {
            finish();
        });
        btnAddMission.setOnClickListener(view -> {
            if (getMissionDescription().equals(Constants.FAIL)) {
                Toast.makeText(view.getContext(), "Enter Card Mission", Toast.LENGTH_SHORT).show();
            } else {
                customCards.addNewMissionCard(getMissionDescription());
                edtMissionDescription.getEditText().setText("");
                Toast.makeText(view.getContext(), "Mission Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteAllMissions.setOnClickListener(view -> {
            customCards.deleteAllMissions();
            Toast.makeText(view.getContext(), "All Missions Deleted Successfully", Toast.LENGTH_SHORT).show();
        });

    }

    private String getMissionDescription() {
        String mission = this.edtMissionDescription.getEditText().getText().toString();
        if (!mission.isEmpty()) {
            return mission;
        } else {
            return Constants.FAIL;
        }
    }

    private void findViews() {
        this.edtMissionDescription = findViewById(R.id.edtMissionDescription);
        this.btnAddMission = findViewById(R.id.btnAddMission);
        this.btnDeleteAllMissions = findViewById(R.id.btnDeleteAllMissions);
        this.btnBackMissions = findViewById(R.id.btnBackMissions);
    }

    private void setVarsFromIntent() {
        Intent i = getIntent();
    }
}