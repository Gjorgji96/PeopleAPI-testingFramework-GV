package com.company;

import netscape.javascript.JSObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;

public class PeopleApiClient {

    public  HttpResponse getWelcomeRequest() throws Exception {

        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE,"application/json");

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        HttpGet request = new HttpGet("https://people-api1.herokuapp.com");
        request.setHeader(contentType);

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();

        HttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);

        HttpEntity newEntity = new StringEntity(body, ContentType.get(entity));
        response.setEntity(newEntity);

        return response;

    }

    public HttpResponse getAllPeople() throws Exception {
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE,"application/json");

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        HttpGet getPeopleRequest = new HttpGet("https://people-api1.herokuapp.com/api/people");
        getPeopleRequest.setHeader(contentType);

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();

        HttpResponse response = httpClient.execute(getPeopleRequest);

        HttpEntity entityPeople = response.getEntity();
        String bodyPeople = EntityUtils.toString(entityPeople);

        HttpEntity newEntity = new StringEntity(bodyPeople, ContentType.get(entityPeople));
        response.setEntity(newEntity);

        return response;


    }

    public HttpResponse getOnePerson() throws Exception{
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE,"application/json");

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        HttpGet getOnePerson = new HttpGet("https://people-api1.herokuapp.com/api/person613e004966bb7645d800f41d");
        getOnePerson.setHeader(contentType);

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();

        HttpResponse response = httpClient.execute(getOnePerson);

        HttpEntity entityPeople = response.getEntity();
        String bodyPeople = EntityUtils.toString(entityPeople);

        HttpEntity newEntity = new StringEntity(bodyPeople, ContentType.get(entityPeople));
        response.setEntity(newEntity);

        return response;

    }
    public HttpResponse createPerson() throws Exception{

        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE,"application/json");

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        HttpPost createPerson = new HttpPost("https://people-api1.herokuapp.com/api/person");
        JSONObject payLoadasObject = new JSONObject();
        payLoadasObject.put("name","Pero");
        payLoadasObject.put("surname","Blazhevski");
        payLoadasObject.put("age",24);
        payLoadasObject.put("isEmployed",true);
        payLoadasObject.put("location","skopje");

        String payLoadAsString = payLoadasObject.toString();

        createPerson.setHeader(contentType);
        createPerson.setEntity(new StringEntity(payLoadasObject.toString()));

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();

        HttpResponse response = httpClient.execute(createPerson);

        HttpEntity entityPeople = response.getEntity();
        String bodyPeople = EntityUtils.toString(entityPeople);

        HttpEntity newEntity = new StringEntity(bodyPeople, ContentType.get(entityPeople));
        response.setEntity(newEntity);

        return response;


    }

    public HttpResponse update () throws Exception {

        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE,"application/json");

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();
        HttpPut location = new HttpPut("https://people-api1.herokuapp.com/api/person/613f3cc8efc41e00046091c5");
        JSONObject payLoadasObject = new JSONObject();
        payLoadasObject.put("location","Oslo,Norway");

        String payLoadasString = payLoadasObject.toString();

        location.setHeader(contentType);
        location.setEntity(new StringEntity(payLoadasObject.toString()));

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();

        HttpResponse response = httpClient.execute(location);

        HttpEntity entityUpdate = response.getEntity();
        String bodyPeople = EntityUtils.toString(entityUpdate);

        HttpEntity newEntity = new StringEntity(bodyPeople, ContentType.get(entityUpdate));
        response.setEntity(newEntity);

        return response;








    }
}
