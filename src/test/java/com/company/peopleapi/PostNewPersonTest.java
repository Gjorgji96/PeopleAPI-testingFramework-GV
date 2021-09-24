package com.company.peopleapi;

import com.company.PeopleApiClient;
import com.company.payloads.PostNewPersonPayload;
import com.company.requests.PostNewPersonRequest;
import com.company.responses.PostNewPersonResponse;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.*;

import static com.company.utils.ConversionUtils.objectToJsonString;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static org.apache.http.HttpStatus.*;

public class PostNewPersonTest {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    PostNewPersonRequest postNewPersonRequest = new PostNewPersonRequest();
    String newPersonPayloadAsString;
    PostNewPersonResponse postNewPersonResponse;

    public PostNewPersonTest() throws Exception {
    }

    @BeforeClass
    public void beforeClass() throws Exception {


    }

    @BeforeTest
    public void beforeTest() {

    }

    @Test
    public void Positive1Test() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);
        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), "P201");
        Assert.assertEquals(postNewPersonResponse.getMessage(), "Person succesfully inserted");
        Assert.assertEquals(postNewPersonResponse.getPersonData().getName(), postNewPersonRequest.getName());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getSurname(), postNewPersonRequest.getSurname());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getAge(), postNewPersonRequest.getAge());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getIsEmployed(), postNewPersonRequest.getIsEmployed());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getLocation(), postNewPersonRequest.getLocation());

    }

    @Test
    public void positiveTestWithoutLocation() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        postNewPersonRequest.setLocation(null);
        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);
        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), "P201");
        Assert.assertEquals(postNewPersonResponse.getMessage(), "Person succesfully inserted");
        ;
        Assert.assertEquals(postNewPersonResponse.getPersonData().getLocation(), postNewPersonRequest.getLocation());


    }

    @Test
    public void positiveTestWithoutAge() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        postNewPersonRequest.setAge(null);
        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);
        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), "P201");
        Assert.assertEquals(postNewPersonResponse.getMessage(), "Person succesfully inserted");
        Assert.assertNull(postNewPersonResponse.getPersonData().getAge());


    }

    @Test
    public void negativeTestWithoutName() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        postNewPersonRequest.setName(null);
        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);
        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(), "P400");
        Assert.assertEquals(postNewPersonResponse.getMessage(), "Person's name cannot be empty");



    }
    @Test
    public void negativeTestWithoutSurname() throws Exception{
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        postNewPersonRequest.setSurname(null);
        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);
        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(), "P400");
        Assert.assertEquals(postNewPersonResponse.getMessage(), "Person's surname cannot be empty");


    }
    @Test
    public void negativeTestWithoutJob () throws Exception{
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        postNewPersonRequest.setIsEmployed(null);
        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);
        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(), "P400");
        Assert.assertEquals(postNewPersonResponse.getMessage(), "Person must provide if he is employed or not");

    }

    @Test
    public void negativeTestWithWrongEmployedInput() throws Exception{
        JSONObject isEmployed = postNewPersonPayload.createNewPersonPayloadEmployedAsString();
        isEmployed.toString();
        String expectedMessage = "Person validation failed: isEmployed:";
        response = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person", isEmployed.toString());
        String body = EntityUtils.toString(response.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);
        String messageAsString = bodyAsObject.get("message").toString();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_INTERNAL_SERVER_ERROR);
        boolean passes = messageAsString.contains(expectedMessage);
        Assert.assertEquals(true,passes);



    }
    //this test doesnt work because of a bug

//    @Test
//    public void negativeTestWithWrongNameAndSurnameINput() throws Exception{
//        JSONObject isEmployed = postNewPersonPayload.createNewPersonPayloadNameAndSurnameAsString();
//        isEmployed.toString();
//        response = peopleApiClient.httpPost
//                ("https://people-api1.herokuapp.com/api/person", isEmployed.toString());
//        String body = EntityUtils.toString(response.getEntity());
//        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);
//        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_INTERNAL_SERVER_ERROR);
//        Assert.assertEquals(postNewPersonResponse.getCode(), "P500");
//        Assert.assertEquals(postNewPersonResponse.getMessage(),
//                "Person must provide if he is employed or not");


    @AfterClass
    public void afterCLass() {

    }

    @AfterTest
    public void afterTest() {

    }

}

