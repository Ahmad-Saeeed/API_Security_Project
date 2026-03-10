package security;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ForgetPasswordSecurityTest {

    String BASE_URL = "https://stg-app.bosta.co";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void validResetRequest() throws InterruptedException {
        String payload = "{"
                + "\"email\":\"test@bosta.co\""
                + "}";

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/v2/users/forget-password")
                .then()
                .statusCode(anyOf(is(200), is(201)));

        Thread.sleep(4000); // prevent 429
    }

    @Test
    public void invalidEmail() throws InterruptedException {
        String payload = "{\"email\":\"invalid_email\"}";

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/v2/users/forget-password")
                .then()
                .statusCode(200); // API returns generic success

        Thread.sleep(4000); // prevent 429
    }
}