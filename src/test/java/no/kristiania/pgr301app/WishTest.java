package no.kristiania.pgr301app;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import no.kristiania.pgr301app.models.Wish;
import no.kristiania.pgr301app.repository.WishRepository;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.post;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WishTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WishRepository wishRepository;

    @BeforeAll
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @BeforeEach
    public void before() {
        wishRepository.deleteAll();
    }

    @Test
    void returnsEmptyArray() {
        get("/wishes").then().assertThat().statusCode(200).body("size()", is(0));
    }

    @Test
    void returnOneFindAll() {
        get("/wishes").then().assertThat().statusCode(200).body("size()", is(0));

        RequestSpecification request = RestAssured.given();
        JSONObject reqParams = new JSONObject();
        reqParams.put("title", "Whatever");
        reqParams.put("url", "Whatever");

        request.body(reqParams.toJSONString());
        request.header("Content-Type", "application/json");

        request.post("/wishes").then().assertThat().statusCode(201);

        get("/wishes").then().assertThat().statusCode(200).body("size()", is(1)).and().body("[0].title", equalTo("Whatever"));
    }

    @Test
    void getOneTest() {
        get("/wishes/1").then().assertThat().statusCode(404);

        RequestSpecification request = RestAssured.given();
        JSONObject reqParams = new JSONObject();
        reqParams.put("title", "Whatever");
        reqParams.put("url", "Whatever");

        request.body(reqParams.toJSONString());
        request.header("Content-Type", "application/json");
        var response = request.post("/wishes");
        var location = response.header("Location");


        var req = RestAssured.given();
        req.baseUri(location);
        req.get().then().assertThat().statusCode(200);


    }



}
