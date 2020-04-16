package com.jaybe.pushnotifierdemo.domain.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class PushInfoDTO {

    private String appName;
    private String deviceToken;
    private String title;
    private String body;

    public Map<String, String> getPayload() {
        var payload = new HashMap<String, String>();
        payload.put("title", this.title);
        payload.put("body", this.body);
        return payload;
    }

}
