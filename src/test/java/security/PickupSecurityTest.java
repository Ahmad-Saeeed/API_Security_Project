package security;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AuthUtil;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PickupSecurityTest {

    String BASE_URL = "https://stg-app.bosta.co";
    String TOKEN;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        TOKEN = AuthUtil.generateToken(); // generate valid token
    }

    @Test
    public void validPickupCreation() {
        String body = "{"
                + "\"businessLocationId\":\"MFqXsoFhxO\","
                + "\"contactPerson\":{"
                + "\"_id\":\"_sCFBrHGi\","
                + "\"name\":\"test name\","
                + "\"email\":\"test@bosta.co\","
                + "\"phone\":\"+201055592829\""
                + "},"
                + "\"scheduledDate\":\"2025-06-30\","
                + "\"numberOfParcels\":3,"
                + "\"hasBigItems\":false,"
                + "\"repeatedData\":{\"repeatedType\":\"One Time\"},"
                + "\"creationSrc\":\"Web\""
                + "}";

        given()
                .header("Authorization", TOKEN)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v2/pickups")
                .then()
                .statusCode(anyOf(is(200), is(201)));
    }

    @Test
    public void missingAuthorization() {
        given()
                .contentType("application/json") // Required to avoid 415
                .when()
                .post("/api/v2/pickups")
                .then()
                .statusCode(401);
    }

    @Test
    public void sqlInjectionTest() {
        String body = "{"
                + "\"businessLocationId\":\"MFqXsoFhxO\","
                + "\"contactPerson\":{"
                + "\"_id\":\"_sCFBrHGi\","
                + "\"name\":\"' OR 1=1 --\","
                + "\"email\":\"test@bosta.co\","
                + "\"phone\":\"+201055592829\""
                + "},"
                + "\"scheduledDate\":\"2025-06-30\","
                + "\"numberOfParcels\":3,"
                + "\"hasBigItems\":false,"
                + "\"repeatedData\":{\"repeatedType\":\"One Time\"},"
                + "\"creationSrc\":\"Web\""
                + "}";

        given()
                .header("Authorization", TOKEN)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/v2/pickups")
                .then()
                .statusCode(not(500));
    }
}