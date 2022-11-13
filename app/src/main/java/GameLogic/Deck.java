package GameLogic;

import com.amitdev.kings.activities.eSymbol;

import java.util.Collections;
import java.util.Stack;

public class Deck {
    private Stack<Card> deckOfCards;

    public Deck() {
        createNewDeckOfCards();
    }

    public Stack<Card> getDeckOfCards() {
        return deckOfCards;
    }

    private void createNewDeckOfCards() {
        this.deckOfCards = new Stack<>();
        for (int i = 1; i < 14; i++) {
            deckOfCards.push(new Card(i, eSymbol.CLUBS));
            deckOfCards.push(new Card(i, eSymbol.SPADES));
            deckOfCards.push(new Card(i, eSymbol.DIAMONDS));
            deckOfCards.push(new Card(i, eSymbol.HEARTS));
        }
        Collections.shuffle(deckOfCards);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "deckOfCards=" + deckOfCards +
                '}';
    }
}
