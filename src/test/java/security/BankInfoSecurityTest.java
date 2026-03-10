package security;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AuthUtil;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BankInfoSecurityTest {

    String BASE_URL = "https://stg-app.bosta.co";
    String TOKEN = AuthUtil.generateToken();

    @BeforeClass
    public void setup(){
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void validBankUpdate(){

        String payload = "{"
                + "\"bankInfo\":{"
                + "\"beneficiaryName\":\"Test User\","
                + "\"bankName\":\"NBG\","
                + "\"ibanNumber\":\"EG123456789\","
                + "\"accountNumber\":\"123\""
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
    public void missingToken(){

        given()
                .when()
                .post("/api/v2/businesses/add-bank-info")
                .then()
                .statusCode(401);
    }

    @Test
    public void scriptInjection(){

        String payload = "{"
                + "\"bankInfo\":{"
                + "\"beneficiaryName\":\"<script>alert(1)</script>\","
                + "\"bankName\":\"NBG\","
                + "\"ibanNumber\":\"EG123456789\","
                + "\"accountNumber\":\"123\""
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