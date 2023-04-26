package eu10.spartan.editor;

import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utilities.SpartanNewBase;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.SerenityRest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.SpartanUtil;

import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.baseURI;
import static net.serenitybdd.rest.SerenityRest.given;

@SerenityTest
public class SpartanEditorPostTest extends SpartanNewBase {


	@Test
	public void postSpartanAsEditor(){

		Map<String, Object> bodyMap = SpartanUtil.getRandomSpartanMap();

		System.out.println(bodyMap);

		given().auth().basic("editor", "editor")
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(bodyMap)
				.when().post("/spartans")
				.prettyPrint();

		/*
                status code is 201
                content type is Json
                success message is A Spartan is Born!
                id is not null
                name is correct
                gender is correct
                phone is correct

                check location header ends with newly generated id
         */

		Ensure.that("status code is 201",p->p.statusCode(201));
		Ensure.that("content type is JSON",p->p.contentType(ContentType.JSON));
		Ensure.that("success message is A Spartan is Born!",p->p.body("success",is("A Spartan is Born!")));
		Ensure.that("id is not null",p->p.body("data.id",notNullValue()));
		Ensure.that("name is correct",p->p.body("data.name",is(bodyMap.get("name"))));
		Ensure.that("gender is correct",p->p.body("data.gender",is(bodyMap.get("gender"))));
		Ensure.that("phone is correct",p->p.body("data.phone",is(bodyMap.get("phone"))));


		String id = lastResponse().jsonPath().getString("data.id");

		Ensure.that("check location header ends with newly generated id",p->p.header("Location",endsWith(id)));

	}

	@ParameterizedTest(name = "New Spartan {index} - name: {0}")
	@CsvFileSource(resources = "/SpartanData.csv",numLinesToSkip = 1)
	public void postSpartansWithCSV(String name, String gender, long phone){

		System.out.println("name = " + name);
		System.out.println("gender = " + gender);
		System.out.println("phone = " + phone);

		Map<String, Object> bodyMap = new HashMap<>();

		bodyMap.put("name", name);
		bodyMap.put("gender", gender);
		bodyMap.put("phone", phone);


		given().auth().basic("editor", "editor")
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(bodyMap)
				.when().post("/spartans")
				.prettyPrint();


		Ensure.that("status code is 201",p->p.statusCode(201));
		Ensure.that("content type is JSON",p->p.contentType(ContentType.JSON));
		Ensure.that("success message is A Spartan is Born!",p->p.body("success",is("A Spartan is Born!")));
		Ensure.that("id is not null",p->p.body("data.id",notNullValue()));
		Ensure.that("name is correct",p->p.body("data.name",is(bodyMap.get("name"))));
		Ensure.that("gender is correct",p->p.body("data.gender",is(bodyMap.get("gender"))));
		Ensure.that("phone is correct",p->p.body("data.phone",is(bodyMap.get("phone"))));


		String id = lastResponse().jsonPath().getString("data.id");

		Ensure.that("check location header ends with newly generated id",p->p.header("Location",endsWith(id)));
	}

}

















