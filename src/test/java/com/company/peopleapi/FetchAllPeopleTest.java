package com.company.peopleapi;
import com.company.base.TestBasePostNewPerson;
import com.company.responses.GetAllPeopleResponse;
import org.testng.Assert;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.*;

import static com.company.config.EndPointConfig.GET_ALL_ENDPOINT;
import static com.company.config.HostNameConfig.HOST_NAME;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static com.company.utils.TestDataUtils.ResponseCode.OKAY;
import static com.company.utils.TestDataUtils.ResponseMessage.LIST_OF_PEOPLE;
import static org.apache.http.HttpStatus.SC_OK;

public class FetchAllPeopleTest extends TestBasePostNewPerson {

    public FetchAllPeopleTest() throws Exception {

    }

    @Test
    public void positiveFetchAllPeopleTest() throws Exception {

        response = peopleApiClient.httpGet(HOST_NAME + GET_ALL_ENDPOINT);
        String body = EntityUtils.toString(response.getEntity());

        GetAllPeopleResponse getAllPeopleResponse = jsonStringToObject(body, GetAllPeopleResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(getAllPeopleResponse.getMessage(), LIST_OF_PEOPLE);
        Assert.assertEquals(getAllPeopleResponse.getCode(), OKAY);
        Assert.assertNotNull(getAllPeopleResponse.getNumberOfPeople());
        Assert.assertNotNull(getAllPeopleResponse.getPeopleData().size());
    }

    @Test
    public void numberOfPeopleField() throws Exception {

        response = peopleApiClient.httpGet(HOST_NAME + GET_ALL_ENDPOINT);
        String body = EntityUtils.toString(response.getEntity());

        GetAllPeopleResponse getAllPeopleResponse = jsonStringToObject(body, GetAllPeopleResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(getAllPeopleResponse.getPeopleData().size(), getAllPeopleResponse.getNumberOfPeople());

        }
    }

