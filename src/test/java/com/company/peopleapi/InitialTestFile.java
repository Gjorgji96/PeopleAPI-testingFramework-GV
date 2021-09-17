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
    @Test
    public void getPeopleTest()throws Exception{

        getPeople = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");
        String body = EntityUtils.toString(getPeople.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);
        String message = "List of people successfully fetched";

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(message,messageAsString);
    }

    @Test
    public void getSinglePersonTest()throws Exception{

        getOnePerson = peopleApiClient.httpGet
                ("https://people-api1.herokuapp.com/api/person/613f3cc8efc41e00046091c5");
        String body = EntityUtils.toString(getOnePerson.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);
        String message = "Person succesfully fetched";

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(message,messageAsString);

    }

    @Test
    public void deletePersonTest()throws Exception{

        response = peopleApiClient.httpDelete
                ("https://people-api1.herokuapp.com/api/person/6144936f2723c70004cafd6c");
        String body = EntityUtils.toString(response.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);


        String message = "Person with id=6144936f2723c70004cafd6c has been succesfully deleted";
        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(message,messageAsString);
    }

    @Test

    public void getPostPersonTest() throws Exception{

        JSONObject payload = new JSONObject();
        payload.put("name","gjorgji");
        payload.put("surname","papito");
        payload.put("age", 24);
        payload.put("isEmployed", true);
        payload.put("location", "Skopje");

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person",payload);
        String body = EntityUtils.toString(response.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);
        String message = "Person succesfully inserted";

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(message,messageAsString);

    }

    @Test
    public void putLocationTest()throws Exception{

        JSONObject bodyAsObject = new JSONObject();
        bodyAsObject.put("location","Island");
        response=peopleApiClient.httpPut
                ("https://people-api1.herokuapp.com/api/person/613f3cc8efc41e00046091c5",bodyAsObject);

        String body = EntityUtils.toString(response.getEntity());
        JSONObject message = new JSONObject(body);
        String poraka = "Person's location succesfully updated !";
        String messageAsString = message.get("message").toString();

        Assert.assertEquals(poraka,messageAsString);

    }
}
