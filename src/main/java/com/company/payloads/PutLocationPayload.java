package com.company.payloads;

import com.company.requests.PutRequest;

public class PutLocationPayload {

    public PutRequest updateLocation(){
        return PutRequest.builder()
                .location("Karposh")
                .build();
    }
}
