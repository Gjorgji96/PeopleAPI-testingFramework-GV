package com.company.peopleapi;

import com.company.PeopleApiClient;
import com.company.payloads.PostNewPersonPayload;
import com.company.responses.PostNewPersonResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.*;

import static com.company.utils.ConversionUtils.objectToJsonString;
import static com.company.utils.ConversionUtils.jsonStringToObject;


public class DeletePersonFeatureTest{

    public DeletePersonFeatureTest() throws Exception {
    }

        PeopleApiClient peopleApiClient = new PeopleApiClient();
        HttpResponse response;
        PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
        String createdID;


        @BeforeClass
                public void beforeClass() throws Exception {
            HttpResponse postResponse = peopleApiClient.httpPost
                    ("https://people-api1.herokuapp.com/api/person",objectToJsonString(postNewPersonPayload.createNewPerson()));

            String postResponseBodyAsString = EntityUtils.toString(postResponse.getEntity());
            PostNewPersonResponse postNewPersonResponse = jsonStringToObject
                    (postResponseBodyAsString,PostNewPersonResponse.class);
            String createdId = postNewPersonResponse.getPersonData().getId();

        }

        @BeforeTest
    public void beforeTest(){
    }

    @Test
    public void deletePersonTest() throws Exception{

        response = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + createdID);
        String body = EntityUtils.toString(response.getEntity());

    }

    @AfterTest
    public void afterTest(){
    }

    @AfterClass
    public void afterClass(){

    }

}
