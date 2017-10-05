package validators;

import entities.Card;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidator {
    public synchronized static boolean validateCard(Card card) {
        if(card == null)
            return false;
        return validateNumber(card.getCardNumber()) && validateSecureCode(card.getSecureCode()) ? true : false;
    }

    private synchronized static boolean validateNumber(String cardNumber) {
        boolean result = false;
        if (hasScript(cardNumber))
            return result;
        String cardNumberRegex = new String("[0-9]{16,19}");
        Pattern pattern = Pattern.compile(cardNumberRegex);
        Matcher matcher = pattern.matcher(cardNumber);
        while (matcher.find())
            result = matcher.group().equals(cardNumber) ? true : result;
        return result;
    }

    private synchronized static boolean validateSecureCode(String secureCode){
        boolean result = false;
        if (hasScript(secureCode))
            return result;
        String secureCodeRegex = new String("[0-9]{3,3}");
        Pattern pattern = Pattern.compile(secureCodeRegex);
        Matcher matcher = pattern.matcher(secureCode);
        while (matcher.find())
            result = matcher.group().equals(secureCode) ? true : result;
        return result;
    }

    private synchronized static boolean hasScript(String line) {
        String scriptRegex = new String("<script");
        Pattern pattern = Pattern.compile(scriptRegex);
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }
}
