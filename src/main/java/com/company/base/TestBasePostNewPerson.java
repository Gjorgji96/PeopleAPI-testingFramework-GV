package com.company.base;

import com.company.PeopleApiClient;
import com.company.payloads.PostNewPersonPayload;
import com.company.requests.PostNewPersonRequest;
import com.company.responses.PostNewPersonResponse;
import org.apache.http.HttpResponse;

public class TestBasePostNewPerson {

   public PeopleApiClient peopleApiClient = new PeopleApiClient();
   public HttpResponse response;
   public PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
   public PostNewPersonRequest postNewPersonRequest = new PostNewPersonRequest();
   public String newPersonPayloadAsString;
   public PostNewPersonResponse postNewPersonResponse;
   public String incorrectID;

    public TestBasePostNewPerson() throws Exception{

    }
}
