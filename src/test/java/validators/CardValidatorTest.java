package validators;


import entities.Card;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class CardValidatorTest {

    Card card;

    @Before
    public void setUp(){
        String cardNumber = "1234567890123456";
        String secureCode = "123";
        Date expiryDate = new Date(System.currentTimeMillis() + 31536000000L);
        card = new Card(cardNumber,secureCode,expiryDate);
    }

    @Test
    public void validateNullCardTest(){
        Assert.assertFalse(CardValidator.validateCard(null,  "123"));
    }

    @Test
    public void validateNullAmountTest(){
        Assert.assertFalse(CardValidator.validateCard(card, null));
    }

    @Test
    public void validateCardTest(){
        String amount = "100";
        Assert.assertTrue(CardValidator.validateCard(card, amount));
    }

    @Test
    public void validateCardWithScriptTest(){
        String amount = "<script>alert('xss');</script>";
        Assert.assertFalse(CardValidator.validateCard(card, amount));
    }
}
