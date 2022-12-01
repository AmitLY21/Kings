package GameLogic;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import common.Constants;
import common.TinyDB;

public class CustomCards {
    ArrayList<String> customMissions = new ArrayList<>();
    TinyDB tinyDB;

    public CustomCards(Context context) {
        tinyDB = new TinyDB(context);
        loadMissionsFromDB();
    }

    private void loadMissionsFromDB() {
        this.customMissions = this.tinyDB.getListString("Missions");
    }

    public void addNewMissionCard(String cardDescription) {
        loadMissionsFromDB();
        if (!cardDescription.isEmpty()) {
            this.customMissions.add(cardDescription);
            saveMissions();
        }
    }

    public void deleteAllMissions() {
        loadMissionsFromDB();
        this.customMissions.clear();
        Log.d(Constants.TAG_SP, "Cleared Custom Missions List");
        saveMissions();
    }

    private void saveMissions() {
        this.tinyDB.putListString(Constants.SP_MISSIONS, this.customMissions);
        Log.d(Constants.TAG_SP, "Saved Custom Missions List: " + this.customMissions.toString());
    }

    public ArrayList<String> fetchMissions() {
        loadMissionsFromDB();
        return this.customMissions;
    }
}

