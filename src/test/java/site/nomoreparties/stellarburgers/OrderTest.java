package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {
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
    public void authOrderCreateWithWrongHashIngridient() {
        String token = userClient.create(user).extract().path("accessToken");
        IngridientsDataJson body = new IngridientsDataJson("test");
        ValidatableResponse response = orderClient.create(body, token);
        Assert.assertTrue(response.extract().statusCode() == 500);
    }

    @Test
    public void notAuthOrderCreateWithWrongHashIngridient() {
        IngridientsDataJson body = new IngridientsDataJson("test");
        ValidatableResponse response = orderClient.create(body);
        Assert.assertTrue(response.extract().statusCode() == 500);
    }
}
