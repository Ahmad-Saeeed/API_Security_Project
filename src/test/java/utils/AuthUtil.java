package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class AuthUtil {

    public static String generateToken() {

        Response response =
                given()
                        .when()
                        .post("https://stg-app.bosta.co/api/v2/users/generate-token-for-interview-task");

        return response.jsonPath().getString("token");
    }
}