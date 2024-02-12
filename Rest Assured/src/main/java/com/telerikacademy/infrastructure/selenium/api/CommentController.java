package src.main.java.com.telerikacademy.infrastructure.selenium.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import src.main.java.com.telerikacademy.infrastructure.selenium.models.Comment;
import com.telerikacademy.infrastructure.selenium.utils.Serializer;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import java.util.Map;

import static com.telerikacademy.infrastructure.selenium.utils.Constants.*;
import static com.telerikacademy.infrastructure.selenium.utils.Endpoints.*;
import static io.restassured.RestAssured.given;

public class CommentController {

    private static RequestSpecification givenConfiguredRequest(Cookies cookies) {
        return given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .log().all();
    }

    public static Response createComment(Cookies cookies, Comment createComment) {
        String bodyCommentString = Serializer.convertObjectToJsonString(createComment);
        return given()
                .spec(givenConfiguredRequest(cookies))
                .body(bodyCommentString)
                .when()
                .post(CREATЕ_COMMENT_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response deleteComment(Cookies cookies, int commentId) {
        return given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam(COMMENT_ID, commentId)
                .when()
                .delete(DELETE_COMMENT_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response editComment(Cookies cookies, int commentId, String updatedUniqueContent, Map<String, Object> additionalParams) {
        RequestSpecification requestSpec = given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam(COMMENT_ID, commentId)
                .queryParam("content", updatedUniqueContent);

        if (additionalParams != null) {
            additionalParams.forEach(requestSpec::queryParam);
            }
        return requestSpec
                .when()
                .put(EDIT_COMMENT_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
        }

    public static Response findAllComments(Cookies cookies) {
        return given()
                .spec(givenConfiguredRequest(cookies))
                .when()
                .get(FIND_ALL_COMMENTS_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response findAllCommentsOfAPost(Cookies cookies, int postId) {
        return given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam(POST_ID, postId)
                .when()
                .get(FIND_ONE_COMMENT_OF_A_POST_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response findOneCommentOfAPost(Cookies cookies, int commentId) {
        return given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam(COMMENT_ID, commentId)
                .when()
                .get(FIND_ONE_COMMENT_OF_A_POST_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response LikeDislikeComment(Cookies cookies, int commentId) {
        return given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam(COMMENT_ID, commentId)
                .when()
                .post(LIKE_COMMENT_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
