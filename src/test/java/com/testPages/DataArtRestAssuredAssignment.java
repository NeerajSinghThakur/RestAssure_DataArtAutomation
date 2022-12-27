package com.testPages;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.TestBase;

import java.util.*;

import static org.hamcrest.Matchers.*;

public class DataArtRestAssuredAssignment  extends TestBase {


    //Get all posts. Verify HTTP response status code and content type.
    @Test
    void test1() {
        RestAssured.baseURI = "http://localhost:3000/";
        RestAssured.given().get("/posts").then().statusCode(200).contentType("application/json");

    }

    //Get all comments and verify response charset.
    @Test
    public void test2() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/comments");
        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();
        String contentType = response.header("Content-Type");
//Verify charset
        Assert.assertEquals(contentType /* actual value */, "application/json; charset=utf-8" /* expected value */);

        // Let us print the id
        System.out.println("id received from Response " + jsonPathEvaluator.get("id"));
        List<String> listDest = new ArrayList<>();
        listDest.addAll((Collection<? extends String>) jsonPathEvaluator.get("id"));
        Assert.assertEquals(listDest.size(), 500);

        // Let us print the email
        System.out.println("email received from Response " + jsonPathEvaluator.get("email"));
        List<String> listemail = new ArrayList<>();
        listDest.addAll((Collection<? extends String>) jsonPathEvaluator.get("email"));
        for (String abc : listemail) {
            Assert.assertTrue(abc.contains("@"));
        }
    }


    // Get third album (path parameter) and verify content length.
    @Test
    public void test3() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/albums");
        JsonPath jsonPathEvaluator = response.jsonPath();

        List<String> result1 = jsonPathEvaluator.getList("findAll{it.id=10}");
        System.out.println("result1 is: " + result1);
        // calculate id count
        System.out.println("id received from Response " + jsonPathEvaluator.get("id"));
        List<String> listDest = new ArrayList<>();
        listDest.addAll((Collection<? extends String>) jsonPathEvaluator.get("id"));
        System.out.println("list size is :" + listDest.size());
        Assert.assertEquals(listDest.size(), 100);

        // Let us check title value for 3rd parameter
        System.out.println("email received from Response " + jsonPathEvaluator.get("title"));
        List<String> listtitle = new ArrayList<>();
        listtitle.addAll((Collection<? extends String>) jsonPathEvaluator.get("title"));
        for (int i = 0; i < listDest.size(); i++) {
            Assert.assertEquals(listtitle.get(2), "omnis laborum odio");
        }
    }


    //Get all photos and verify that content length header is absent in response.
    @Test
    public void test4() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/photos");
        JsonPath jsonPathEvaluator = response.jsonPath();

        // calculate id count
        System.out.println("id received from Response " + jsonPathEvaluator.get("id"));
        List<String> list_photocount = new ArrayList<>();
        list_photocount.addAll((Collection<? extends String>) jsonPathEvaluator.get("id"));
        System.out.println("list size is :" + list_photocount.size());
        Assert.assertEquals(list_photocount.size(), 5000);
    }


    //Verify response time for photos, endpoint is less than 10 seconds.
    @Test
    public void test5() {
        RestAssured.baseURI = "http://localhost:3000/";
        long millis = RestAssured.get("/photos").time();
        System.out.println("time is:" + millis);
        RestAssured.given().accept(ContentType.JSON).get("/photos").then().statusCode(200).and().contentType(ContentType.JSON).and().time(lessThan(10000L));

    }

    //Get all users. Verify HTTP response status code. Verify the 5th user geo coordinates.
    @Test()
    public void test6() {
        RestAssured.baseURI = "http://localhost:3000/";
        RestAssured.given().accept(ContentType.JSON).get("/users").then().statusCode(200).and().contentType(ContentType.JSON).body("[0].address.geo.lat", containsString("-31.8129")).body("[0].address.geo.lng", containsString("62.5342"));
    }


    // Get non-existing album. Verify HTTP response status code.
    @Test
    public void test7() {
        RestAssured.baseURI = "http://localhost:3000/";
        RestAssured.given().accept(ContentType.JSON).get("/albums").then().statusCode(404);
    }


    //Verify HTTP status code and completion status of the 10th task.
    @Test
    public void test8() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/todos");
        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println("staus code:" + response.statusCode());
        if (response.statusCode() == 200) {

            // Let us check completion status for 10rd parameter
            System.out.println("completed status received from Response " + jsonPathEvaluator.get("completed"));
            List<Boolean> listcompleted = new ArrayList<>();
            listcompleted.addAll((Collection<? extends Boolean>) jsonPathEvaluator.get("completed"));
            for (int i = 0; i < listcompleted.size(); i++) {
                Assert.assertEquals(listcompleted.get(9).booleanValue(), true);
            }
        } else System.out.println("there is some issue in api response");
    }


    //Get user by street name. Verify HTTP status code. Verify street field of returned user record.
    @Test
    public void test9() {
        RestAssured.baseURI = "http://localhost:3000/";
        RestAssured.basePath = "/users";
        Response response1 = RestAssured.given().contentType(ContentType.JSON).log().all().get("/2");
        System.out.println("status code:" + response1.statusCode());
        if (response1.statusCode() == 200) {
            String street = RestAssured.given().contentType(ContentType.JSON).log().all().get("/2").then().extract().path("address.street");
            System.out.println("Street namevalue is:" + street);
            if (street.equals("Victor Plains")) {
                String name = RestAssured.given().contentType(ContentType.JSON).log().all().get("/2").then().extract().path("name");
                System.out.println("name namevalue is:" + name);
                Assert.assertEquals(name, "Ervin Howell");

                String username = RestAssured.given().contentType(ContentType.JSON).log().all().get("/2").then().extract().path("username");
                System.out.println("username value is:" + username);
                Assert.assertEquals(username, "Antonette");

                String email = RestAssured.given().contentType(ContentType.JSON).log().all().get("/2").then().extract().path("email");
                System.out.println("email value is:" + email);
                Assert.assertEquals(email, "Shanna@melissa.tv");

                String phone = RestAssured.given().contentType(ContentType.JSON).log().all().get("/2").then().extract().path("phone");
                System.out.println("phone value is:" + phone);
                Assert.assertEquals(phone, "010-692-6593 x09125");

                String website = RestAssured.given().contentType(ContentType.JSON).log().all().get("/2").then().extract().path("website");
                System.out.println("website value is:" + website);
                Assert.assertEquals(website, "anastasia.net");


                LinkedHashMap<String, String> company = RestAssured.given().contentType(ContentType.JSON).log().all().get("/2").then().extract().path("company");
                for (String a : company.keySet()) {
                    List<String> value = Collections.singletonList(company.get(a));
                    System.out.println("value is:" + value);
                }
            } else {
                System.out.println("issue in server");
            }

        }

    }

    //Get comments with postId sorted in descending order. Verify HTTP response status code. Verify that records are sorted in response.
    @Test
    public void test11() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/comments");
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Let us print the PostId variable to see what we got
        System.out.println("PostId received from Response " + jsonPathEvaluator.get("postId"));
        List<String> listDest = new ArrayList<>();
        listDest.addAll((Collection<? extends String>) jsonPathEvaluator.get("Id"));
        Assert.assertEquals(listDest.size(), 500);
        if (response.statusCode() == 200) {
            List<String> body = new ArrayList<>();
            body.addAll((Collection<? extends String>) jsonPathEvaluator.get("body"));
            for (String bodycomment : body) {
                System.out.println("body comment is: " + bodycomment);

            }
        } else System.out.println("issue in api response");
    }


    //Get photos from the third album. Verify HTTP response status code. Verify that only photos from third album are returned.
    @Test
    public void test12() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/photos");
        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println("staus code:" + response.statusCode());
        if (response.statusCode() == 200) {

            // Let us check completion status for 3rd parameter
            System.out.println("ThumbnailsUrl received from Response " + jsonPathEvaluator.get("thumbnailUrl"));
            List<String> photos = new ArrayList<>();
            photos.addAll((Collection<? extends String>) jsonPathEvaluator.get("thumbnailUrl"));
            for (int i = 0; i < photos.size(); i++) {

                Assert.assertEquals(photos.get(2), "https://via.placeholder.com/150/24f355");
                //    response.prettyPrint();
            }
        } else System.out.println("there is some issue in api response");

    }


    //Get user by city name. Verify HTTP response status code. Verify user with proper city is returned.
    @Test
    public void test13() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/users");
        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println("staus code:" + response.statusCode());
        if (response.statusCode() == 200) {
            // Let us check completion status for 3rd parameter
            System.out.println("Address city received from Response " + jsonPathEvaluator.get("address.city"));
            List<String> photos = new ArrayList<>();
            photos.addAll((Collection<? extends String>) jsonPathEvaluator.get("address.city"));
            //    Assert.assertEquals(photos.get(1),"Wisokyburgh");
            if (photos.get(2).contains("Wisokyburgh")) {
                String username = RestAssured.given().contentType(ContentType.JSON).log().all().get("/2").then().extract().path("username");
                System.out.println("username value is:" + username);
                Assert.assertEquals(username, "Antonette");
            } else {
                System.out.println("getting wrong username from response");
            }
        } else System.out.println("there is some issue in api response");

    }


    //Get only first 10 posts. Verify HTTP response status code. Verify that only first posts are returned.
    @Test
    public void test14() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/posts");
        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println("staus code:" + response.statusCode());
        if (response.statusCode() == 200) {

            List<String> result1 = jsonPathEvaluator.getList("findAll{it.id==1|it.id==2|it.id==3|it.id==4|it.id==5|it.id==6|it.id==7|it.id==8|it.id==9|it.id==10}");
            System.out.println("result1 is: " + result1);
            System.out.println(" ");


        }
    }

    //	Get posts with id = 55 and id = 60. Verify HTTP response status code. Verify id values of returned records.
    @Test
    public void test15() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/posts");
        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println("staus code:" + response.statusCode());
        if (response.statusCode() == 200) {

            List<String> result1 = jsonPathEvaluator.getList("findAll{it.id==55|it.id==56}");
            System.out.println("result1 is: " + result1);
            System.out.println(" ");


        }
    }


    //Get photos from first album in range from 20th to 25th. Verify HTTP response status code. Verify returned album and photo ids.
    @Test
    public void test16() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/photos");
        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println("staus code:" + response.statusCode());
        if (response.statusCode() == 200) {

            List<String> result1 = jsonPathEvaluator.getList("findAll{it.id>=20 & it.id<=25}");
            System.out.println("result1 is: " + result1);
            System.out.println(" ");


        }
    }

    //Get tenth user. Verify HTTP response status code. Verify response against JSON schema.
    @Test
    public void test17() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/users");
        JsonPath jsonPathEvaluator = response.jsonPath();
        System.out.println("staus code:" + response.statusCode());
        if (response.statusCode() == 200) {

            List<String> result1 = jsonPathEvaluator.getList("findAll{it.id=10}");
            System.out.println("result1 is: " + result1);
            System.out.println(" ");


        }
    }

    //Create already existing comment entity. Verify HTTP response status code.
    @Test
    public void test18() {
        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("comments").body("  {\n" + "    \"postId\": 1,\n" + "    \"id\": 505,\n" + "    \"name\": \"id ankurlabore ex et quam laborum\",\n" + "    \"email\": \"ankurEliseo@gardner.biz\",\n" + "    \"body\": \"ankurlaudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\n" + "  }").contentType("application/json").post().then().log().all().statusCode(500);
    }

    //Create a post. Verify HTTP response status code.
    @Test
    public void test19() {

        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("664/posts").body("{\n" + "    \"userId\": 1,\n" + "    \"id\": 1,\n" + "    \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" + "    \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" + "  }").contentType("application/json").post().then().statusCode(401);
    }

    //Register new user. Verify HTTP response status code. Verify that access token is present is response body.
    @Test
    public void test20() {
        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("register").body("{\n" + "    \"id\": 13,\n" + "\"access_token\":\"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3\",\n" + "    \"token_type\":\"Bearer\",\n" + "\"expires_in\":3600,\n" + "\"refresh_token\":\"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk\",\n" + "\"scope\":\"create\",\n" + "    \"name\": \"Ankur Rana\",\n" + "    \"username\": \"ankur\",\n" + "    \"email\": \"ankurrana12@april.biz\",\n" + "    \"password\": \"ankurrana\",\n" + "    \"address\": {\n" + "      \"street\": \"Kulas Light\",\n" + "      \"suite\": \"Apt. 556\",\n" + "      \"city\": \"Gwenborough\",\n" + "      \"zipcode\": \"92998-3874\",\n" + "      \"geo\": {\n" + "        \"lat\": \"-37.3159\",\n" + "        \"lng\": \"81.1496\"\n" + "      }\n" + "    },\n" + "    \"phone\": \"1-770-736-8031 x56442\",\n" + "    \"website\": \"hildegard.org\",\n" + "    \"company\": {\n" + "      \"name\": \"Romaguera-Crona\",\n" + "      \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" + "      \"bs\": \"harness real-time e-markets\"\n" + "    }\n" + "  }").contentType("application/json").post().then().log().all().statusCode(201);
    }


    //Create post with adding access token in header. Verify HTTP response status code. Verify post is created.
    @Test
    public void test21() {
        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("664/posts").body("  {\n" + "    \"userId\": 11,\n" + "    \"id\": 1,\n" + "    \"title\": \"at nam consequatur ea labore ea harum\",\n" + "    \"body\": \"cupiditate quo est a modi nesciunt soluta\\nipsa voluptas error itaque dicta in\\nautem qui minus magnam et distinctio eum\\naccusamus ratione error aut\"\n" + "  }").contentType("application/json").post().then().log().all().statusCode(201);

    }

    //Create post entity and verify that the entity is created. Verify HTTP response status code. Use JSON in body.
    @Test
    public void test22() {
        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("posts").body(" {\n" + "    \"userId\": 10,\n" + "    \"id\": 101,\n" + "    \"title\": \"ankur ratione ex tenetur perferendis\",\n" + "    \"body\": \"ankur aut et excepturi dicta laudantium sint rerum nihil\\nlaudantium et at\\na neque minima officia et similique libero et\\ncommodi voluptate qui\"\n" + "  }").contentType("application/json").post().then().statusCode(201);

    }

    //Update non-existing entity. Verify HTTP response status code.
    @Test
    public void test23() {
        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("posts/10989").header("Content-Type", "application/json").body("{\n" + "  \"userId\": 99991,\n" + "  \"id\": 1999,\n" + "  \"title\": \" aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" + "  \"body\": \"ankurquia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" + "}").when().put().then().log().all().assertThat().statusCode(404);

    }


    //Create post entity and update the created entity. Verify HTTP response status code and verify that the entity is updated.
    @Test
    public void test24() throws InterruptedException {
        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("posts").body(" {\n" + "    \"userId\": 1011,\n" + "    \"id\": 10119,\n" + "    \"title\": \"ankur Rana ratione ex tenetur perferendis\",\n" + "    \"body\": \"ankur Rana aut et excepturi dicta laudantium sint rerum nihil\\nlaudantium et at\\na neque minima officia et similique libero et\\ncommodi voluptate qui\"\n" + "  }").contentType("application/json").post().then().assertThat().statusCode(201);


        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("posts/10119").header("Content-Type", "application/json").body(" {\n" + "  \"userId\": 1011,\n" + "  \"id\": 10119,\n" + "  \"title\": \"ankur Rana 123 ratione ex tenetur perferendis\",\n" + "  \"body\": \"ankur aakriti Rana aut et excepturi dicta laudantium sint rerum nihil\\nlaudantium et at\\na neque minima officia et similique libero et\\ncommodi voluptate qui\"\n" + "}").when().put().then().log().all().assertThat().statusCode(200);
    }


    //Delete non-existing post entity. Verify HTTP response status code.
    @Test
    public void test25() {
        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("posts/10167").header("Content-Type", "application/json").body(" {\n" + "  \"userId\": 10113,\n" + "  \"id\": 1011932,\n" + "  \"title\": \"ankur Rana 123 ratione ex tenetur perferendis\",\n" + "  \"body\": \"ankur aakriti Rana aut et excepturi dicta laudantium sint rerum nihil\\nlaudantium et at\\na neque minima officia et similique libero et\\ncommodi voluptate qui\"\n" + "}").when().delete().then().log().all().assertThat().statusCode(404);
    }

    //Create post entity, update the created entity, and delete the entity. Verify HTTP response status code and verify that the entity is deleted.
    @Test
    public void test26() {
        //create a request
        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("posts").body(" {\n" + "    \"userId\": 10,\n" + "    \"id\": 102,\n" + "    \"title\": \"ankur Rana new request\",\n" + "    \"body\": \"ankur Rana aut et excepturi dicta laudantium sint rerum nihil\\nlaudantium et at\\na neque minima officia et similique libero et\\ncommodi voluptate qui\"\n" + "  }").contentType("application/json").post().then().assertThat().statusCode(201);

        //update request
        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("posts/102").header("Content-Type", "application/json").body(" {\n" + "  \"userId\": 10,\n" + "  \"id\": 102,\n" + "  \"title\": \"ankur Rana 101 request\",\n" + "  \"body\": \"ankur aakriti Rana aut et excepturi dicta laudantium sint rerum nihil\\nlaudantium et at\\na neque minima officia et similique libero et\\ncommodi voluptate qui\"\n" + "}").when().put().then().log().all().assertThat().statusCode(200);

        //delete request
        RestAssured.given().log().all().baseUri("http://localhost:3000/").basePath("posts/102").header("Content-Type", "application/json").body(" {\n" + "  \"userId\": 10,\n" + "  \"id\": 102,\n" + "  \"title\": \"ankur Rana 101 request\",\n" + "  \"body\": \"ankur aakriti Rana aut et excepturi dicta laudantium sint rerum nihil\\nlaudantium et at\\na neque minima officia et similique libero et\\ncommodi voluptate qui\"\n" + "}").when().delete().then().log().all().assertThat().statusCode(200);
    }


    @Test
    public void ValidateBookHeaders() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("comments");
