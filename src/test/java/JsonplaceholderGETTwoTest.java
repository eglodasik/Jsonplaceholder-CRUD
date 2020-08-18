import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderGETTwoTest {
    private final String Base_URL = "https://jsonplaceholder.typicode.com/users";

    @Test
    public void jsonplaceholderReadAllUsers() {

        Response response = given()
                .when()
                .get(Base_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        assertEquals(10, names.size());
    }

    @Test
    public void jsonplaceholderReadOneUser() {
        given()
                .when()
                .get(Base_URL+"/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email", equalTo("Sincere@april.biz"))
                .body("address.street", equalTo("Kulas Light"));


    }

    @Test
    public void jsnoplaceholderReadOneUser2(){

        Response response = given()
                .when()
                .get(Base_URL+"/2")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Ervin Howell", json.get("name"));
        assertEquals("Antonette", json.get("username"));
        assertEquals("Shanna@melissa.tv", json.get("email"));
        assertEquals("Victor Plains", json.get("address.street"));
    }


    //PATH VARIABLES
    @Test
    public void jsonplaceholderReadOneUserPathVariable() {
        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get(Base_URL+"/" +"{userId}");

        JsonPath json = response.jsonPath();
        System.out.println(response.asString());
        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Sincere@april.biz", json.get("email"));
        assertEquals("Kulas Light", json.get("address.street"));
    }

    //QUERY PARAM
    @Test
    public  void jsonplaceholderReadOneUserQueryParam() {
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get(Base_URL);

        assertEquals(200, response.statusCode());
        JsonPath json = response.jsonPath();
        assertEquals("Leanne Graham", json.getList("name").get(0));
        assertEquals("Bret", json.getList("username").get(0));
        assertEquals("Sincere@april.biz", json.getList("email").get(0));
        assertEquals("Kulas Light", json.getList("address.street").get(0));
    }
}
