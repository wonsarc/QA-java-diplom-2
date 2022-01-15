package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class UserCreateParameterizedTest {
    private final User user;
    private final int statusCode;
    private final String message;
    private UserClient userClient;

    public UserCreateParameterizedTest(User user, int statusCode, String message) {
        this.user = user;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getUserInfo() {
        return new Object[][]{
                {User.getRandom(), 200, null},
                {User.getWithEmailAndNameOnly(), 403, "Email, password and name are required fields"},
                {User.getWithEmailAndPasswordOnly(), 403, "Email, password and name are required fields"},
                {User.getWithNameAndPasswordOnly(), 403, "Email, password and name are required fields"}
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    public void userCreatedTest() {
        ValidatableResponse response = userClient.create(user);
        Assert.assertTrue(statusCode == response.extract().statusCode());
        Assert.assertEquals(message, response.extract().path("message"));
    }
}
