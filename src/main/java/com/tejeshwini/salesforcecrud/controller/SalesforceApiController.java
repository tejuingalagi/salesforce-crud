package com.tejeshwini.salesforcecrud.controller;

import com.tejeshwini.salesforcecrud.service.SalesforceApiService;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
public class SalesforceApiController {

    private final SalesforceApiService salesforceApiService;

    public SalesforceApiController(
            SalesforceApiService salesforceApiService) {
        this.salesforceApiService = salesforceApiService;
    }

    @GetMapping("/api/salesforce/accounts")
    public String getAccounts(HttpSession session)
            throws Exception {

        return salesforceApiService.getAccounts(session);
    }
    
    @PostMapping("/api/salesforce/accounts")
    public String createAccount(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam String industry,
            HttpSession session)
            throws Exception {

        return salesforceApiService.createAccount(
                name,
                type,
                industry,
                session);
    }
    @PatchMapping("/api/salesforce/accounts/{accountId}")
    public String updateAccount(
            @PathVariable  String accountId,
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam String industry,
            HttpSession session)
            throws Exception {

        return salesforceApiService.updateAccount(
                accountId,
                name,
                type,
                industry,
                session);
    }
    
    @DeleteMapping("/api/salesforce/accounts/{accountId}")
    public String deleteAccount(
            @PathVariable String accountId,
            HttpSession session)
            throws Exception {

        return salesforceApiService.deleteAccount(
                accountId,
                session);
    }
    
    @GetMapping("/api/salesforce/opportunities")
    public String getOpportunities(HttpSession session)
            throws Exception {

        return salesforceApiService.getOpportunities(session);
    }
    
    @PostMapping("/api/salesforce/opportunities")
    public String createOpportunity(
            @RequestParam String name,
            @RequestParam String stageName,
            @RequestParam String closeDate,
            @RequestParam String amount,
            HttpSession session)
            throws Exception {

        return salesforceApiService.createOpportunity(
                name,
                stageName,
                closeDate,
                amount,
                session);
    }
    @PatchMapping("/api/salesforce/opportunities/{opportunityId}")
    public String updateOpportunity(

            @PathVariable String opportunityId,

            @RequestParam String name,

            @RequestParam String stageName,

            @RequestParam String closeDate,

            @RequestParam String amount,

            HttpSession session)

            throws Exception {

        return salesforceApiService.updateOpportunity(
                opportunityId,
                name,
                stageName,
                closeDate,
                amount,
                session);
    }
    
    @DeleteMapping("/api/salesforce/opportunities/{opportunityId}")
    public String deleteOpportunity(

            @PathVariable String opportunityId,

            HttpSession session)

            throws Exception {

        return salesforceApiService.deleteOpportunity(
                opportunityId,
                session);
    }
    @GetMapping("/api/salesforce/leads")
    public String getLeads(HttpSession session)
            throws Exception {

        return salesforceApiService.getLeads(session);
    }
    
    @PostMapping("/api/salesforce/leads")
    public String createLead(

            @RequestParam String firstName,

            @RequestParam String lastName,

            @RequestParam String company,

            @RequestParam String status,

            @RequestParam String email,

            HttpSession session)

            throws Exception {

        return salesforceApiService.createLead(
                firstName,
                lastName,
                company,
                status,
                email,
                session);
    }
    
    @PatchMapping("/api/salesforce/leads/{leadId}")
    public String updateLead(

            @PathVariable String leadId,

            @RequestParam String firstName,

            @RequestParam String lastName,

            @RequestParam String company,

            @RequestParam String status,

            @RequestParam String email,

            HttpSession session)

            throws Exception {

        return salesforceApiService.updateLead(
                leadId,
                firstName,
                lastName,
                company,
                status,
                email,
                session);
    }
    
    @DeleteMapping("/api/salesforce/leads/{leadId}")
    public String deleteLead(
            @PathVariable String leadId,
            HttpSession session)
            throws Exception {

        return salesforceApiService.deleteLead(
                leadId,
                session);
    }
    
    @GetMapping("/api/salesforce/contacts")
    public String getContacts(HttpSession session)
            throws Exception {

        return salesforceApiService.getContacts(session);
    }
    @PostMapping("/api/salesforce/contacts")
    public String createContact(

            @RequestParam String firstName,

            @RequestParam String lastName,

            @RequestParam String accountId,

            @RequestParam String email,

            @RequestParam String phone,

            HttpSession session)

            throws Exception {

        return salesforceApiService.createContact(
                firstName,
                lastName,
                accountId,
                email,
                phone,
                session);
    }
    
    @PatchMapping("/api/salesforce/contacts/{contactId}")
    public String updateContact(

            @PathVariable String contactId,

            @RequestParam String firstName,

            @RequestParam String lastName,

            @RequestParam String accountId,

            @RequestParam String email,

            @RequestParam String phone,

            HttpSession session)

            throws Exception {

        return salesforceApiService.updateContact(
                contactId,
                firstName,
                lastName,
                accountId,
                email,
                phone,
                session);
    }
    
    @DeleteMapping("/api/salesforce/contacts/{contactId}")
    public String deleteContact(
            @PathVariable String contactId,
            HttpSession session)
            throws Exception {

        return salesforceApiService.deleteContact(
                contactId,
                session);
    }
    
    @GetMapping("/api/salesforce/cases")
    public String getCases(HttpSession session)
            throws Exception {

        return salesforceApiService.getCases(session);
    }
    @PostMapping("/api/salesforce/cases")
    public String createCase(
            @RequestBody Map<String, Object> caseData,
            HttpSession session)
            throws Exception {

        return salesforceApiService.createCase(
                caseData,
                session);
    }
    @PatchMapping("/api/salesforce/cases/{caseId}")
    public String updateCase(
            @PathVariable String caseId,
            @RequestBody Map<String, Object> caseData,
            HttpSession session)
            throws Exception {

        return salesforceApiService.updateCase(
                caseId,
                caseData,
                session);
    }
    @DeleteMapping("/api/salesforce/cases/{caseId}")
    public String deleteCase(
            @PathVariable String caseId,
            HttpSession session)
            throws Exception {

        return salesforceApiService.deleteCase(
                caseId,
                session);
    }
}