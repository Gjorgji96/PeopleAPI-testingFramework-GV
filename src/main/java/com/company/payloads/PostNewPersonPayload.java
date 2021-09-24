package com.company.payloads;

import com.company.requests.PostNewPersonRequest;
import org.json.JSONObject;

public class PostNewPersonPayload {

    public PostNewPersonRequest createNewPerson() {
        return PostNewPersonRequest.builder()
                .name("Goran")
                .surname("Pandev")
                .age(41)
                .isEmployed(true)
                .location("Genoa")
                .build();
    }

    public JSONObject createNewPersonPayloadEmployedAsString() {
        JSONObject isEmployed = new JSONObject();
        isEmployed.put("name", "Darko");
        isEmployed.put("surname", "Pancev");
        isEmployed.put("isEmployed", "ne sum vraboten");
        isEmployed.put("age", 40);
        isEmployed.put("location", "vardar");
        return isEmployed;
    }

    public JSONObject createNewPersonPayloadNameAndSurnameAsString() {
        JSONObject isEmployed = new JSONObject();
        isEmployed.put("name", 40);
        isEmployed.put("surname", 40);
        isEmployed.put("isEmployed", "ne sum vraboten");
        isEmployed.put("age", 40);
        isEmployed.put("location", "vardar");
        return isEmployed;
    }
}
