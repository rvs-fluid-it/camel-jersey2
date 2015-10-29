package be.fluid_it.camel.components.jersey2.showcase.service;

import be.fluid_it.µs.bundle.dropwizard.µServiceRule;
import com.jayway.restassured.http.ContentType;
import org.junit.ClassRule;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import org.hamcrest.Matchers;

public class ShowcaseTest {
  @ClassRule
  public static µServiceRule µService = new µServiceRule(ShowcaseService.class);

  @Test
  public void testGet() throws Exception {
    RestAssured.get("/say/hello").then().assertThat().body(Matchers.containsString("Hello World From Route"));
  }

  @Test
  public void testPostJson() throws Exception {
    RestAssured.given().body("{ \"firstName\" : \"Bart\", \"lastName\" : \"Simpson\"}").contentType(ContentType.JSON)
         .then().expect().statusCode(200).and().expect().body("firstName", Matchers.equalTo("Bart")).when().post("/people-json");
  }

  @Test
  public void testPostUrlEncoded() throws Exception {
    RestAssured.given().contentType(ContentType.URLENC).with().parameter("firstName", "Bart").parameter("lastName", "Simpson")
        .post("/people-encoded").then().statusCode(200);
  }

}
