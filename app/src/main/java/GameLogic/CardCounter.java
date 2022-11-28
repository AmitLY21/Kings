package GameLogic;

import common.BasicSharedPreference;

public class CardCounter {
    public int counter = 0;
    private BasicSharedPreference mySP;
    private static CardCounter instance = null;

    public static CardCounter getInstance() {
        if (instance == null)
            instance = new CardCounter();
        return instance;
    }

    private CardCounter() {
        loadCounter();
    }

    public void loadCounter() {
        counter = mySP.getIntKey("counter", 0);
    }

    public int increment() {
        mySP.putsIntValue("counter", counter++);
        return counter;
    }
}
