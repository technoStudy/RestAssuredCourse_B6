import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

public class NationalitiesFunctionTests {

    private RequestSpecification reqSpec;
    private Cookies cookies;
    private String nation_id;

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://demo.mersys.io";

        reqSpec = given()
                .log().body()
                .contentType(ContentType.JSON);

    }

    @Test(priority = 1)
    public void loginTest() {

        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", "richfield.edu");
        credentials.put("password", "Richfield2020!");
        credentials.put("rememberMe", "true");

        cookies = given()
                .spec(reqSpec)
                .body(credentials)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("username", equalTo(credentials.get("username")))
                .extract().detailedCookies();

    }

    @Test(priority = 2)
    public void createNationalityTest() {

        HashMap<String, String> reqBody = new HashMap<>();
        reqBody.put("name", "Halit QA Nation");

        nation_id = given()
                .spec(reqSpec)
                .cookies(cookies)
                .body(reqBody)
                .when()
                .post("/school-service/api/nationality")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id");


    }

    @Test(priority = 3)
    public void createNationalityNegativeTest() {

        HashMap<String, String> reqBody = new HashMap<>();
        reqBody.put("name", "Halit QA Nation");

        given()
                .spec(reqSpec)
                .cookies(cookies)
                .body(reqBody)
                .when()
                .post("/school-service/api/nationality")
                .then()
                .log().body()
                .statusCode(400);

    }

    @Test(priority = 4)
    public void getNationalityTest() { }

    @Test(priority = 5)
    public void updateNationalityTest() { }

    @Test(priority = 6)
    public void deleteNationalityTest() { }

    @Test(priority = 7)
    public void getNationalityNegativeTest() { }

    @Test(priority = 8)
    public void deleteNationalityNegativeTest() { }

}
