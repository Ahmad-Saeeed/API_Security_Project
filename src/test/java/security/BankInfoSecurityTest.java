package security;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AuthUtil;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BankInfoSecurityTest {

    String BASE_URL = "https://stg-app.bosta.co";
    String TOKEN;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        TOKEN = AuthUtil.generateToken();
    }

    @Test
    public void validBankUpdate() {
        String payload = "{"
                + "\"bankInfo\":{"
                + "\"beneficiaryName\":\"Test User\","
                + "\"bankName\":\"NBG\","
                + "\"ibanNumber\":\"EG1234567890123456789012\","
                + "\"accountNumber\":\"123456\""
                + "},"
                + "\"paymentInfoOtp\":\"123\""
                + "}";

        given()
                .header("Authorization", TOKEN)
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/v2/businesses/add-bank-info")
                .then()
                .statusCode(anyOf(is(200), is(201)));
    }

    @Test
    public void missingToken() {
        given()
                .contentType("application/json") // Prevent 415
                .when()
                .post("/api/v2/businesses/add-bank-info")
                .then()
                .statusCode(401);
    }

    @Test
    public void scriptInjection() {
        String payload = "{"
                + "\"bankInfo\":{"
                + "\"beneficiaryName\":\"<script>alert(1)</script>\","
                + "\"bankName\":\"NBG\","
                + "\"ibanNumber\":\"EG1234567890123456789012\","
                + "\"accountNumber\":\"123456\""
                + "},"
                + "\"paymentInfoOtp\":\"123\""
                + "}";

        given()
                .header("Authorization", TOKEN)
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/api/v2/businesses/add-bank-info")
                .then()
                .statusCode(not(500));
    }
}