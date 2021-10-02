package com.company.peopleapi;

import com.company.base.TestBasePostNewPerson;
import com.company.requests.PutRequest;
import com.company.responses.PostNewPersonResponse;
import com.company.responses.PutRequestResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.company.config.EndPointConfig.POST_ENDPOINT;
import static com.company.config.EndPointConfig.PUT_ENDPOINT;
import static com.company.config.HostNameConfig.HOST_NAME;
import static com.company.utils.ConversionUtils.objectToJsonString;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static com.company.utils.TestDataUtils.ResponseCode.BADREQUEST;
import static com.company.utils.TestDataUtils.ResponseCode.OKAY;
import static com.company.utils.TestDataUtils.ResponseMessage.*;
import static org.apache.http.HttpStatus.*;

public class PutLocationTest extends TestBasePostNewPerson {
    HttpResponse delete;
    String createdID;
    PutRequest updateLocation = new PutRequest();
    PutRequestResponse putRequestResponse;
    String updateLocationAsString;
    String body;


    public PutLocationTest() throws Exception {

    }

    @BeforeClass
    public void beforeClass() throws Exception {

        response = peopleApiClient.httpPost
                (HOST_NAME + POST_ENDPOINT, objectToJsonString(postNewPersonPayload.createNewPerson()));

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
                (HOST_NAME + PUT_ENDPOINT + createdID, updateLocationAsString);

        body = EntityUtils.toString(response.getEntity());
        putRequestResponse = jsonStringToObject(body, PutRequestResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(putRequestResponse.getMessage(), UPDATE_LOCATION);
        Assert.assertEquals(putRequestResponse.getCode(),OKAY);
        Assert.assertEquals(putRequestResponse.getPerson().getLocation(), updateLocation.getLocation());
    }

    @Test
    public void notExistentIdTest() throws Exception {

        incorrectID = "asdah1238123812";
        updateLocation = PutRequest.builder()
                .location("SHutka")
                .build();

        updateLocationAsString = objectToJsonString(updateLocation);
        response = peopleApiClient.httpPut
                (HOST_NAME + PUT_ENDPOINT + incorrectID, updateLocationAsString);

        body = EntityUtils.toString(response.getEntity());
        putRequestResponse = jsonStringToObject(body, PutRequestResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_NOT_FOUND);
        Assert.assertEquals(putRequestResponse.getMessage(), INCORRECT_ID_PART_1 + incorrectID + INCORRECT_ID_PART_2);
    }

    @Test
    public void emptyPayloadLocation() throws Exception {
        updateLocation = PutRequest.builder()
                .location(null)
                .build();

        updateLocationAsString = objectToJsonString(updateLocation);
        response = peopleApiClient.httpPut
                (HOST_NAME + PUT_ENDPOINT + createdID, updateLocationAsString);
        body = EntityUtils.toString(response.getEntity());
        putRequestResponse = jsonStringToObject(body, PutRequestResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(putRequestResponse.getCode(),BADREQUEST);
        Assert.assertEquals(putRequestResponse.getMessage(),EMPTY_BODY);

    }
    @Test
    public void missingLocationField()throws Exception{
        updateLocation = PutRequest.builder()
                .location("")
                .build();

        updateLocationAsString = objectToJsonString(updateLocation);
        response = peopleApiClient.httpPut
                (HOST_NAME + PUT_ENDPOINT + createdID, updateLocationAsString);
        body = EntityUtils.toString(response.getEntity());
        putRequestResponse = jsonStringToObject(body, PutRequestResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(putRequestResponse.getCode(),BADREQUEST);
        Assert.assertEquals(putRequestResponse.getMessage(),EMPTY_BODY);


    }

    @AfterClass
    public void afterClass() throws Exception {
         delete = peopleApiClient.httpDelete(HOST_NAME + PUT_ENDPOINT + createdID);
         Assert.assertEquals(delete.getStatusLine().getStatusCode(),SC_OK);
    }
}
