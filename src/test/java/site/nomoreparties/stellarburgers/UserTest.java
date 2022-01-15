package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
    }

    @Test
    public void userSucessLoginTest() {
        userClient.create(user);
        ValidatableResponse response = userClient.login(UserCredentials.from(user));
        Assert.assertTrue(response.extract().statusCode() == 200);
        Assert.assertTrue(response.extract().path("message") == null);
    }

    @Test
    public void userFailLoginTest() {
        ValidatableResponse response = userClient.login(UserCredentials.from(user));
        Assert.assertTrue(response.extract().statusCode() == 401);
        Assert.assertEquals("email or password are incorrect", response.extract().path("message"));
    }

    @Test
    public void userDoubleCreatedReturnErrorTest() {
        userClient.create(user);
        ValidatableResponse response = userClient.create(user);
        Assert.assertTrue(response.extract().statusCode() == 403);
        Assert.assertEquals("User already exists", response.extract().path("message"));
    }

    @Test
    public void authUserEditPasswordTest() {
        String token = userClient.create(user).extract().path("accessToken");
        UserDataJson body = new UserDataJson(null, null, (RandomStringUtils.randomAlphabetic(10)));
        userClient.editData(body, token);
        ValidatableResponse response = userClient.manualLogin(new UserDataJson(null, user.email, body.getKey("password")));
        Assert.assertTrue(response.extract().statusCode() == 200);
        Assert.assertTrue(response.extract().path("success"));
        Assert.assertTrue(response.extract().path("message") == null);
    }
}
