package services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class UserServiceTest {

    UserService userService;
    @Before
    public void setUp() {
        userService = UserService.getUserServiceInstance();
    }

    @Test
    public void testGetUserNull() {
        Assert.assertNull(userService.getUser(null));
    }


    @Test
    public void testUpdateBalanceNull() {
        Assert.assertFalse(userService.updateBalance(null, null));
    }

    @Test
    public void testUpdateInvalidBalance() { Assert.assertFalse(userService.updateBalance(new BigDecimal(-1), "Andrii")); }
}
