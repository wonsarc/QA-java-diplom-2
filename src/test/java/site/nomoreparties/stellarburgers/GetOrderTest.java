package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetOrderTest {
    private OrderClient orderClient;
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
        orderClient = new OrderClient();
    }

    @Test
    public void getOrderAuthUserTest() {
        String token = userClient.create(user).extract().path("accessToken");
        ValidatableResponse response = orderClient.get(token);
        Assert.assertTrue(response.extract().statusCode() == 200);
        Assert.assertTrue(response.extract().path("success"));
    }

    @Test
    public void getOrderNotAuthUserTest() {
        ValidatableResponse response = orderClient.get();
        Assert.assertTrue(response.extract().statusCode() == 401);
        Assert.assertEquals("You should be authorised", response.extract().path("message"));
    }
}
