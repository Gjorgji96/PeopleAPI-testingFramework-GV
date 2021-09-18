package com.company.peopleapi;

import com.company.PeopleApiClient;
import com.company.payloads.PostNewPersonPayload;
import com.company.requests.PostNewPersonRequest;
import com.company.requests.UpdateLocationRequest;
import com.company.responses.DeleteResponse;
import com.company.responses.PostNewPersonResponse;
import com.company.responses.UpdateLocationResponse;
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
    HttpResponse getPeople;
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

        getPeople = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");
        String body = EntityUtils.toString(getPeople.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);
        String message = "List of people successfully fetched";

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(message,messageAsString);
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
    public void deletePersonTest()throws Exception{

       HttpResponse postResponse = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person",
               objectToJsonString(postNewPersonPayload.createNewPerson()));

       String postResponseAsString = EntityUtils.toString(postResponse.getEntity());
       PostNewPersonResponse postNewPersonResponse = jsonStringToObject
               (postResponseAsString,PostNewPersonResponse.class);
       String createdId = postNewPersonResponse.getPersonData().getId();
       response=peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person" + createdId);
       String body = EntityUtils.toString(response.getEntity());
//       DeleteResponse deleteResponse;
//       deleteResponse = jsonStringToObject(body,DeleteResponse.class);
//       Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);



    }

    @Test

    public void getPostPersonTest() throws Exception{

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
    public void putLocationTest()throws Exception{

        UpdateLocationRequest updateLocationRequest = UpdateLocationRequest.builder()
                .location("Genoa-.no19")
                .build();
        String updateLocationAsString = objectToJsonString(updateLocationRequest);
        response = peopleApiClient.httpPut
                ("https://people-api1.herokuapp.com/api/person/614632f4ef846100040ed678",updateLocationAsString);
        String body = EntityUtils.toString(response.getEntity());
        UpdateLocationResponse updateLocationResponse;
        updateLocationResponse = jsonStringToObject(body,UpdateLocationResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);


    }
}
