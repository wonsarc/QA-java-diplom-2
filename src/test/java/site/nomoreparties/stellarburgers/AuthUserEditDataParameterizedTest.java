package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;

@RunWith(Parameterized.class)
public class AuthUserEditDataParameterizedTest {
    private final UserDataJson body;
    private final String userDataParam;
    private User user;
    private UserClient userClient;

    public AuthUserEditDataParameterizedTest(UserDataJson body, String userDataParam) {
        this.body = body;
        this.userDataParam = userDataParam;
    }

    @Parameterized.Parameters
    public static Object[][] getUserInfo() {
        return new Object[][]{
                {new UserDataJson(RandomStringUtils.randomAlphabetic(10), null, null), "name"},
                {new UserDataJson(null, (RandomStringUtils.randomAlphabetic(10) + "@test.ru").toLowerCase(), null), "email"},
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
    }

    @Test
    public void userCreatedTest() {
        String token = userClient.create(user).extract().path("accessToken");
        ValidatableResponse response = userClient.editData(body, token);
        HashMap<String, String> userInfo = response.extract().path("user");
        Assert.assertEquals(body.getKey(userDataParam), userInfo.get(userDataParam));
        Assert.assertTrue(response.extract().statusCode() == 200);
        Assert.assertTrue(response.extract().path("success"));
    }
}
