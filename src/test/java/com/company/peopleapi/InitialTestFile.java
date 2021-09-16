package com.company.peopleapi;

import com.company.PeopleApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
public class InitialTestFile {
    PeopleApiClient   peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    HttpResponse getPeople;
    HttpResponse getOnePerson;
    HttpResponse update;
    

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

    public void getPeople(String url)throws Exception{

        getPeople = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");
        String body = EntityUtils.toString(getPeople.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);
        String message = "List of people successfully fetched";

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(message,messageAsString);
    }
}
