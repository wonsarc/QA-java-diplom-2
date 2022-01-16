package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class NotAuthOrderCreateParameterizedTest {
    private final IngridientsDataJson body;
    private OrderClient orderClient;

    public NotAuthOrderCreateParameterizedTest(IngridientsDataJson body) {
        this.body = body;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderInfo() {
        return new Object[][]{
                {new IngridientsDataJson("61c0c5a71d1f82001bdaaa6d")},
                {new IngridientsDataJson(null)},
                {new IngridientsDataJson("test")}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void userCreatedTest() {
        ValidatableResponse response = orderClient.create(body, "123");
        Assert.assertTrue(response.extract().statusCode() == 401);
        Assert.assertEquals("You should be authorised", response.extract().path("message"));
    }
}
