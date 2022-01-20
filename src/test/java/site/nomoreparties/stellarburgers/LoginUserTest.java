package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
    }

    @Test
    public void loginExistUserTest() {
        userClient.create(user);
        ValidatableResponse response = userClient.login(UserCredentials.from(user));
        Assert.assertTrue(response.extract().statusCode() == 200);
        Assert.assertTrue(response.extract().path("message") == null);
    }

    @Test
    public void loginNotExistUserTest() {
        ValidatableResponse response = userClient.login(UserCredentials.from(user));
        Assert.assertTrue(response.extract().statusCode() == 401);
        Assert.assertEquals("email or password are incorrect", response.extract().path("message"));
    }
}
