package com.company.peopleapi;

import com.company.PeopleApiClient;
import com.company.payloads.PostNewPersonPayload;
import com.company.responses.PostNewPersonResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.*;
import static com.company.utils.ConversionUtils.objectToJsonString;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;


public class FetchSinglePersonTest {

    HttpResponse response;
    PeopleApiClient peopleApiClient = new PeopleApiClient();
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    PostNewPersonResponse postNewPersonResponse;
    String personId;
    String body;


    public FetchSinglePersonTest() throws Exception{
    }

    @BeforeClass
    public void beforeClass()throws Exception{

        HttpResponse postResponse = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person",objectToJsonString(postNewPersonPayload.createNewPerson()));

        String postResponseBodyAsString = EntityUtils.toString(postResponse.getEntity());
        PostNewPersonResponse postNewPersonResponse = jsonStringToObject
                (postResponseBodyAsString,PostNewPersonResponse.class);
         personId = postNewPersonResponse.getPersonData().getId();;


    }
    @Test
    public void correctIdTest() throws Exception{

        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/person/" + personId);
        body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body,PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);
        Assert.assertEquals(postNewPersonResponse.getCode(),"P200");
        Assert.assertEquals(postNewPersonResponse.getMessage(),"Person succesfully fetched");
//        Assert.assertEquals(postNewPersonResponse.getPersonData(),response);



    }
    @Test
    public void incorrectIdTest() throws Exception{
        String incorrectID = "21n123b1h132h1";
        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/person/" + incorrectID);
        body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body,PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_NOT_FOUND);
        Assert.assertEquals(postNewPersonResponse.getCode(),"P404");
        Assert.assertEquals(postNewPersonResponse.getMessage(),"Person with id " + incorrectID +" not found");

    }
    @AfterClass
    public void afterClass() throws Exception{
        response = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + personId);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);

    }
}
