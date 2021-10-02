package com.company.peopleapi;

import com.company.base.TestBasePostNewPerson;
import com.company.responses.PostNewPersonResponse;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.testng.Assert;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.*;

import static com.company.config.EndPointConfig.POST_ENDPOINT;
import static com.company.config.EndPointConfig.PUT_ENDPOINT;
import static com.company.config.HostNameConfig.HOST_NAME;
import static com.company.utils.ConversionUtils.objectToJsonString;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static com.company.utils.TestDataUtils.ResponseMessage.*;
import static com.company.utils.TestDataUtils.ResponseCode.*;
import static org.apache.http.HttpStatus.*;

public class PostNewPersonTest extends TestBasePostNewPerson {


    String person1ID;
    String person2ID;
    String person3ID;

    public PostNewPersonTest() throws Exception {
    }

    @Test
    public void Positive1Test() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost
                (HOST_NAME + POST_ENDPOINT, newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        person1ID = postNewPersonResponse.getPersonData().getId();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), CREATED);
        Assert.assertEquals(postNewPersonResponse.getMessage(), PERSON_SUCCESSFULLY_INSERTED);
        Assert.assertEquals(postNewPersonResponse.getPersonData().getName(), postNewPersonRequest.getName());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getSurname(), postNewPersonRequest.getSurname());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getAge(), postNewPersonRequest.getAge());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getIsEmployed(), postNewPersonRequest.getIsEmployed());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getLocation(), postNewPersonRequest.getLocation());

    }

    @Test
    public void positiveTestWithoutLocation() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        postNewPersonRequest.setLocation(null);

        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                (HOST_NAME + POST_ENDPOINT, newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        person2ID = postNewPersonResponse.getPersonData().getId();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), CREATED);
        Assert.assertEquals(postNewPersonResponse.getMessage(), PERSON_SUCCESSFULLY_INSERTED);
        Assert.assertEquals(postNewPersonResponse.getPersonData().getLocation(), postNewPersonRequest.getLocation());


    }

    @Test
    public void positiveTestWithoutAge() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        postNewPersonRequest.setAge(null);

        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                (HOST_NAME + POST_ENDPOINT, newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);
        person3ID = postNewPersonResponse.getPersonData().getId();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), CREATED);
        Assert.assertEquals(postNewPersonResponse.getMessage(), PERSON_SUCCESSFULLY_INSERTED);
        Assert.assertNull(postNewPersonResponse.getPersonData().getAge());


    }

    @Test
    public void negativeTestWithoutName() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        postNewPersonRequest.setName(null);

        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                (HOST_NAME + POST_ENDPOINT, newPersonPayloadAsString);
        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(), BADREQUEST);
        Assert.assertEquals(postNewPersonResponse.getMessage(), NO_NAME);



    }
    @Test
    public void negativeTestWithoutSurname() throws Exception{
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        postNewPersonRequest.setSurname(null);

        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                (HOST_NAME + POST_ENDPOINT, newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(), BADREQUEST);
        Assert.assertEquals(postNewPersonResponse.getMessage(), NO_SURNAME);


    }
    @Test
    public void negativeTestWithoutJob () throws Exception{
        postNewPersonRequest = postNewPersonPayload.createNewPerson();
        postNewPersonRequest.setIsEmployed(null);

        newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);
        response = peopleApiClient.httpPost
                (HOST_NAME + POST_ENDPOINT, newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());
        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(),BADREQUEST );
        Assert.assertEquals(postNewPersonResponse.getMessage(), EMPTY_EMPLOYED_FIELD);

    }

    @Test
    public void negativeTestWithWrongEmployedInput() throws Exception{
        JSONObject isEmployed = postNewPersonPayload.createNewPersonPayloadEmployedAsString();
        isEmployed.toString();
        String expectedMessage = "Person validation failed: isEmployed:";

        response = peopleApiClient.httpPost
                (HOST_NAME + POST_ENDPOINT, isEmployed.toString());
        String body = EntityUtils.toString(response.getEntity());

        JSONObject bodyAsObject = new JSONObject(body);
        String messageAsString = bodyAsObject.get("message").toString();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_INTERNAL_SERVER_ERROR);
        boolean passes = messageAsString.contains(expectedMessage);
        Assert.assertEquals(true,passes);


    }

    @AfterClass
    public void afterCLass() throws Exception {
        HttpResponse deletingPerson1 = peopleApiClient.httpDelete(HOST_NAME + PUT_ENDPOINT + person1ID);
        Assert.assertEquals(deletingPerson1.getStatusLine().getStatusCode(), SC_OK);

        HttpResponse deletingPerson2 = peopleApiClient.httpDelete(HOST_NAME + PUT_ENDPOINT + person2ID);
        Assert.assertEquals(deletingPerson2.getStatusLine().getStatusCode(), SC_OK);

        HttpResponse deletingPerson3 = peopleApiClient.httpDelete(HOST_NAME + PUT_ENDPOINT + person3ID);
        Assert.assertEquals(deletingPerson3.getStatusLine().getStatusCode(), SC_OK);
    }

}

