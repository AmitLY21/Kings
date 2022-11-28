package GameLogic;

import com.amitdev.kings.activities.eSymbol;

public class Card {

    private int number;
    private eSymbol symbol;

    public Card(int number, eSymbol symbol) {
        this.number = number;
        this.symbol = symbol;
    }

    public Card(String cardDescription) {
    }

    public int getNumber() {
        return number;
    }

    public Card setNumber(int number) {
        this.number = number;
        return this;
    }

    public eSymbol getSymbol() {
        return symbol;
    }

    public Card setSymbol(eSymbol symbol) {
        this.symbol = symbol;
        return this;
    }
}
