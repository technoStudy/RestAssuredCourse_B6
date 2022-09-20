import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class GoRestUserFunction {

    private RequestSpecification reqSpec;
    private Object user_id;
    private HashMap<String, String> requestBody;

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://gorest.co.in";

        reqSpec = given()
                .log().body()
                .header("Authorization", "Bearer a970f0a198c804bfd481ad5fd1420649fdc33f0a25dfaa96fae1cde5f0a94637")
                .contentType(ContentType.JSON);

        requestBody = new HashMap<>();
        requestBody.put("name", "Test User Name");
        requestBody.put("email", "testuser@gorest.com");
        requestBody.put("gender", "male");
        requestBody.put("status", "active");

    }


    @Test
    public void createUserTest() {

        user_id = given()
                .spec(reqSpec)
                .body(requestBody)
                .when()
                .post("/public/v2/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", equalTo(requestBody.get("name")))
                .extract().path("id");

    }


    @Test(dependsOnMethods = "createUserTest")
    public void createUserNegativeTest() {

        given()
                .spec(reqSpec)
                .body(requestBody)
                .when()
                .post("/public/v2/users")
                .then()
                .log().body()
                .statusCode(422);

    }


    @Test(dependsOnMethods = "createUserNegativeTest")
    public void editUserTest() {

        HashMap<String, String> reqBodyForUpdate = new HashMap<>();
        reqBodyForUpdate.put("name", "newTestUser name");

        given()
                .spec(reqSpec)
                .body(reqBodyForUpdate)
                .when()
                .put("/public/v2/users/" + user_id)
                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(reqBodyForUpdate.get("name")));

    }


    @Test
    public void deleteUserTest() {

        given()
                .when()
                .then();

    }

}
