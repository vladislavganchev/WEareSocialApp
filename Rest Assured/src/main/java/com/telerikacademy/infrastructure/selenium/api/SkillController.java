package src.main.java.com.telerikacademy.infrastructure.selenium.api;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import src.main.java.com.telerikacademy.infrastructure.selenium.models.Skill;
import com.telerikacademy.infrastructure.selenium.utils.Serializer;

import static com.telerikacademy.infrastructure.selenium.utils.Constants.*;
import static com.telerikacademy.infrastructure.selenium.utils.Endpoints.*;
import static io.restassured.RestAssured.given;

public class SkillController {

    private static RequestSpecification givenConfiguredRequest(Cookies cookies) {
        return given()
                .baseUri(BASE_URL)
                .cookies(cookies)
                .contentType(APPLICATION_JSON)
                .log().all();
    }

    public static Response createSkill(Cookies cookies, Skill model) {
        String bodySkillString = Serializer.convertObjectToJsonString(model);
        return given()
                .spec(givenConfiguredRequest(cookies))
                .body(bodySkillString)
                .when()
                .post(SKILL_CREATE_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response deleteSkill(int skillId) {
        return given()
                .baseUri(BASE_URL)
                .queryParam("skillId", skillId)
                .when()
                .put(SKILL_DELETE_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response getAllSkills(Cookies cookies) {
        return given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam("sorted", "true")
                .when()
                .get(SKILL_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response getOneSkillById(Cookies cookies, int skillId) {
        return given()
                .spec(givenConfiguredRequest(cookies))
                .queryParam("skillId", skillId)
                .when()
                .get(SKILL_GET_ONE_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response updateOneSkill(Cookies cookies, Skill skillToUpdate) {
        String bodySkillString = Serializer.convertObjectToJsonString(skillToUpdate);
        return given()
                .spec(givenConfiguredRequest(cookies))
                .body(bodySkillString)
                .queryParam("skillId", skillToUpdate.skillId)
                .when()
                .put(SKILL_UPDATE_ENDPOINT)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
