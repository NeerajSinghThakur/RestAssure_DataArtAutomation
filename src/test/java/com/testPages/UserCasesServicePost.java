/**
 * 
 */
package com.testPages;

import org.testng.annotations.Test;
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
public class UserCasesServicePost extends TestBase {

	String RandomeString=getSaltString();
	@Test(priority = 3)
	public void CreatingANewUser() {
		JSONObject request = new JSONObject();
		request.put("firstName", "F"+RandomeString);
		request.put("lastName", "L"+RandomeString);
		request.put("email", RandomeString+"@gmail.com");

		System.out.println(request);
		System.out.println(request.toString());

		given()
		.contentType("application/json")
		.body(request.toJSONString()).
		when().
		post("http://localhost:8080/users").
		then().statusCode(201);


	}
	@Test(priority = 3)
	public void CreatingexistenceUser() {
		JSONObject request = new JSONObject();
		request.put("firstName", "F"+RandomeString);
		request.put("lastName", "L"+RandomeString);
		request.put("email", RandomeString+"@gmail.com");

		System.out.println(request);
		System.out.println(request.toString());

		given()
		.contentType("application/json")
		.body(request.toJSONString()).
		when().
		post("http://localhost:8080/users").
		then().statusCode(400);


	}
	
	
	
	
}
