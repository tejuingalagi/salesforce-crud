package com.tejeshwini.salesforcecrud.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tejeshwini.salesforcecrud.SalesforceProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class SalesforceOAuthService {

    private final SalesforceProperties salesforceProperties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public SalesforceOAuthService(
            SalesforceProperties salesforceProperties,
            ObjectMapper objectMapper) {

        this.salesforceProperties = salesforceProperties;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newHttpClient();
    }

    public Map<String, Object> exchangeCodeForToken(
            String code,
            String codeVerifier) throws IOException, InterruptedException {

        String requestBody =
                "grant_type=authorization_code"
                + "&code=" + encode(code)
                + "&client_id=" + encode(salesforceProperties.getClientId())
                + "&client_secret=" + encode(salesforceProperties.getClientSecret())
                + "&redirect_uri=" + encode(salesforceProperties.getRedirectUri())
                + "&code_verifier=" + encode(codeVerifier);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(salesforceProperties.getTokenUrl()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IllegalStateException(
                    "Salesforce token request failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return objectMapper.readValue(
                response.body(),
                new TypeReference<Map<String, Object>>() {
                });
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}