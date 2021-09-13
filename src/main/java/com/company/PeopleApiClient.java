package com.company;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

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
}
