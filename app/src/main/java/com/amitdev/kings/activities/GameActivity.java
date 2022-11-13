package com.amitdev.kings.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.amitdev.kings.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import GameLogic.Card;
import GameLogic.CardParser;
import GameLogic.Deck;


public class GameActivity extends AppCompatActivity {
    private final String TAG = "Kings ->";
    private int playersCounter = 0;
    private String mode;
    private MaterialTextView lblGameName;
    private MaterialTextView lblPlayerName;
    private MaterialTextView lblCardDescription;
    private ShapeableImageView imgCardSymbol;
    private MaterialButton btnNext;
    private ArrayList<String> playerList = new ArrayList<>();
    private final Deck deck = new Deck();
    private final CardParser cardParser = new CardParser();
    private int doOrDrinkCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        findViews();
        setVarsFromIntent();
        nextCardHelper();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.equals("REG")) {
                    nextCard();
                }else{ //PREMIUM
                    doOrDrinkCounter++;
                    if(doOrDrinkCounter%8==0){
                        doOrDrinkMission();
                    }
                    nextCard();
                }
            }
        });
    }

    private void doOrDrinkMission() {
        //TODO: create do or drink mission and show them on card!
        Log.d(TAG, "doOrDrinkMission: had been invoked, Current Counter:" + doOrDrinkCounter);
    }

    private void nextCard() {
        if (playerList.size() - 1 == playersCounter) {
            playersCounter = 0;
            lblPlayerName.setText(playerList.get(playersCounter));

        }else {
            playersCounter++;
            lblPlayerName.setText(playerList.get(playersCounter));
        }
        nextCardHelper();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void nextCardHelper() {
        if(!deck.getDeckOfCards().empty()) {
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
        }else{
            //finished cards!
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

