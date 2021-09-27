package com.company.peopleapi;
import com.company.PeopleApiClient;
import com.company.responses.GetAllPeopleResponse;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.*;
import static com.company.utils.ConversionUtils.jsonStringToObject;
import static org.apache.http.HttpStatus.SC_OK;

public class FetchAllPeopleTest {

    HttpResponse response;
    PeopleApiClient peopleApiClient = new PeopleApiClient();


    public FetchAllPeopleTest() throws Exception {

    }

    @BeforeClass
    public void beforeClass() {

    }

    @BeforeTest
    public void beforeTest() {

    }

    @Test
    public void positiveFetchAllPeopleTest() throws Exception {

        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");
        String body = EntityUtils.toString(response.getEntity());

        GetAllPeopleResponse getAllPeopleResponse = jsonStringToObject(body, GetAllPeopleResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(getAllPeopleResponse.getMessage(), "List of people successfully fetched");
        Assert.assertEquals(getAllPeopleResponse.getCode(), "P200");
        Assert.assertNotNull(getAllPeopleResponse.getNumberOfPeople());
        Assert.assertNotNull(getAllPeopleResponse.getPeopleData().size());
    }

    @Test
    public void numberOfPeopleField() throws Exception {

        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");
        String body = EntityUtils.toString(response.getEntity());

        GetAllPeopleResponse getAllPeopleResponse = jsonStringToObject(body, GetAllPeopleResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(getAllPeopleResponse.getPeopleData().size(), getAllPeopleResponse.getNumberOfPeople());

    }

    @AfterClass
        public void afterClass () {
        }

        @AfterTest
        public void afterTest () {

        }


    }

