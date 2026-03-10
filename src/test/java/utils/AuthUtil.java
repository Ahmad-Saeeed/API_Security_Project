package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class AuthUtil {

    public static String generateToken() {

        RestAssured.baseURI = "https://stg-app.bosta.co";

        Response response =
                given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .when()
                        .post("/api/v2/users/generate-token-for-interview-task")
                        .then()
                        .statusCode(200)   // ensure request succeeds
                        .extract()
                        .response();

        // Extract the token from the correct JSON field
        // Update "token" if the API returns a different field name
        String token = response.jsonPath().getString("token");

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Failed to generate token. Response: " + response.asString());
        }

        return token;
    }
}