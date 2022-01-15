package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {
    private static final String ORDER_PATH = "api/orders/";
    private static final String INGRIDIENTS_PATH = "api/ingredients";

    @Step
    public IngridientsJson getDataIngridients() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(INGRIDIENTS_PATH)
                .body().as(IngridientsJson.class);
    }
}
