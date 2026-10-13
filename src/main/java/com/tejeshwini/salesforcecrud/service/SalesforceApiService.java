package com.tejeshwini.salesforcecrud.service;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;

import java.util.Map;
@Service
public class SalesforceApiService {

	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

	public SalesforceApiService(ObjectMapper objectMapper) {
	    this.httpClient = HttpClient.newHttpClient();
	    this.objectMapper = objectMapper;
	}

    public String getAccounts(HttpSession session, int offset)
            throws IOException, InterruptedException {
        return queryRecords(session, "Account",
                "Id, Name, Type, Industry, Phone, Website", offset);
    }

    public String createAccount(
            String name,
            String type,
            String industry,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String jsonBody =
                "{"
                + "\"Name\":\"" + name + "\","
                + "\"Type\":\"" + type + "\","
                + "\"Industry\":\"" + industry + "\""
                + "}";

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Account/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new IllegalStateException(
                    "Account creation failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return response.body();
    }
    
    public String updateAccount(
            String accountId,
            String name,
            String type,
            String industry,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String jsonBody =
                "{"
                + "\"Name\":\"" + name + "\","
                + "\"Type\":\"" + type + "\","
                + "\"Industry\":\"" + industry + "\""
                + "}";

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Account/"
                + accountId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .method(
                        "PATCH",
                        HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new IllegalStateException(
                    "Account update failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return "{\"success\":true,\"message\":\"Account updated successfully\"}";
    }
    
    public String deleteAccount(
            String accountId,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Account/"
                + accountId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .DELETE()
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new IllegalStateException(
                    "Account deletion failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return "{\"success\":true,\"message\":\"Account deleted successfully\"}";
    }
    
    public String getOpportunities(HttpSession session, int offset)
            throws IOException, InterruptedException {
        return queryRecords(session, "Opportunity",
                "Id, Name, StageName, CloseDate, Amount, Probability", offset);
    }

    public String createOpportunity(
            String name,
            String stageName,
            String closeDate,
            String amount,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String jsonBody =
                "{"
                + "\"Name\":\"" + name + "\","
                + "\"StageName\":\"" + stageName + "\","
                + "\"CloseDate\":\"" + closeDate + "\","
                + "\"Amount\":" + amount
                + "}";

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Opportunity/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new IllegalStateException(
                    "Opportunity creation failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return response.body();
    }
    
    public String updateOpportunity(
            String opportunityId,
            String name,
            String stageName,
            String closeDate,
            String amount,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String jsonBody =
                "{"
                + "\"Name\":\"" + name + "\","
                + "\"StageName\":\"" + stageName + "\","
                + "\"CloseDate\":\"" + closeDate + "\","
                + "\"Amount\":" + amount
                + "}";

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Opportunity/"
                + opportunityId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .method(
                        "PATCH",
                        HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new IllegalStateException(
                    "Opportunity update failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return "{\"success\":true,\"message\":\"Opportunity updated successfully\"}";
    }
    
    public String deleteOpportunity(
            String opportunityId,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Opportunity/"
                + opportunityId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .DELETE()
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new IllegalStateException(
                    "Opportunity deletion failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return "{\"success\":true,\"message\":\"Opportunity deleted successfully\"}";
    }
    
    public String getLeads(HttpSession session, int offset)
            throws IOException, InterruptedException {
        return queryRecords(session, "Lead",
                "Id, FirstName, LastName, Company, Status, Email", offset);
    }

    public String createLead(
            String firstName,
            String lastName,
            String company,
            String status,
            String email,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String jsonBody =
                "{"
                + "\"FirstName\":\"" + firstName + "\","
                + "\"LastName\":\"" + lastName + "\","
                + "\"Company\":\"" + company + "\","
                + "\"Status\":\"" + status + "\","
                + "\"Email\":\"" + email + "\""
                + "}";

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Lead/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new IllegalStateException(
                    "Lead creation failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return response.body();
    }
    public String updateLead(
            String leadId,
            String firstName,
            String lastName,
            String company,
            String status,
            String email,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String jsonBody =
                "{"
                + "\"FirstName\":\"" + firstName + "\","
                + "\"LastName\":\"" + lastName + "\","
                + "\"Company\":\"" + company + "\","
                + "\"Status\":\"" + status + "\","
                + "\"Email\":\"" + email + "\""
                + "}";

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Lead/"
                + leadId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .method(
                        "PATCH",
                        HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new IllegalStateException(
                    "Lead update failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return "{\"success\":true,\"message\":\"Lead updated successfully\"}";
    }
    
    public String deleteLead(
            String leadId,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Lead/"
                + leadId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .DELETE()
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new IllegalStateException(
                    "Lead deletion failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return "{\"success\":true,\"message\":\"Lead deleted successfully\"}";
    }
    public String getContacts(HttpSession session, int offset)
            throws IOException, InterruptedException {
        return queryRecords(session, "Contact",
                "Id, FirstName, LastName, AccountId, Email, Phone", offset);
    }

    public String createContact(
            String firstName,
            String lastName,
            String accountId,
            String email,
            String phone,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        Map<String, Object> contactData = new HashMap<>();

        contactData.put("FirstName", firstName);
        contactData.put("LastName", lastName);
        contactData.put("AccountId", accountId);
        contactData.put("Email", email);
        contactData.put("Phone", phone);

        String jsonBody = objectMapper.writeValueAsString(contactData);

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Contact/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new IllegalStateException(
                    "Contact creation failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return response.body();
    }
    
    public String updateContact(
            String contactId,
            String firstName,
            String lastName,
            String accountId,
            String email,
            String phone,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String jsonBody =
                "{"
                + "\"FirstName\":\"" + firstName + "\","
                + "\"LastName\":\"" + lastName + "\","
                + "\"AccountId\":\"" + accountId + "\","
                + "\"Email\":\"" + email + "\","
                + "\"Phone\":\"" + phone + "\""
                + "}";

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Contact/"
                + contactId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .method(
                        "PATCH",
                        HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new IllegalStateException(
                    "Contact update failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return "{\"success\":true,\"message\":\"Contact updated successfully\"}";
    }
    
    public String deleteContact(
            String contactId,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Contact/"
                + contactId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .DELETE()
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new IllegalStateException(
                    "Contact deletion failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return "{\"success\":true,\"message\":\"Contact deleted successfully\"}";
    }
    
    public String getCases(HttpSession session, int offset)
            throws IOException, InterruptedException {
        return queryRecords(session, "Case",
                "Id, CaseNumber, Subject, Status, Priority, Origin", offset);
    }

    public String createCase(
            Map<String, Object> caseData,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Case";

        String requestBody =
                objectMapper.writeValueAsString(caseData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new IllegalStateException(
                    "Case create failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return response.body();
    }
    
    public String updateCase(
            String caseId,
            Map<String, Object> caseData,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Case/"
                + caseId;

        String requestBody =
        		objectMapper.writeValueAsString(caseData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .method(
                        "PATCH",
                        HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new IllegalStateException(
                    "Case update failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return "{\"success\":true,\"message\":\"Case updated successfully\"}";
    }
    public String deleteCase(
            String caseId,
            HttpSession session)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        String url =
                instanceUrl
                + "/services/data/v67.0/sobjects/Case/"
                + caseId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .DELETE()
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new IllegalStateException(
                    "Case delete failed. Status: "
                    + response.statusCode()
                    + ", Response: "
                    + response.body());
        }

        return "{\"success\":true,\"message\":\"Case deleted successfully\"}";
    }
    

    private String queryRecords(
            HttpSession session,
            String objectName,
            String fields,
            int offset)
            throws IOException, InterruptedException {

        String accessToken =
                (String) session.getAttribute("access_token");

        String instanceUrl =
                (String) session.getAttribute("instance_url");

        if (accessToken == null || instanceUrl == null) {
            throw new IllegalStateException(
                    "Salesforce authentication required. Please login first.");
        }

        if (offset < 0 || offset > 2000 || offset % 20 != 0) {
            throw new IllegalArgumentException(
                    "Offset must be 0 to 2000 and a multiple of 20.");
        }

        String soql =
                "SELECT " + fields +
                " FROM " + objectName +
                " ORDER BY CreatedDate DESC, Id DESC" +
                " LIMIT 20 OFFSET " + offset;

        String url =
                instanceUrl
                + "/services/data/v67.0/query/?q="
                + URLEncoder.encode(soql, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IllegalStateException(
                    objectName + " query failed. Status: "
                    + response.statusCode()
                    + ", Response: " + response.body());
        }

        return response.body();
    }

}
