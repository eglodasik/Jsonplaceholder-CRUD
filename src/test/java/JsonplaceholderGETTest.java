import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;

public class JsonplaceholderGETTest {

    @Test
    public void jsonplaceholderReadAllUsers() {

        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");

        Assertions.assertEquals(10, names.size());
    }

    @Test
    public void jsonplaceholderReadOneUser() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/1");

        Assertions.assertEquals(200, response.statusCode());


    }

    //PATH VARIABLES
    @Test
    public void jsonplaceholderReadOneUserPathVariable() {
        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get("https://jsonplaceholder.typicode.com/users/{userId}");

        JsonPath json = response.jsonPath();
        System.out.println(response.asString());
        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));
    }

    //QUERY PARAM
    @Test
    public  void jsonplaceholderReadOneUserQueryParam() {
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        Assertions.assertEquals(200, response.statusCode());
        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Leanne Graham", json.getList("name").get(0));
        Assertions.assertEquals("Bret", json.getList("username").get(0));
        Assertions.assertEquals("Sincere@april.biz", json.getList("email").get(0));
        Assertions.assertEquals("Kulas Light", json.getList("address.street").get(0));
    }
}
