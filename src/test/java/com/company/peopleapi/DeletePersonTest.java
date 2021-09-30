package com.company.peopleapi;

import com.company.PeopleApiClient;
import com.company.payloads.PostNewPersonPayload;
import com.company.responses.PostNewPersonResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.company.utils.ConversionUtils.objectToJsonString;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static org.apache.http.HttpStatus.SC_CREATED;

public class DeletePersonTest {
    HttpResponse response;
    PeopleApiClient peopleApiClient = new PeopleApiClient();
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();


    public DeletePersonTest() throws Exception{

    }
    @BeforeClass
    public void beforeClass()throws Exception{
        PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
        response = peopleApiClient.httpPost
                ("https://people-api1.herokuapp.com/api/person",
                        objectToJsonString(postNewPersonPayload.createNewPerson()));
        String body = EntityUtils.toString(response.getEntity());
        PostNewPersonResponse bodyAsObject = jsonStringToObject(body,PostNewPersonResponse.class);
        Assert.assertEquals(bodyAsObject.getCode(),"P201");
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_CREATED);

        String personID = bodyAsObject.getPersonData().getId();

    }
    @Test
    public void successfullyDeletedPerson() throws Exception{


    }
}
