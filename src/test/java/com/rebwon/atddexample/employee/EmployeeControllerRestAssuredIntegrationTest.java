package com.rebwon.atddexample.employee;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.rebwon.atddexample.Application;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
	classes = Application.class
)
@TestPropertySource(
	locations = "classpath:application-integration.properties"
)
public class EmployeeControllerRestAssuredIntegrationTest {
	@Autowired
	private EmployeeRepository repository;

	private RequestSpecification basicRequest;

	@BeforeEach
	void setUp() {
		basicRequest = given()
			.baseUri("http://localhost")
			.port(8080)
		;

		repository.deleteAll();
		repository.save(new Employee("Kim", "Chulsu"));
	}

	@Test
	void shouldReturnDefaultMessageWhenLastNameNotFound() {
		String nonExistingLastName = "nonExistingLastName";
		String expectedMessage = "Who is this " + nonExistingLastName + " you're talking about?";

		given().spec(basicRequest).basePath("/api/hello/" + nonExistingLastName)
			.when().get()
			.then().log().body()
			.statusCode(HttpStatus.OK.value())
			.body(is(expectedMessage));
	}

	@Test
	void shouldReturnGreetingMessageWhenLastNameFound() {
		String existingLastName = "Kim";
		String expectedMessage = "Hello Chulsu Kim!";

		given().spec(basicRequest).basePath("/api/hello/" + existingLastName)
			.when().get()
			.then().log().body()
			.statusCode(HttpStatus.OK.value())
			.body(is(expectedMessage));
	}
}
