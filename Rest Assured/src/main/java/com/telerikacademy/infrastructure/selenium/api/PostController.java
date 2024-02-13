package src.main.java.com.telerikacademy.infrastructure.selenium.api;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import src.main.java.com.telerikacademy.infrastructure.selenium.models.Post;

import static com.telerikacademy.infrastructure.selenium.utils.Constants.*;
import static com.telerikacademy.infrastructure.selenium.utils.Endpoints.*;
import static io.restassured.RestAssured.given;
import com.telerikacademy.infrastructure.selenium.utils.Serializer;
public class PostController {
    //Common method to prepare a RequestSpecification with common settings
    private static RequestSpecification givenConfiguredRequest(Cookies cookies) {
        return given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .log().all();
    }

    public static Response createPost(Cookies cookies, Post post) {
        String bodyPostString = Serializer.convertObjectToJsonString(post);
        return given()
                .spec(givenConfiguredRequest(cookies))
                .body(bodyPostString)
                .when()
                .post(CREATЕ_POST_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response deletePost(Cookies cookies, int postId) {
        return given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam("postId", postId) // Using the constant POST_ID for consistency
                .when()
                .delete(DELETE_POST_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response editPost(Cookies cookies, Post editedPost) {
        String bodyEditPostString = Serializer.convertObjectToJsonString(editedPost);
        return given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam("postId", editedPost.postId)
                .body(bodyEditPostString)
                .when()
                .put(EDIT_POST_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response getNewsFeed(Cookies cookies) {
        return given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam("sorted", true)
                .when()
                .get(GET_ALL_POSTS_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response likeAndDislikePost(Cookies cookies, int postId) {
        return given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam("postId", postId) // Using the constant POST_ID for consistency
                .when()
                .post(LIKE_POST_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
