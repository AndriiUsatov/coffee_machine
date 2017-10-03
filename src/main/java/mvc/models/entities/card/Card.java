package mvc.models.entities.card;

public class Card {
    private String cardNumber;
    private String secureCode;

    public Card(String cardNumber, String secureCode){
        this.cardNumber = cardNumber;
        this.secureCode = secureCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getSecureCode() {
        return secureCode;
    }
}
