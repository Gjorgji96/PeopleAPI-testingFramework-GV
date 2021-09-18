package com.company.payloads;

import com.company.requests.PostNewPersonRequest;

public class PostNewPersonPayload {

    public PostNewPersonRequest createNewPerson(){
        return PostNewPersonRequest.builder()
                .name("Goran")
                .surname("Pandev")
                .age(41)
                .isEmployed(true)
                .location("Genoa")
                .build();
    }
}
