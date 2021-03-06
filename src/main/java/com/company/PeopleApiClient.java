package com.company;

import netscape.javascript.JSObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
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
import java.net.URL;

public class PeopleApiClient {

    public  HttpResponse httpGet(String url) throws Exception {

        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE,"application/json");

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        HttpGet request = new HttpGet(url);
        request.setHeader(contentType);

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();

        HttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);

        HttpEntity newEntity = new StringEntity(body, ContentType.get(entity));
        response.setEntity(newEntity);

        return response;

    }
    public HttpResponse httpDelete(String url) throws Exception {
        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        HttpDelete request = new HttpDelete(url);
        request.setHeader(contentType);

        HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();

        HttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(response.getEntity());

        HttpEntity newEntity = new StringEntity(body, ContentType.get(entity));
        response.setEntity(newEntity);

        return response;
    }

    public HttpResponse httpPost(String url,JSONObject payLoadasObject) throws Exception{

        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE,"application/json");

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        HttpPost createPerson = new HttpPost(url);

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

    public HttpResponse httpPut (String url,JSONObject payLoadasObject) throws Exception {

        Header contentType = new BasicHeader(HttpHeaders.CONTENT_TYPE,"application/json");

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();
        HttpPut location = new HttpPut(url);

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