// Access header with a given name. Header = Content-Type
        String contentType = response.header("Content-Type");
        Assert.assertEquals(contentType /* actual value */, "application/json; charset=utf-8" /* expected value */);

    }

    @Test
    void restTest3() {
        RestAssured.baseURI = "http://localhost:3000/";
        RestAssured.given().get("/albums").then().statusCode(200).body("[2].id", equalTo(3));
    }


    @Test
    public void DisplayAllNodesInWeatherAPI() {
        RestAssured.baseURI = "http://localhost:3000/";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/comments");

        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Let us print the city variable to see what we got
        System.out.println("City received from Response " + jsonPathEvaluator.get("id"));
        List<String> listDest = new ArrayList<>();
        listDest.addAll((Collection<? extends String>) jsonPathEvaluator.get("id"));
        Assert.assertEquals(listDest.size(), 500);

        List<String> listemail = new ArrayList<>();
        listDest.addAll((Collection<? extends String>) jsonPathEvaluator.get("email"));
        for (String abc : listemail) {
            Assert.assertTrue(abc.contains("@"));

        }

        //  var  v=jsonPathEvaluator.get("id");
        // Assert.assertEquals();

        // Print the temperature node
        System.out.println("Temperature received from Response " + jsonPathEvaluator.get("name"));
    }


}

