
package com.tejeshwini.salesforcecrud.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Service
public class SalesforceApiService {

    private static final String API_PATH = "/services/data/v67.0";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    public SalesforceApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // ---------- Accounts ----------

    public String getAccounts(HttpSession session, int offset)
            throws IOException, InterruptedException {
        return queryRecords(session, "Account",
                "Id, Name, Type, Industry, Phone, Website", offset);
    }

    public String createAccount(String name, String type, String industry,
            HttpSession session) throws IOException, InterruptedException {
        return create("Account", fields(
                "Name", name, "Type", type, "Industry", industry), session);
    }

    public String updateAccount(String id, String name, String type,
            String industry, HttpSession session)
            throws IOException, InterruptedException {
        return update("Account", id, fields(
                "Name", name, "Type", type, "Industry", industry), session);
    }

    public String deleteAccount(String id, HttpSession session)
            throws IOException, InterruptedException {
        return delete("Account", id, session);
    }

    // ---------- Opportunities ----------

    public String getOpportunities(HttpSession session, int offset)
            throws IOException, InterruptedException {
        return queryRecords(session, "Opportunity",
                "Id, Name, StageName, CloseDate, Amount, Probability", offset);
    }

    public String createOpportunity(String name, String stageName,
            String closeDate, String amount, HttpSession session)
            throws IOException, InterruptedException {
        return create("Opportunity", fields(
                "Name", name,
                "StageName", stageName,
                "CloseDate", closeDate,
                "Amount", toNumber(amount)), session);
    }

    public String updateOpportunity(String id, String name, String stageName,
            String closeDate, String amount, HttpSession session)
            throws IOException, InterruptedException {
        return update("Opportunity", id, fields(
                "Name", name,
                "StageName", stageName,
                "CloseDate", closeDate,
                "Amount", toNumber(amount)), session);
    }

    public String deleteOpportunity(String id, HttpSession session)
            throws IOException, InterruptedException {
        return delete("Opportunity", id, session);
    }

    // ---------- Leads ----------

    public String getLeads(HttpSession session, int offset)
            throws IOException, InterruptedException {
        return queryRecords(session, "Lead",
                "Id, FirstName, LastName, Company, Status, Email", offset);
    }

    public String createLead(String firstName, String lastName, String company,
            String status, String email, HttpSession session)
            throws IOException, InterruptedException {
        return create("Lead", fields(
                "FirstName", firstName,
                "LastName", lastName,
                "Company", company,
                "Status", status,
                "Email", email), session);
    }

    public String updateLead(String id, String firstName, String lastName,
            String company, String status, String email, HttpSession session)
            throws IOException, InterruptedException {
        return update("Lead", id, fields(
                "FirstName", firstName,
                "LastName", lastName,
                "Company", company,
                "Status", status,
                "Email", email), session);
    }

    public String deleteLead(String id, HttpSession session)
            throws IOException, InterruptedException {
        return delete("Lead", id, session);
    }

    // ---------- Contacts ----------

    public String getContacts(HttpSession session, int offset)
            throws IOException, InterruptedException {
        return queryRecords(session, "Contact",
                "Id, FirstName, LastName, AccountId, Email, Phone", offset);
    }

    public String createContact(String firstName, String lastName,
            String accountId, String email, String phone, HttpSession session)
            throws IOException, InterruptedException {
        return create("Contact", fields(
                "FirstName", firstName,
                "LastName", lastName,
                "AccountId", accountId,
                "Email", email,
                "Phone", phone), session);
    }

    public String updateContact(String id, String firstName, String lastName,
            String accountId, String email, String phone, HttpSession session)
            throws IOException, InterruptedException {
        return update("Contact", id, fields(
                "FirstName", firstName,
                "LastName", lastName,
                "AccountId", accountId,
                "Email", email,
                "Phone", phone), session);
    }

    public String deleteContact(String id, HttpSession session)
            throws IOException, InterruptedException {
        return delete("Contact", id, session);
    }

    // ---------- Cases ----------

