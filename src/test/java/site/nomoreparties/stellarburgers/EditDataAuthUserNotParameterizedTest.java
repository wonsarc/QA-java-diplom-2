package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditDataAuthUserNotParameterizedTest {
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
    }

    @Test
    public void editPasswordAuthUserTest() {
        String token = userClient.create(user).extract().path("accessToken");
        UserDataJson body = new UserDataJson(null, null, (RandomStringUtils.randomAlphabetic(10)));
        userClient.editData(body, token);
        ValidatableResponse response = userClient.login(new UserDataJson(null, user.email, body.getKey("password")));
        Assert.assertTrue(response.extract().statusCode() == 200);
        Assert.assertTrue(response.extract().path("success"));
        Assert.assertTrue(response.extract().path("message") == null);
    }
}
