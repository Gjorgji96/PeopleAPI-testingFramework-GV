package com.company.peopleapi;

import com.company.PeopleApiClient;
import com.company.payloads.PostNewPersonPayload;
import com.company.responses.DeleteResponse;
import com.company.responses.PostNewPersonResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.company.utils.ConversionUtils.objectToJsonString;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static org.apache.http.HttpStatus.*;

public class DeletePersonTest {
    HttpResponse response;
    PeopleApiClient peopleApiClient = new PeopleApiClient();
    PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    String personID;
    DeleteResponse deleteResponse;


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

        personID = bodyAsObject.getPersonData().getId();

    }
    @Test
    public void successfullyDeletedPerson() throws Exception{

        response = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + personID);
        String body = EntityUtils.toString(response.getEntity());
        deleteResponse = jsonStringToObject(body,DeleteResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);
        Assert.assertEquals(deleteResponse.getCode(),"P200");
        Assert.assertEquals
                (deleteResponse.getMessage(),"Person with id=" + personID + " has been succesfully deleted");

    }
    @Test
    public void negativeScenarioTest() throws Exception{

        String incorrectID = "12313basdbashd1";
        response = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + incorrectID);
        String body = EntityUtils.toString(response.getEntity());
        deleteResponse = jsonStringToObject(body,DeleteResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_NOT_FOUND);
        Assert.assertEquals(deleteResponse.getMessage(),"Person with id=" + incorrectID + " not found");


    }

}
