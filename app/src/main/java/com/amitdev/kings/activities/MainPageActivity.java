package com.amitdev.kings.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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

public class MainPageActivity extends AppCompatActivity {

    private MaterialButton btnSettings;
    private MaterialButton btnHowToPlay;
    private MaterialButton btnPremium;
    private MaterialButton btnStart;
    private TextInputLayout edtAddPlayerName;
    private ScrollView scrollPlayerList;
    private LinearLayout llPlayerList;
    private ArrayList<String> playerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        findViews();

        btnHowToPlay.setOnClickListener(view -> {
            openHowtoPlayDialog();
        });

        edtAddPlayerName.setEndIconOnClickListener(view -> {
            addPlayer();
        });

        btnStart.setOnClickListener(view -> {
            moveToModesPage();
        });

    }

    private void moveToModesPage() {
        if (playerList.size() > 1) {
            Intent i = new Intent(this, ModesActivity.class);
            i.putExtra("playerList", playerList);
            startActivity(i);
        } else {
            Toast.makeText(this, "Not enough players", Toast.LENGTH_SHORT).show();
        }
    }

    private void openHowtoPlayDialog() {
        final Dialog dialog = new Dialog(MainPageActivity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.how_to_play);
        dialog.show();
    }

    private void addPlayer() {
        String currPlayer = edtAddPlayerName.getEditText().getText().toString();
        currPlayer = currPlayer.trim();
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
        scrollPlayerList = findViewById(R.id.scrollPlayerList);
        llPlayerList = findViewById(R.id.llPlayerList);
    }
}