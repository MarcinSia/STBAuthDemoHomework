package trello;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import trello.base.BaseTest;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class OrganizationTest extends BaseTest {

    private String organizationId;

    private static Stream<Arguments> createOrganizationData() {

        return Stream.of(
                Arguments.of("This is display name","Akademia QA is awesome!","akademia qa","https://akademiaqa.pl"),
                Arguments.of("This is display name","Akademia QA is awesome!","akademia qa","http://akademiaqa.pl"),
                Arguments.of("This is display name","Akademia QA is awesome!","aqa","http://akademiaqa.pl"),
                Arguments.of("This is display name","Akademia QA is awesome!","akademia_qa","http://akademiaqa.pl"),
                Arguments.of("This is display name","Akademia QA is awesome!","akademiaqa123","http://akademiaqa.pl"));
    }

    private static Stream<Arguments> createOrganizationInvalidData() {
        return Stream.of(
                Arguments.of("This is display name", "Description", "xx", "https://akademiaqa.pl"),
                Arguments.of("This is display name", "Description", "NAME", "https://akademiaqa.pl"),
                Arguments.of("This is display name", "Description", "!@#$%^&*", "https://akademiaqa.pl"),
                Arguments.of("This is display name", "Description", "name", "123"));
    }

    @DisplayName("Create organization with valid data")
    @ParameterizedTest(name = "Display name: {0}, desc: {1}, name: {2}, website: {3}")
    @MethodSource("createOrganizationData")
    public void createOrganization(String displayName, String desc, String name, String website){

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", displayName)
                .queryParam("desc", desc)
                .queryParam("name", name)
                .queryParam("website", website)
                .when()
                .post(BASE_URL + "/" + ORGANIZATIONS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("displayName")).isEqualTo(displayName);

        organizationId = json.getString("id");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + "/" + ORGANIZATIONS + "/" + organizationId)
                .then()
                .statusCode(200);
    }

    @DisplayName("Create organization with invalid data")
    @ParameterizedTest(name = "Display name: {0}, desc: {1}, name: {2}, website: {3}")
    @MethodSource("createOrganizationInvalidData")
    public void createOrganizationInvalidData(String displayName, String desc, String name, String website) {

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", displayName)
                .queryParam("desc", desc)
                .queryParam("name", name)
                .queryParam("website", website)
                .when()
                .post(BASE_URL + "/" + ORGANIZATIONS)
                .then()
                .statusCode(400)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("displayName")).isEqualTo(displayName);

        organizationId = json.getString("id");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + "/" + ORGANIZATIONS + "/" + organizationId)
                .then()
                .statusCode(200);
    }

}
