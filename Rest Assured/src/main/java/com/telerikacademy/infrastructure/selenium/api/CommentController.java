package src.main.java.com.telerikacademy.infrastructure.selenium.api;

import com.telerikacademy.infrastructure.selenium.models.Comment;
import com.telerikacademy.infrastructure.selenium.utils.Serializer;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import static com.telerikacademy.infrastructure.selenium.utils.Constants.*;
import static com.telerikacademy.infrastructure.selenium.utils.Endpoints.*;
import static com.telerikacademy.infrastructure.selenium.utils.Serializer.*;


public class CommentController {

    public static Response createComment(Cookies cookies, Comment createComment) {
        String bodyCommentString = Serializer.convertObjectToJsonString(createComment);
        return RestAssured.given()
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .body(bodyCommentString)
                .when()
                .post(CREATЕ_COMMENT_ENDPOINT);
    }
}
