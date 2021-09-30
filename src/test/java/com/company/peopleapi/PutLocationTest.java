package com.company.peopleapi;

import com.company.PeopleApiClient;
import com.company.payloads.PostNewPersonPayload;
import com.company.payloads.PutLocationPayload;
import com.company.requests.PutRequest;
import com.company.responses.PostNewPersonResponse;
import com.company.responses.PutRequestResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static com.company.utils.ConversionUtils.objectToJsonString;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static org.apache.http.HttpStatus.*;

public class PutLocationTest {
    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    HttpResponse delete;
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    String createdID;
    PostNewPersonResponse postNewPersonResponse;
    PutRequest updateLocation = new PutRequest();
    PutRequestResponse putRequestResponse;
    String updateLocationAsString;
    String body;


    public PutLocationTest() throws Exception {

    }

    @BeforeClass
    public void beforeClass() throws Exception {

        response = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person", objectToJsonString(postNewPersonPayload.createNewPerson()));

        String postResponseBodyAsString = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject
                (postResponseBodyAsString, PostNewPersonResponse.class);
        createdID = postNewPersonResponse.getPersonData().getId();


    }

    @Test
    public void positiveUpdatePersonLocation() throws Exception {

        updateLocation = PutRequest.builder()
                .location("Karposh")
                .build();
        updateLocationAsString = objectToJsonString(updateLocation);
        response = peopleApiClient.httpPut
                ("https://people-api1.herokuapp.com/api/person/" + createdID, updateLocationAsString);

        body = EntityUtils.toString(response.getEntity());
        putRequestResponse = jsonStringToObject(body, PutRequestResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(putRequestResponse.getMessage(), "Person's location succesfully updated !");
        Assert.assertEquals(putRequestResponse.getCode(), "P200");
        Assert.assertEquals(putRequestResponse.getPerson().getLocation(), updateLocation.getLocation());
    }

    @Test
    public void notExistentIdTest() throws Exception {

        String incorrectID = "asdah1238123812";
        updateLocation = PutRequest.builder()
                .location("SHutka")
                .build();
        updateLocationAsString = objectToJsonString(updateLocation);
        response = peopleApiClient.httpPut
                ("https://people-api1.herokuapp.com/api/person/" + incorrectID, updateLocationAsString);
        body = EntityUtils.toString(response.getEntity());
        putRequestResponse = jsonStringToObject(body, PutRequestResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_NOT_FOUND);
        Assert.assertEquals(putRequestResponse.getMessage(), "Person with id=" + incorrectID + " not found");
    }

    @Test
    public void emptyPayloadLocation() throws Exception {
        updateLocation = PutRequest.builder()
                .location(null)
                .build();
        updateLocationAsString = objectToJsonString(updateLocation);
        response = peopleApiClient.httpPut
                ("https://people-api1.herokuapp.com/api/person/" + createdID, updateLocationAsString);
        body = EntityUtils.toString(response.getEntity());
        putRequestResponse = jsonStringToObject(body, PutRequestResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(putRequestResponse.getCode(),"P400");
        Assert.assertEquals(putRequestResponse.getMessage(),"Request body cannot be empty");

    }
    @Test
    public void missingLocationField()throws Exception{
        updateLocation = PutRequest.builder()
                .location("")
                .build();
        updateLocationAsString = objectToJsonString(updateLocation);
        response = peopleApiClient.httpPut
                ("https://people-api1.herokuapp.com/api/person/" + createdID, updateLocationAsString);
        body = EntityUtils.toString(response.getEntity());
        putRequestResponse = jsonStringToObject(body, PutRequestResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(putRequestResponse.getCode(),"P400");
        Assert.assertEquals(putRequestResponse.getMessage(),"Person's location must be provided to be updated !");


    }

    @AfterClass
    public void afterClass() throws Exception {
         delete = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + createdID);
         Assert.assertEquals(delete.getStatusLine().getStatusCode(),SC_OK);
    }
}
