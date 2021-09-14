package com.company.peopleapi;

import com.company.PeopleApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
public class InitialTestFile {
    PeopleApiClient   peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    HttpResponse getPeople;
    HttpResponse getOnePerson;
    HttpResponse update;


    @Test
    public void initialTest() throws Exception{
        //nekakov REQUEST do PEOPLE API

        response = peopleApiClient.getWelcomeRequest();

        String body = EntityUtils.toString(response.getEntity());
        getPeople = peopleApiClient.getAllPeople();
        String peopleBody = EntityUtils.toString(getPeople.getEntity());

        getOnePerson = peopleApiClient.getOnePerson();
        String onePerson = EntityUtils.toString(getOnePerson.getEntity());

        update = peopleApiClient.update();
        String location = EntityUtils.toString(update.getEntity());

    }
}
