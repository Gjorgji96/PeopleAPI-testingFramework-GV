package com.company.peopleapi;

import com.company.PeopleApiClient;
import com.company.payloads.PostNewPersonPayload;
import com.company.requests.PostNewPersonRequest;
import com.company.requests.PutRequest;
import com.company.responses.GetAllPeopleResponse;
import com.company.responses.PostNewPersonResponse;
import com.company.responses.PutRequestResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import  static com.company.utils.ConversionUtils.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class InitialTestFile {
    PeopleApiClient   peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    HttpResponse getOnePerson;
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    PostNewPersonRequest postNewPersonRequest = new PostNewPersonRequest();

    public InitialTestFile() throws Exception {
    }


    @Test

    public void getWelcomeRequestTest() throws Exception{

       response = peopleApiClient.httpGet("https://people-api1.herokuapp.com");
       String message = "Welcome to People API";

       String body = EntityUtils.toString(response.getEntity());
       JSONObject bodyAsObject = new JSONObject(body);

       String messageAsString = bodyAsObject.get("message").toString();

       Assert.assertEquals(message,messageAsString);

    //sporeduvanje so JSONObject e polosh(selski)nacin
    }
    @Test
    public void getPeopleTest()throws Exception{

        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");
        String body = EntityUtils.toString(response.getEntity());

        GetAllPeopleResponse getAllPeopleResponse =  jsonStringToObject(body,GetAllPeopleResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);
        Assert.assertEquals(getAllPeopleResponse.getCode(),"P200");
        Assert.assertEquals(getAllPeopleResponse.getNumberOfPeople(),40);



    }

    @Test
    public void getSinglePersonTest()throws Exception{

        getOnePerson = peopleApiClient.httpGet
                ("https://people-api1.herokuapp.com/api/person/613f3cc8efc41e00046091c5");
        String body = EntityUtils.toString(getOnePerson.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);
        String message = "Person succesfully fetched";

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(message,messageAsString);

    }

    @Test

    public void PostPersonTest() throws Exception{

        postNewPersonRequest= postNewPersonPayload.createNewPerson();
        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person",newPersonPayloadAsString);
        String body = EntityUtils.toString(response.getEntity());
        PostNewPersonResponse postNewPersonResponse;
        postNewPersonResponse = jsonStringToObject(body,PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_CREATED);


    }

    @Test
    public void putRequestTest()throws Exception{

        PutRequest putRequest = PutRequest.builder()
                .location("FKMGJP")
                .build();
        String updateLocationAsString = objectToJsonString(putRequest);
        response = peopleApiClient.httpPut
                ("https://people-api1.herokuapp.com/api/person/613f3cc8efc41e00046091c5",updateLocationAsString);
        String body = EntityUtils.toString(response.getEntity());
        PutRequestResponse putRequestResponse;
        putRequestResponse = jsonStringToObject(body, PutRequestResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);


    }
}
