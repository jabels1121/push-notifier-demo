package com.jaybe.pushnotifierdemo.domain.fcm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FcmConnectionToken {
    private String type;
    @JsonProperty(value = "project_id")
    @Field(value = "project_id")
    private String projectId;
    @JsonProperty(value = "private_key_id")
    @Field(value = "private_key_id")
    private String privateKeyId;
    @JsonProperty(value = "private_key")
    @Field(value = "private_key")
    private String privateKey;
    @JsonProperty(value = "client_email")
    @Field(value = "client_email")
    private String clientEmail;
    @JsonProperty(value = "client_id")
    @Field(value = "client_id")
    private String clientId;
    @JsonProperty(value = "auth_uri")
    @Field(value = "auth_uri")
    private String authUri;
    @JsonProperty(value = "token_uri")
    @Field(value = "token_uri")
    private String tokenUri;
    @JsonProperty(value = "auth_provider_x509_cert_url")
    @Field(value = "auth_provider_x509_cert_url")
    private String authProviderX509CertUrl;
    @JsonProperty(value = "client_x509_cert_url")
    @Field(value = "client_x509_cert_url")
    private String clientX509CertUrl;
}
