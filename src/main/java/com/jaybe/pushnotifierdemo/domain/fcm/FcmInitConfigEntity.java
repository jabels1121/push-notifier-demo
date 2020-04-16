package com.jaybe.pushnotifierdemo.domain.fcm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaybe.pushnotifierdemo.exceptions.NotValidFcmConfigFileException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Document
@Slf4j
public class FcmInitConfigEntity {

    @Id
    private String id;
    @Getter
    @Setter
    private String appName;
    @Getter
    @Setter
    private FcmConnectionToken fcmConnectionToken;

    @Transient
    private final ObjectMapper mapper = new ObjectMapper();

    public FcmInitConfigEntity(FcmConnectionToken fcmConnectionToken) throws NotValidFcmConfigFileException {
        try {
            this.appName = extractAppNameFromConfig(fcmConnectionToken);
        } catch (IOException e) {
            log.error("Error was occur when trying to read project_id. Message={}", e.getLocalizedMessage());
            throw new NotValidFcmConfigFileException(e.getLocalizedMessage(), e);
        }
        this.fcmConnectionToken = fcmConnectionToken;
    }

    private String extractAppNameFromConfig(FcmConnectionToken initConfig) throws IOException {
        return initConfig.getProjectId();

    }

    public InputStream configStream() throws Exception {
        if (validationConfigFile()) {
            return new ByteArrayInputStream(mapper.writeValueAsString(fcmConnectionToken).getBytes());
        }
        throw new NotValidFcmConfigFileException("Not valid fcm config file for project=" + appName +
            " . Config=" + fcmConnectionToken);
    }

    private boolean validationConfigFile() {
        try {
            var jsonNode = mapper.readTree(mapper.writeValueAsString(this.fcmConnectionToken));
            return jsonNode.has("type") &&
                    jsonNode.has("project_id") &&
                    jsonNode.has("private_key_id") &&
                    jsonNode.has("private_key") &&
                    jsonNode.has("client_email") &&
                    jsonNode.has("client_id") &&
                    jsonNode.has("auth_uri") &&
                    jsonNode.has("token_uri") &&
                    jsonNode.has("auth_provider_x509_cert_url") &&
                    jsonNode.has("client_x509_cert_url");
        } catch (IOException e) {
            log.error("Некорректный формат конфиг файла");
            return false;
        }
    }
}
