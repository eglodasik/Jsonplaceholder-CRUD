import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderPUTPATCHTest {

    private static Faker faker;
    private String fakeEmail;

    @BeforeAll
    public static void beforeAll(){
         faker = new Faker();
    }

    @BeforeEach
    public void beforeEach(){

        fakeEmail = faker.internet().emailAddress();

    }

    @Test
    public void JsonplaceholderPUTTest(){

        String fakeName = faker.name().name();
        String fakeUsername = faker.name().username();
        String fakePhone = faker.phoneNumber().phoneNumber();
        String fakeWebsite = faker.internet().url();

        JSONObject user = new JSONObject();
        user.put("name", fakeName);
        user.put("username", fakeUsername);
        user.put("email", fakeEmail);
        user.put("phone", fakePhone);
        user.put("website", fakeWebsite);


        JSONObject geo = new JSONObject();
        geo.put("lat", "-37.3159");
        geo.put("lng", "81.1496");

        JSONObject address = new JSONObject();
        address.put("street", "Kulas Light");
        address.put("suite", "Apt. 556");
        address.put("city", "Gwenborough");
        address.put("zipcode", "92998-3874");
        address.put("geo", geo);

        user.put("address", address);

        JSONObject company = new JSONObject();
        company.put("name", "Romaguera-Crona");
        company.put("catchPhrase", "Multi-layered client-server neural-net");
        company.put("bs", "harness real-time e-markets");
        user.put("company", company);

        System.out.println(user.toString());


        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .put("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakeName, json.get("name"));
        assertEquals(fakeUsername, json.get("username"));
        assertEquals(fakeEmail, json.get("email"));

    }

    @Test
    public void JsonplaceholderPATCHTest(){

        String jsonBody = "{\n" +
                "    \"email\": \"bartek@patch.pl\"\n" +
                "  }";

        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .patch("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("bartek@patch.pl", json.get("email"));
    }

}
