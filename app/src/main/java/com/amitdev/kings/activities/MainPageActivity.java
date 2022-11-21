package com.amitdev.kings.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amitdev.kings.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Locale;

public class MainPageActivity extends AppCompatActivity {

    private MaterialButton btnSettings;
    private MaterialButton btnHowToPlay;
    private MaterialButton btnPremium;
    private MaterialButton btnStart;
    private TextInputLayout edtAddPlayerName;
    private LinearLayout llPlayerList;
    private ArrayList<String> playerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        findViews();

        btnHowToPlay.setOnClickListener(view -> openHowtoPlayDialog());

        edtAddPlayerName.setEndIconOnClickListener(view -> addPlayer());

        btnStart.setOnClickListener(view -> moveToModesPage());

        btnSettings.setOnClickListener(view -> openSettings());

    }

    //TODO: Add localization in the future
    private void openSettings() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Settings");
        builder.setCancelable(false);
        builder.setNeutralButton(
                "Hebrew",
                (dialog, id) -> Toast.makeText(MainPageActivity.this, "Changed to Hebrew", Toast.LENGTH_SHORT).show());
        builder.setPositiveButton(
                "English",
                (dialog, id) -> Toast.makeText(MainPageActivity.this, "Changed to English", Toast.LENGTH_SHORT).show());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void moveToModesPage() {
        if (playerList.size() > 1) {
            Intent i = new Intent(this, ModesActivity.class);
            i.putExtra("playerList", playerList);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "Not enough players", Toast.LENGTH_SHORT).show();
        }
    }

    private void openHowtoPlayDialog() {
        final Dialog dialog = new Dialog(MainPageActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.how_to_play);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.btnClose).setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private void addPlayer() {
        String currPlayer = edtAddPlayerName.getEditText().getText().toString();
        currPlayer = currPlayer.trim();
        currPlayer = currPlayer.toUpperCase(Locale.ROOT);
        if (currPlayer.isEmpty()) {
            Toast.makeText(this, "Enter Player Name!", Toast.LENGTH_SHORT).show();
        } else {
            this.playerList.add(currPlayer);
            TextView tv = new TextView(this);
            tv.setText(currPlayer);
            tv.setTextSize(18);
            tv.setAllCaps(true);
            tv.setTextColor(Color.WHITE);
            llPlayerList.addView(tv);
        }
        edtAddPlayerName.getEditText().getText().clear();
    }

    private void findViews() {
        btnSettings = findViewById(R.id.btnSettings);
        btnPremium = findViewById(R.id.btnPremium);
        btnHowToPlay = findViewById(R.id.btnHowToPlay);
        btnStart = findViewById(R.id.btnStart);
        edtAddPlayerName = findViewById(R.id.edtAddPlayerName);
        llPlayerList = findViewById(R.id.llPlayerList);
    }
}