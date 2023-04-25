package eu10.spartan.admin;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.baseURI;


@SerenityTest
public class SpartanAdminGetTest {

	@BeforeAll
	public static void init(){
		baseURI = "http://44.199.219.111:7000";
	}

	@Test
	public void getAllSpartan(){

		given().accept(ContentType.JSON)
				.and().auth().basic("admin","admin")
		.when().get("/api/spartans")
		.then().statusCode(200)
				.and().contentType(ContentType.JSON);
	}

}
