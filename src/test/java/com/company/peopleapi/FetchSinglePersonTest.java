package com.company.peopleapi;

import com.company.base.TestBasePostNewPerson;
import com.company.responses.PostNewPersonResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.company.config.EndPointConfig.POST_ENDPOINT;
import static com.company.config.EndPointConfig.PUT_ENDPOINT;
import static com.company.config.HostNameConfig.HOST_NAME;
import static com.company.utils.ConversionUtils.objectToJsonString;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static com.company.utils.TestDataUtils.ResponseCode.NOTFOUND;
import static com.company.utils.TestDataUtils.ResponseCode.OKAY;
import static com.company.utils.TestDataUtils.ResponseMessage.*;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;


public class FetchSinglePersonTest extends TestBasePostNewPerson {

    String personId;
    String body;

    public FetchSinglePersonTest() throws Exception{
    }

    @BeforeClass
    public void beforeClass()throws Exception{

        response = peopleApiClient.httpPost
                (HOST_NAME + POST_ENDPOINT,objectToJsonString(postNewPersonPayload.createNewPerson()));

        String postResponseBodyAsString = EntityUtils.toString(response.getEntity());
        PostNewPersonResponse postNewPersonResponse = jsonStringToObject
                (postResponseBodyAsString,PostNewPersonResponse.class);
         personId = postNewPersonResponse.getPersonData().getId();;


    }
    @Test
    public void correctIdTest() throws Exception{

        response = peopleApiClient.httpGet(HOST_NAME + PUT_ENDPOINT  + personId);
        body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body,PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);
        Assert.assertEquals(postNewPersonResponse.getCode(),OKAY);
        Assert.assertEquals(postNewPersonResponse.getMessage(),PERSON_FETCHED);

    }
    @Test
    public void incorrectIdTest() throws Exception{

        incorrectID = "21n123b1h132h1";
        response = peopleApiClient.httpGet(HOST_NAME + PUT_ENDPOINT  + incorrectID);
        body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body,PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_NOT_FOUND);
        Assert.assertEquals(postNewPersonResponse.getCode(),NOTFOUND);
        Assert.assertEquals(postNewPersonResponse.getMessage(),
                INCORRECT_ID_PART_1 + incorrectID + INCORRECT_ID_PART_2);

    }
    @AfterClass
    public void afterClass() throws Exception{
        response = peopleApiClient.httpDelete(HOST_NAME + PUT_ENDPOINT + personId);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);

    }
}
