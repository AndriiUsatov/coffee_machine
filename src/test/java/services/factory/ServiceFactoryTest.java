package services.factory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.*;
import services.factory.impl.ServiceFactoryImpl;

public class ServiceFactoryTest {
    private ServiceFactory serviceFactory;

    @Before
    public void setUp() {
        serviceFactory = ServiceFactoryImpl.getServiceFactoryInstance();
    }

    @Test
    public void testCupService() {
        Assert.assertTrue(serviceFactory.getCupService() instanceof CupService);
    }

    @Test
    public void testDrinkService() {
        Assert.assertTrue(serviceFactory.getDrinkService() instanceof DrinkService);
    }

    @Test
    public void testIngredientService() {
        Assert.assertTrue(serviceFactory.getIngredientService() instanceof IngredientService);
    }

    @Test
    public void testItemService() {
        Assert.assertTrue(serviceFactory.getItemService() instanceof ItemService);
    }

    @Test
    public void testPasswordService() {
        Assert.assertTrue(serviceFactory.getPasswordService() instanceof PasswordService);
    }

    @Test
    public void testStickService() {
        Assert.assertTrue(serviceFactory.getStickService() instanceof StickService);
    }

    @Test
    public void testUserService() {
        Assert.assertTrue(serviceFactory.getUserService() instanceof UserService);
    }

}