    public String getCases(HttpSession session, int offset)
            throws IOException, InterruptedException {
        return queryRecords(session, "Case",
                "Id, CaseNumber, Subject, Status, Priority, Origin", offset);
    }

    public String createCase(Map<String, Object> caseData, HttpSession session)
            throws IOException, InterruptedException {
        return create("Case", caseData, session);
    }

    public String updateCase(String id, Map<String, Object> caseData,
            HttpSession session) throws IOException, InterruptedException {
        return update("Case", id, caseData, session);
    }

    public String deleteCase(String id, HttpSession session)
            throws IOException, InterruptedException {
        return delete("Case", id, session);
    }

    // ---------- Shared helpers ----------

    private record Auth(String accessToken, String instanceUrl) {}

    private Auth auth(HttpSession session) {
        String accessToken =
                (String) session.getAttribute("access_token");
        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        return new Auth(accessToken, instanceUrl);
    }

    // Builds a map from alternating key-value arguments.
    private Map<String, Object> fields(Object... keyValues) {
        Map<String, Object> map = new LinkedHashMap<>();

        for (int i = 0; i < keyValues.length; i += 2) {
            map.put((String) keyValues[i], keyValues[i + 1]);
        }

        return map;
    }

    private BigDecimal toNumber(String amount) {
        if (amount == null || amount.isBlank()) {
            return null;
        }

        try {
            return new BigDecimal(amount.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Amount must be a valid number.");
        }
    }

    // ---------- Generic CREATE ----------

    private String create(String object, Map<String, Object> data,
            HttpSession session) throws IOException, InterruptedException {

        Auth auth = auth(session);

        String requestBody = objectMapper.writeValueAsString(data);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(auth.instanceUrl() + API_PATH
                        + "/sobjects/" + object))
                .header("Authorization", "Bearer " + auth.accessToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw failure(object + " creation", response);
        }

        return response.body();
    }

    // ---------- Generic UPDATE ----------

    private String update(String object, String id,
            Map<String, Object> data, HttpSession session)
            throws IOException, InterruptedException {

        Auth auth = auth(session);

        String requestBody = objectMapper.writeValueAsString(data);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(auth.instanceUrl() + API_PATH
                        + "/sobjects/" + object + "/" + id))
                .header("Authorization", "Bearer " + auth.accessToken())
                .header("Content-Type", "application/json")
                .method("PATCH",
                        HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw failure(object + " update", response);
        }

        return "{\"success\":true,\"message\":\""
                + object + " updated successfully\"}";
    }

    // ---------- Generic DELETE ----------

    private String delete(String object, String id, HttpSession session)
            throws IOException, InterruptedException {

        Auth auth = auth(session);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(auth.instanceUrl() + API_PATH
                        + "/sobjects/" + object + "/" + id))
                .header("Authorization", "Bearer " + auth.accessToken())
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw failure(object + " deletion", response);
        }

        return "{\"success\":true,\"message\":\""
                + object + " deleted successfully\"}";
    }

    // ---------- Query records with pagination ----------

    private String queryRecords(HttpSession session, String objectName,
            String selectedFields, int offset)
            throws IOException, InterruptedException {

        Auth auth = auth(session);

        if (offset < 0 || offset > 2000 || offset % 20 != 0) {
            throw new IllegalArgumentException(
                    "Offset must be 0 to 2000 and a multiple of 20.");
        }

        String soql = "SELECT " + selectedFields
                + " FROM " + objectName
                + " ORDER BY CreatedDate DESC, Id DESC"
                + " LIMIT 20 OFFSET " + offset;

        String url = auth.instanceUrl() + API_PATH + "/query/?q="
                + URLEncoder.encode(soql, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + auth.accessToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw failure(objectName + " query", response);
        }

        return response.body();
    }

    // ---------- Salesforce error helper ----------

    private IllegalStateException failure(String action,
            HttpResponse<String> response) {
        return new IllegalStateException(
                action + " failed. Status: " + response.statusCode()
                        + ", Response: " + response.body());
    }
}
