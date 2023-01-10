package com.amitdev.kings.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amitdev.kings.R;
import com.google.android.material.button.MaterialButton;

import common.Constants;
import common.TinyDB;

public class PurchaseActivity extends AppCompatActivity {

    private MaterialButton btnPurchaseRemoveAds;
    private MaterialButton btnPurchaseKingsWithTwist;
    private MaterialButton btnPurchaseUltimatePack;
    private MaterialButton btnBack;
    private LinearLayout llRemoveAds;
    private LinearLayout llTwist;
    private LinearLayout llUltimate;
    private AlertDialog.Builder builder;
    private TinyDB tinyDB;
    private boolean isNoAds;
    private boolean isPremium;
    private boolean isUltimate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_purchase);
        this.tinyDB = new TinyDB(this);
        this.builder = new AlertDialog.Builder(this);
        fetchDataFromTinyDB();
        findViews();
        disableButtons();

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
            finish();
        });

        btnPurchaseRemoveAds.setOnClickListener(view -> {
            purchaseDialog("Remove Ads", Constants.NO_ADS);
        });

        btnPurchaseKingsWithTwist.setOnClickListener(view -> {
            purchaseDialog("Kings with Twist", Constants.TWIST);
        });

        btnPurchaseUltimatePack.setOnClickListener(view -> {
            purchaseDialog("Ultimate Pack", Constants.ULTIMATE);
        });
    }

    private void fetchDataFromTinyDB() {
        this.isNoAds = this.tinyDB.getBoolean(Constants.NO_ADS);
        this.isPremium = this.tinyDB.getBoolean(Constants.TWIST);
        this.isUltimate = this.tinyDB.getBoolean(Constants.ULTIMATE);
    }

    /**
     * Buttons which the user already purchased will be disabled
     */
    private void disableButtons() {
        if (isNoAds) {
            disableNoAdsCard();
        }
        if (isPremium) {
            disableTwistCard();
        }
        if (isUltimate) {
            disableUltimateCard();
            disableTwistCard();
            disableNoAdsCard();
        }
    }

    private void disableUltimateCard() {
        btnPurchaseUltimatePack.setEnabled(false);
        btnPurchaseUltimatePack.setAlpha(0.5f);
        llUltimate.setAlpha(0.5f);
    }

    private void disableNoAdsCard() {
        btnPurchaseRemoveAds.setEnabled(false);
        btnPurchaseRemoveAds.setAlpha(0.5f);
        llRemoveAds.setAlpha(0.5f);
    }

    private void disableTwistCard() {
        btnPurchaseKingsWithTwist.setEnabled(false);
        btnPurchaseKingsWithTwist.setAlpha(0.5f);
        llTwist.setAlpha(0.5f);
    }

    private void purchaseDialog(String title, String purchase_type) {
        builder.setTitle(title);
        builder.setMessage("Are you sure you want to buy the following " + purchase_type + "?");
        builder.setCancelable(true);
        builder.setNeutralButton(
                "Yes",
                (dialog, id) -> {
                    addUserPermissionAfterPurchase(purchase_type);
                    Toast.makeText(this, "Purchased completed", Toast.LENGTH_SHORT).show();
                    disableButtons();
                    reloadActivity();
                });
        builder.setPositiveButton(
                "No",
                (dialog, id) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void reloadActivity() {
        Intent i = new Intent(PurchaseActivity.this, PurchaseActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

    private void addUserPermissionAfterPurchase(String purchase_type) {
        this.tinyDB.putBoolean(purchase_type, true);
        Log.d(Constants.TAG_SP, "addUserPermissionAfterPurchase: " + tinyDB.getAll().toString());
    }

    private void findViews() {
        btnPurchaseRemoveAds = findViewById(R.id.btnPurchaseRemoveAds);
        btnPurchaseKingsWithTwist = findViewById(R.id.btnPurchaseKingsWithTwist);
        btnPurchaseUltimatePack = findViewById(R.id.btnPurchaseUltimatePack);
        btnBack = findViewById(R.id.btnBackFromPurchasePage);
        llRemoveAds = findViewById(R.id.llRemoveAds);
        llTwist = findViewById(R.id.llTwist);
        llUltimate = findViewById(R.id.llUltimate);
    }
}