package validators;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserValidatorTest {

    UserValidator validator;

    @Before
    public void setUp() {
        validator = UserValidator.getUserValidatorInstance();
    }

    @Test
    public void validateLoginInNullLoginTest() {
        Assert.assertFalse(validator.validateLoginIn(null, "123456"));
    }

    @Test
    public void validateLoginInNullPasswordTest() {
        Assert.assertFalse(validator.validateLoginIn("someLogin", null));
    }

    @Test
    public void validateLoginInScriptTest() {
        Assert.assertFalse(validator.validateLoginIn("<script>alert('xss');</script>", "123456"));
    }

}
