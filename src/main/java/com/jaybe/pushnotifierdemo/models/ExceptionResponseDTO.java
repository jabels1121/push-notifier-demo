package com.jaybe.pushnotifierdemo.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class ExceptionResponseDTO {

    @Getter
    @Setter
    private long timestamp;

    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private String message;

    private ExceptionResponseDTO(long timestamp, Integer code, String message) {
        this.timestamp = timestamp;
        this.code = code;
        this.message = message;
    }

    public static ExceptionResponseDTO getUnauthorizedInstance() {
        return new ExceptionResponseDTO(System.currentTimeMillis(), 401, "Unauthorized!");
    }

    public static String getUnauthorizedInstanceAsJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(getUnauthorizedInstance());
    }

}
