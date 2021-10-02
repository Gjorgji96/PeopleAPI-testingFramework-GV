package com.company.peopleapi;

import com.company.base.TestBasePostNewPerson;
import com.company.payloads.PostNewPersonPayload;
import com.company.responses.DeleteResponse;
import com.company.responses.PostNewPersonResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.company.config.EndPointConfig.POST_ENDPOINT;
import static com.company.config.EndPointConfig.PUT_ENDPOINT;
import static com.company.config.HostNameConfig.HOST_NAME;
import static com.company.utils.ConversionUtils.objectToJsonString;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static com.company.utils.TestDataUtils.ResponseCode.CREATED;
import static com.company.utils.TestDataUtils.ResponseCode.OKAY;
import static com.company.utils.TestDataUtils.ResponseMessage.*;
import static org.apache.http.HttpStatus.*;

public class DeletePersonTest extends TestBasePostNewPerson {

    String personID;
    DeleteResponse deleteResponse;


    public DeletePersonTest() throws Exception{

    }
    @BeforeClass
    public void beforeClass()throws Exception{
        PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
        response = peopleApiClient.httpPost
                (HOST_NAME + POST_ENDPOINT,
                        objectToJsonString(postNewPersonPayload.createNewPerson()));
        String body = EntityUtils.toString(response.getEntity());
        PostNewPersonResponse bodyAsObject = jsonStringToObject(body,PostNewPersonResponse.class);
        Assert.assertEquals(bodyAsObject.getCode(),CREATED);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_CREATED);

        personID = bodyAsObject.getPersonData().getId();

    }
    @Test
    public void successfullyDeletedPerson() throws Exception{

        response = peopleApiClient.httpDelete(HOST_NAME + PUT_ENDPOINT + personID);
        String body = EntityUtils.toString(response.getEntity());
        deleteResponse = jsonStringToObject(body,DeleteResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_OK);
        Assert.assertEquals(deleteResponse.getCode(),OKAY);
        Assert.assertEquals
                (deleteResponse.getMessage(),INCORRECT_ID_PART_1 + personID + SUCCESSFULLY_DELETED);

    }
    @Test
    public void negativeScenarioTest() throws Exception{

        incorrectID = "12313basdbashd1";
        response = peopleApiClient.httpDelete(HOST_NAME + PUT_ENDPOINT + incorrectID);
        String body = EntityUtils.toString(response.getEntity());

        deleteResponse = jsonStringToObject(body,DeleteResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_NOT_FOUND);
        Assert.assertEquals(deleteResponse.getMessage(),
                INCORRECT_ID_PART_1 + incorrectID + INCORRECT_ID_PART_2);


    }

}
