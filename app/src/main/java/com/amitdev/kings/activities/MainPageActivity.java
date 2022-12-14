package com.amitdev.kings.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amitdev.kings.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Locale;

import common.Constants;
import common.TinyDB;

public class MainPageActivity extends AppCompatActivity {
    private AdView adView;
    private MaterialButton btnSettings;
    private MaterialButton btnHowToPlay;
    private MaterialButton btnPremium;
    private MaterialButton btnStart;
    private TextInputLayout edtAddPlayerName;
    private LinearLayout llPlayerList;
    private ArrayList<String> playerList = new ArrayList<>();
    private TinyDB tinyDB;

    @Override
    protected void onResume() {
        super.onResume();
        boolean isUltimate = tinyDB.getBoolean(Constants.ULTIMATE);
        boolean isNoAds = tinyDB.getBoolean(Constants.NO_ADS);
        if (!isUltimate && !isNoAds) {
            loadAds();
        } else {
            this.adView.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.tinyDB = new TinyDB(this);
        this.adView = new AdView(this);

        Log.d(Constants.TAG_SP, "All db onCreate:" + this.tinyDB.getAll().toString());

        findViews();

        btnHowToPlay.setOnClickListener(view -> openHowtoPlayDialog());

        edtAddPlayerName.setEndIconOnClickListener(view -> addPlayer());

        btnStart.setOnClickListener(view -> moveToModesPage());

        btnSettings.setOnClickListener(view -> openSettings());

        btnPremium.setOnClickListener(view -> moveToPurchasePage());

    }

    private void moveToPurchasePage() {
        Intent i = new Intent(this, PurchaseActivity.class);
        startActivity(i);
    }

    private void loadAds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void openSettings() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Settings");
        builder.setCancelable(true);
        builder.setNeutralButton(
                "Privacy Policy",
                (dialog, id) -> openUrl(getString(R.string.privacy_url)));//Toast.makeText(MainPageActivity.this, "Changed to Hebrew", Toast.LENGTH_SHORT).show());
        builder.setPositiveButton(
                "Terms & Conditions",
                (dialog, id) -> openUrl(getString(R.string.terms_url)));//Toast.makeText(MainPageActivity.this, "Changed to English", Toast.LENGTH_SHORT).show());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openUrl(String url) {
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
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