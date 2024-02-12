package src.main.java.com.telerikacademy.infrastructure.selenium.api;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import src.main.java.com.telerikacademy.infrastructure.selenium.models.SendRequest;

import static com.telerikacademy.infrastructure.selenium.utils.Constants.*;
import static com.telerikacademy.infrastructure.selenium.utils.Endpoints.*;
import com.telerikacademy.infrastructure.selenium.utils.Serializer;
import static io.restassured.RestAssured.given;

public class ConnectionController {

    private static RequestSpecification givenConfiguredRequest(Cookies cookies) {
        return given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .log().all();
    }

    public static Response sendRequest(SendRequest sendRequestToUser, Cookies cookies, String senderUsername) {
        String bodySendRequest = Serializer.convertObjectToJsonString(sendRequestToUser);

        return given()
                .spec(givenConfiguredRequest(cookies))
                .header("name", senderUsername)
                .body(bodySendRequest)
                .when()
                .post(SEND_REQUEST_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response getUserRequests(Cookies cookies, int receiverUserId){
        return given()
                .spec(givenConfiguredRequest(cookies))
                .pathParam("receiverUserId", receiverUserId)
                .when()
                .get(GET_REQUESTS_ENDPOINT.replace("{receiverUserId}", String.valueOf(receiverUserId)))
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response approveRequest(Cookies cookies, int receiverUserId, int requestId){
        return  given()
                .spec(givenConfiguredRequest(cookies))
                .pathParam("receiverUserId", receiverUserId)
                .queryParam("requestId", requestId)
                .when()
                .post(APPROVE_REQUEST_ENDPOINT.replace("{receiverUserId}", String.valueOf(receiverUserId)))
                .then()
                .log().all()
                .extract()
                .response();
    }
}
