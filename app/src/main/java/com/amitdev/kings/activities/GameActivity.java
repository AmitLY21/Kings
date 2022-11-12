package com.amitdev.kings.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.amitdev.kings.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {
    private int playersCounter = 0;
    private String mode;
    private String gameName;
    private MaterialTextView lblGameName;
    private MaterialTextView lblPlayerName;
    private MaterialTextView lblCardDescription;
    private MaterialButton btnNext;
    private ArrayList<String> playerList = new ArrayList<>();
    //private Map<Integer,String> deck = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        findViews();
        setVarsFromIntent();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode.equals("REG")){
                    nextCard();
                }
            }
        });
    }

    //todo: fix logic
    private void nextCard() {
        if(playerList.size() -1  == playersCounter)
            playersCounter = -1;
        else{
            playersCounter++;
            lblPlayerName.setText(playerList.get(playersCounter));

        }
        //lblCardDescription.setText();
    }

    private void findViews() {
        lblGameName = findViewById(R.id.lblGameName);
        lblPlayerName = findViewById(R.id.lblPlayerName);
        btnNext = findViewById(R.id.btnNext);
        lblCardDescription = findViewById(R.id.lblCardDescription);
    }

    private void setVarsFromIntent() {
        Intent i = getIntent();
        mode = i.getStringExtra("Mode");
        gameName = i.getStringExtra("gameName");
        playerList = i.getStringArrayListExtra("playerList");

        if (!gameName.isEmpty()) {
            lblGameName.setText(gameName);
        }

        if (!playerList.get(playersCounter).isEmpty()) {
            lblPlayerName.setText(playerList.get(playersCounter));
        }

        if (mode.equals("REG")) {
            //TODO: set do or drink feature to false
        }
    }
}