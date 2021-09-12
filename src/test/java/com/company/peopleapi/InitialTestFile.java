package com.company.peopleapi;

import com.company.PeopleApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
public class InitialTestFile {
    PeopleApiClient   peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    HttpResponse getPeople;


    @Test
    public void initialTest() throws Exception{
        //nekakov REQUEST do PEOPLE API

        response = peopleApiClient.getWelcomeRequest();

        String body = EntityUtils.toString(response.getEntity());
        getPeople = peopleApiClient.getPeopleRequest();
        String peopleBody = EntityUtils.toString(response.getEntity());



    }
}
