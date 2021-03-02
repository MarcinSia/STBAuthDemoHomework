package github;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthTest {

    private static final String TOKEN = "ffe40f443c32334a15e41c7acf17fe60e2e43c76";

//    @Test
//    public void basicAuth() {
//        given()
//                .auth()
//                .preemptive()
//                .basic("MarcinSia","Ekonometria7!")
//                .when()
//                .get("https://api.github.com/user")
//                .then()
//                .statusCode(200);
//    }
    @Test
    public void bearerToken(){
        given()
                .header("Authorization","Bearer " + TOKEN)
                .get("https://api.github.com/user")
                .then()
                .statusCode(200);

    }

    @Test
    public void oAuth2(){
        given()
                .auth()
                .oauth2(TOKEN)
                .get("https://api.github.com/user")
                .then()
                .statusCode(200);
    }

}
