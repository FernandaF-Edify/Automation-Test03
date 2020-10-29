import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AutomationTest {

    @Test
    public void StatusValidationTest01() {
        when()
                .get("https://api-coffee-testing.herokuapp.com/v1/examen/status")
                .then()
                .statusCode(200)
                .header("Access-Control-Allow-Origin", equalTo("http://localhost"))
                .body("status", equalTo("Listos para el examen"));
    }

    @Test
    public void PutNameWithoutBodyTest02() {
        when()
                .put("https://api-coffee-testing.herokuapp.com/v1/examen/updateName")
                .then()
                .statusCode(406)
                .body("message", equalTo("Name was not provided"));
    }

    @Test
    public void RandomNameWithoutAuthTest03() {
        when()
                .get("https://api-coffee-testing.herokuapp.com/v1/examen/randomName")
                .then()
                .statusCode(401)
                .body("message", equalTo("Please login first"));
    }

    private Response result;

    @Test
    public void RandomUserValidationTest04() {
        Response response = given().auth()
                .basic("testuser", "testpass")
                .when()
                .get("https://api-coffee-testing.herokuapp.com/v1/examen/randomName")
                .then()
                .statusCode(200)
                .extract()
                .response();

        result = response;
        System.out.println(result.body().print());
        Assert.assertEquals(200, response.getStatusCode(), "Please review the login credentials");

    }

    @Test
    public void SearchSameUserTest04() {
        JSONObject jsonObj = new JSONObject(result.body().print());
        System.out.println(jsonObj.get("name"));

        given()
                .body(jsonObj.toString())
                .when()
                .post("https://api-coffee-testing.herokuapp.com/v1/examen/sameName")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

}
