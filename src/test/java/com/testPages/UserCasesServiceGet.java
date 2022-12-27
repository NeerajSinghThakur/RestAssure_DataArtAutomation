/**
 * 
 */
package com.testPages;

import com.relevantcodes.extentreports.LogStatus;
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

		Response resp = RestAssured.given().get("https://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1");
		System.out.println("........."+System.getProperty("user.dir"));

	}

	// https://stackoverflow.com/questions/52208765/rest-assured-how-to-pass-object-in-jsonobject-body
	@Test(priority = 1, enabled = true)
	public void accessingUsersByID() {
		eTest.log(LogStatus.FAIL, "has passed.");
		//RestAssured.given().get("http://localhost:3000/posts").then().statusCode(200);
		Response resp = RestAssured.given().get("http://localhost:3000/posts");
		Utils.assertIfEqual(Utils.getStatusCode(resp),200);
		//resp.then().assertThat().statusLine(isEmptyOrNullString());
		resp.getBody().prettyPeek();
		eTest.log(LogStatus.PASS, " has passed.");
		}
	@Test(priority = 1, enabled = true)
	public void test1() {

		//RestAssured.given().get("http://localhost:3000/posts").then().statusCode(200);
		Response resp = RestAssured.given().get("http://localhost:3000/posts");
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
			Response resp = RestAssured.given().get("http://localhost:3000/comments");
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
