package GameLogic;

import com.amitdev.kings.R;

import java.util.HashMap;
import java.util.Map;

public class CardParser {
    public CardParser() {}

    public Map<Integer, String> parseCard(Card card) {
        int currCardNumber = card.getNumber();
        Map<Integer, String> cardTitleDesc = new HashMap<>();
        switch (currCardNumber) {
            case 1:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.ace));
                break;
            case 2:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.two));
                break;
            case 3:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.three));
                break;
            case 4:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.four));
                break;
            case 5:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.five));
                break;
            case 6:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.six));
                break;
            case 7:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.seven));
                break;
            case 8:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.eight));
                break;
            case 9:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.nine));
                break;
            case 10:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.ten));
                break;
            case 11:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.jack));
                break;
            case 12:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.queen));
                break;
            case 13:
                cardTitleDesc.put(currCardNumber, String.valueOf(R.string.king));
                break;
            default:
        }
        return cardTitleDesc;
    }


}