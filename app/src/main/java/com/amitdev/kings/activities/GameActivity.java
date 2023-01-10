package com.amitdev.kings.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amitdev.kings.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import GameLogic.Card;
import GameLogic.CardParser;
import GameLogic.CustomCards;
import GameLogic.Deck;
import common.Constants;
import common.TinyDB;


public class GameActivity extends AppCompatActivity {
    private int playersCounter = 0;
    private String mode;
    private MaterialTextView lblGameName;
    private MaterialTextView lblPlayerName;
    private MaterialTextView lblCardDescription;
    private ShapeableImageView imgCardSymbol;
    private MaterialButton btnNext;
    private ArrayList<String> playerList = new ArrayList<>();
    private CustomCards customCards;
    private final Deck deck = new Deck();
    private final CardParser cardParser = new CardParser();
    private int doOrDrinkCounter = 0;
    private int standardCounter = 0;
    private List<String> missions;
    private InterstitialAd mInterstitial;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        this.tinyDB = new TinyDB(this);
        this.missions = new ArrayList<String>(Arrays.asList(this.getResources().getStringArray(R.array.missions)));
        this.customCards = new CustomCards(this);
        boolean isUltimate = this.tinyDB.getBoolean(Constants.ULTIMATE);
        boolean isPremium = this.tinyDB.getBoolean(Constants.TWIST);
        boolean isNoAds = this.tinyDB.getBoolean(Constants.NO_ADS);
        findViews();
        initInterstitial();
        concatAllMissions();
        setVarsFromIntent();
        nextCardHelper();

        //Game Loop
        btnNext.setOnClickListener(view -> {
            if (mode.equals(ModesActivity.eMode.REG.name())) {
                if (!isNoAds) {
                    if (standardCounter % 4 == 0) {
                        showInterstitial();
                        loadAd();
                    }
                }
                nextCard();
                standardCounter++;
            } else if (isPremium || isUltimate) { //PREMIUM
                doOrDrinkCounter++;
                if (doOrDrinkCounter % 8 == 0) {
                    doOrDrinkMission();
                } else {
                    nextCard();
                }
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitial != null) {
            mInterstitial.show(GameActivity.this);
        } else {
            Log.d(Constants.TAG, "The interstitial ad wasn't ready yet.");
        }
    }

    private void initInterstitial() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        loadAd();
    }

    private void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitial = interstitialAd;
                        Log.i(Constants.TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(Constants.TAG, loadAdError.toString());
                        mInterstitial = null;
                    }
                });
    }

    private void concatAllMissions() {
        ArrayList<String> customMissions = this.customCards.fetchMissions();
        Log.d(Constants.TAG, "concatAllMissions: " + customMissions.toString());
        Log.d(Constants.TAG, "concatAllMissions: " + this.missions);
        this.missions.addAll(customMissions);
        Log.d(Constants.TAG, "concatAllMissions After concatenating: " + this.missions);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void doOrDrinkMission() {
        Random r = new Random();

        int randomitem = r.nextInt(missions.size());
        String randomElement = missions.get(randomitem);
        int randomPlayerIndex = r.nextInt(playerList.size());
        String randomPlayerName = playerList.get(randomPlayerIndex);
        lblPlayerName.setText(randomPlayerName);
        lblCardDescription.setText(randomElement);
        imgCardSymbol.setImageDrawable(getDrawable(R.drawable.ic_spy));
        Log.d(Constants.TAG, "doOrDrinkMission: had been invoked, Current Counter:" + doOrDrinkCounter);
    }

    private void nextCard() {
        if (playerList.size() - 1 == playersCounter) {
            playersCounter = 0;
            lblPlayerName.setText(playerList.get(playersCounter));

        } else {
            playersCounter++;
            lblPlayerName.setText(playerList.get(playersCounter));
        }
        nextCardHelper();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void nextCardHelper() {
        if (!deck.getDeckOfCards().empty()) {
            Card currCard = deck.getDeckOfCards().pop();
            Map<Integer, String> currCardData = cardParser.parseCard(currCard);
            String description = getString(Integer.parseInt(Objects.requireNonNull(currCardData.get(currCard.getNumber()))));
            lblCardDescription.setText(description);

            switch (currCard.getSymbol().name()) {
                case "CLUBS":
                    imgCardSymbol.setImageDrawable(getDrawable(R.drawable.ic_clubs));
                    break;
                case "SPADES":
                    imgCardSymbol.setImageDrawable(getDrawable(R.drawable.ic_spades));
                    break;
                case "DIAMONDS":
                    imgCardSymbol.setImageDrawable(getDrawable(R.drawable.ic_diamonds));
                    break;
                case "HEARTS":
                    imgCardSymbol.setImageDrawable(getDrawable(R.drawable.ic_hearts));
                    break;
            }
        } else {
            lblPlayerName.setText("Finished Game");
            lblCardDescription.setText("Drink Safely");
            btnNext.setText("Start Over");
            imgCardSymbol.setImageDrawable(getDrawable(R.drawable.ic_crown));
            btnNext.setOnClickListener(view -> {
                Intent i = new Intent(this, MainPageActivity.class);
                startActivity(i);
                finish();
            });
        }
    }

    private void findViews() {
        lblGameName = findViewById(R.id.lblGameName);
        lblPlayerName = findViewById(R.id.lblPlayerName);
        btnNext = findViewById(R.id.btnNext);
        lblCardDescription = findViewById(R.id.lblCardDescription);
        imgCardSymbol = findViewById(R.id.imgCardSymbol);
    }

    private void setVarsFromIntent() {
        Intent i = getIntent();
        mode = i.getStringExtra("Mode");
        String gameName = i.getStringExtra("gameName");
        playerList = i.getStringArrayListExtra("playerList");
        if (!gameName.isEmpty()) {
            lblGameName.setText(gameName);
        }
        if (!playerList.get(playersCounter).isEmpty()) {
            lblPlayerName.setText(playerList.get(playersCounter));
        }
    }
}

