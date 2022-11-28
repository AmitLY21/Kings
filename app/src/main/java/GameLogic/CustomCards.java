package GameLogic;

import android.content.Context;

import common.BasicSharedPreference;

public class CustomCards {
    BasicSharedPreference mySP;

    //TODO fix shared preference
    public CustomCards(Context context) {
        this.mySP = new BasicSharedPreference(context);
    }

    public void addNewMissionCard(String cardDescription){
        if(!cardDescription.isEmpty()){
            Card card = new Card(cardDescription);
            int counter = CardCounter.getInstance().increment();
            String cardName = "card_"+counter;
            mySP.putObject(cardName,card);
        }
    }

}

