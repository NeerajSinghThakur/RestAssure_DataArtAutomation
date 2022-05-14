/**
 * 
 */
package com.testPages;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.specification.RequestSpecification;

import net.minidev.json.JSONObject;
import utilities.TestBase;
import utilities.Utils;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import static com.jayway.restassured.RestAssured.*;
public class UserCasesServiceGet extends TestBase {

	 @Test(enabled = true)
	public void accessingUsers() {

		Response resp = RestAssured.given().get("http://localhost:8080/users");
		System.out.println("........."+System.getProperty("user.dir"));

	}

	// https://stackoverflow.com/questions/52208765/rest-assured-how-to-pass-object-in-jsonobject-body
	@Test(priority = 1, enabled = true)
	public void accessingUsersByID() {
		
		RestAssured.given().get("http://localhost:8080/users/2").then().statusCode(200);
		Response resp = RestAssured.given().get("http://localhost:8080/users/2");
		Utils.assertIfEqual(Utils.getStatusCode(resp),200);
		//resp.then().assertThat().statusLine(isEmptyOrNullString());
		resp.getBody().prettyPeek();
		}
	@Test(priority = 1, enabled = true)
	public void accessingUsersCases() {
		
		RestAssured.given().get("http://localhost:8080/users/2/cases").then().statusCode(200);
		Response resp = RestAssured.given().get("http://localhost:8080/users/2/cases");
		Utils.assertIfEqual(Utils.getStatusCode(resp),200);
		resp.getBody().prettyPeek();
	}

	@Test(priority = 1, enabled = true)
	public void accessingOpenCases() {
		
		RestAssured.given().get("http://localhost:8080/users/2/cases/status/OPEN").then().statusCode(200);
		Response resp = RestAssured.given().get("http://localhost:8080/users/2/cases/status/OPEN");
		Utils.assertIfEqual(Utils.getStatusCode(resp),200);
		resp.getBody().prettyPeek();
	}
	
	@Test(priority = 1, enabled = true)
	public void accessingClosedCases() {
		
		RestAssured.given().get("http://localhost:8080/users/2/cases/status/CLOSED").then().statusCode(200);
		Response resp = RestAssured.given().get("http://localhost:8080/users/2/cases/status/CLOSED");
		Utils.assertIfEqual(Utils.getStatusCode(resp),200);
		resp.getBody().prettyPeek();
	}
	@Test(priority = 1, enabled = true)
	public void accessingUsersCaseByID() {
		
		RestAssured.given().get("http://localhost:8080/users/2/cases/3").then().statusCode(200);
		Response resp = RestAssured.given().get("http://localhost:8080/users/2/cases/3");
		Utils.assertIfEqual(Utils.getStatusCode(resp),200);
		resp.getBody().prettyPeek();
	}
	
	@Test(priority = 2, enabled = true)
	public void accessingAllClosedCases() {
		RestAssured.baseURI = "http://localhost:8080/cases/status/CLOSED";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/");

		// Retrieve the body of the Response
		ResponseBody body = response.getBody();

		// To check for sub string presence get the Response body as a String.
		// Do a String.contains
		String bodyAsString = body.asString();
		AssertJUnit.assertEquals(bodyAsString.contains("CLOSED"), true);

	}
	

	@Test(priority = 2, enabled = true)
	public void accessingAllOpenCases() {
		RestAssured.baseURI = "http://localhost:8080/cases/status/OPEN";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/");

		// Retrieve the body of the Response
		ResponseBody body = response.getBody();

		// To check for sub string presence get the Response body as a String.
		// Do a String.contains
		String bodyAsString = body.asString();
		AssertJUnit.assertEquals(bodyAsString.contains("OPEN"), true);
	
	}
	
	
}
