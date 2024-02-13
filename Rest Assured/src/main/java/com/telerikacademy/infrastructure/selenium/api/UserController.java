package src.main.java.com.telerikacademy.infrastructure.selenium.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.Cookies;
import src.main.java.com.telerikacademy.infrastructure.selenium.models.*;
import src.main.java.com.telerikacademy.infrastructure.selenium.models.UserRegister;
import com.telerikacademy.infrastructure.selenium.utils.Serializer;

import static com.telerikacademy.infrastructure.selenium.utils.Constants.*;
import static com.telerikacademy.infrastructure.selenium.utils.Endpoints.*;
import static io.restassured.RestAssured.given;

public class UserController {

    private static RequestSpecification givenConfiguredRequest(Cookies cookies) {
        RequestSpecification specification = given()
                .baseUri(BASE_URL)
                .contentType(APPLICATION_JSON)
                .log().all();

        if (cookies != null) {
            specification.cookies(cookies);
        }
        return specification;
    }

    public static Response registerUser(UserRegister user) {
        String bodyUserString = Serializer.convertObjectToJsonString(user);
        return givenConfiguredRequest(null)
                .body(bodyUserString)
                .when()
                .post(USERS_ENDPOINT)
                .then().log().body().extract().response();
    }

    public static Response getUsers(Page page) {
        String bodyPageString = Serializer.convertObjectToJsonString(page);
        return givenConfiguredRequest(null)
                .body(bodyPageString)
                .when()
                .post(GET_USERS_BY_NAME_ENDPOINT)
                .then().log().all().extract().response();
    }

    public static Response getUserByName(Cookies cookies, SearchUser user) {
        String bodyGetUserByName = Serializer.convertObjectToJsonString(user);
        return givenConfiguredRequest(cookies)
                .body(bodyGetUserByName)
                .when()
                .post(GET_USERS_BY_NAME_ENDPOINT)
                .then().log().all().extract().response();
    }

    public static Response updatePersonalProfile(Cookies cookies, UserPersonalInfo userPersonal, int currentUserId) {
        String bodyUpdatedPersonalProfileString = Serializer.convertObjectToJsonString(userPersonal);
        return givenConfiguredRequest(cookies)
                .pathParam("currentUserId", currentUserId)
                .body(bodyUpdatedPersonalProfileString)
                .when()
                .post("/api/users/auth/{currentUserId}/personal")
                .then().log().all().extract().response();
    }

    public static Response updateExpertiseProfile(Cookies cookies, ExpertiseProfile expertiseProfile, int currentUserId) {
        String bodyExpertiseProfileString = Serializer.convertObjectToJsonString(expertiseProfile);
        return givenConfiguredRequest(cookies)
                .body(bodyExpertiseProfileString)
                .when()
                .post(UPDATE_ENDPOINT + currentUserId + "/expertise")
                .then().log().all().extract().response();
    }

    public static Response getUserById(String currentUsername, int currentUserId) {
        return givenConfiguredRequest(null)
                .when()
                .get(UPDATE_ENDPOINT + currentUserId)
                .then().log().all().extract().response();
    }

    public static Response getProfilePosts(Cookies cookies, int currentUserId) {
        Page page = new Page();
        page.size = 10;
        String bodyPageString = Serializer.convertObjectToJsonString(page);
        return givenConfiguredRequest(cookies)
                .pathParam("currentUserId", currentUserId)
                .body(bodyPageString)
                .when()
                .get(GET_PROFILE_POSTS_ENDPOINT)
                .then().log().all().extract().response();
    }
}
