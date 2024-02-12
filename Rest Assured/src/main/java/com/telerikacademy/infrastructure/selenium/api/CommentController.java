package src.main.java.com.telerikacademy.infrastructure.selenium.api;

import src.main.java.com.telerikacademy.infrastructure.selenium.models.Comment;
import com.telerikacademy.infrastructure.selenium.utils.Serializer;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import static com.telerikacademy.infrastructure.selenium.utils.Constants.*;
import static com.telerikacademy.infrastructure.selenium.utils.Endpoints.*;

public class CommentController {

    public static Response createComment(Cookies cookies, Comment createComment) {
        String bodyCommentString = Serializer.convertObjectToJsonString(createComment);
        return RestAssured.given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .body(bodyCommentString)
                .when()
                .post(CREATЕ_COMMENT_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    public static Response deleteComment(Cookies cookies, int commentId) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .queryParam(COMMENT_ID, commentId)
                .when()
                .delete(DELETE_COMMENT_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    public static Response editComment(Cookies cookies, int commentId, String updatedUniqueContent) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .queryParam(COMMENT_ID, commentId)
                .queryParam("content", updatedUniqueContent)
                .when()
                .put(EDIT_COMMENT_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    public static Response findAllComments(Cookies cookies) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .when()
                .get(FIND_ALL_COMMENTS_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    public static Response findAllCommentsOfAPost(Cookies cookies, int postId) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .queryParam(POST_ID, postId)
                .when()
                .get(FIND_ALL_COMMENTS_OF_A_POST_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    public static Response findOneCommentOfAPost(Cookies cookies, int commentId) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .queryParam(COMMENT_ID, commentId)
                .when()
                .get(FIND_ONE_COMMENT_OF_A_POST_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    public static Response LikeDislikeComment(Cookies cookies, int commentId) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .queryParam(COMMENT_ID, commentId)
                .when()
                .post(LIKE_COMMENT_ENDPOINT)
                .then()
                .extract()
                .response();
    }
}
