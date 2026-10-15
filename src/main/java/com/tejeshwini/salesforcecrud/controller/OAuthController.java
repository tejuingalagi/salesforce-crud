package com.tejeshwini.salesforcecrud.controller;

import com.tejeshwini.salesforcecrud.SalesforceProperties;
import com.tejeshwini.salesforcecrud.service.SalesforceOAuthService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

@RestController
public class OAuthController {

    private final SalesforceProperties salesforceProperties;
    private final SalesforceOAuthService salesforceOAuthService;

    public OAuthController(
            SalesforceProperties salesforceProperties,
            SalesforceOAuthService salesforceOAuthService) {

        this.salesforceProperties = salesforceProperties;
        this.salesforceOAuthService = salesforceOAuthService;
    }

    @GetMapping("/oauth/login")
    public RedirectView login(HttpSession session) {

        // 1. Generate PKCE code verifier
        String codeVerifier = generateCodeVerifier();

        // 2. Generate PKCE code challenge
        String codeChallenge = generateCodeChallenge(codeVerifier);

        // 3. Generate state for security
        String state = generateState();

        // 4. Store values in session
        session.setAttribute("code_verifier", codeVerifier);
        session.setAttribute("oauth_state", state);

        // 5. Build Salesforce authorization URL
        String authorizationUrl =
                salesforceProperties.getAuthorizationUrl()
                + "?response_type=code"
                + "&client_id=" + salesforceProperties.getClientId()
                + "&redirect_uri=" + salesforceProperties.getRedirectUri()
                + "&scope=api%20refresh_token"
                + "&state=" + state
                + "&code_challenge=" + codeChallenge
                + "&code_challenge_method=S256";

        return new RedirectView(authorizationUrl);
    }

    
    @GetMapping("/oauth2/callback")
    public RedirectView callback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpSession session) throws Exception {

        String savedState =
                (String) session.getAttribute("oauth_state");

        String codeVerifier =
                (String) session.getAttribute("code_verifier");

        if (savedState == null || !savedState.equals(state)) {
        	return new RedirectView(
        	        "/?oauthError=invalid_state");
        }

        if (codeVerifier == null) {
        	return new RedirectView(
        	        "/?oauthError=missing_verifier");
        }

        Map<String, Object> tokenResponse =
                salesforceOAuthService.exchangeCodeForToken(
                        code, codeVerifier);

        session.setAttribute(
                "access_token", tokenResponse.get("access_token"));

        session.setAttribute(
                "refresh_token", tokenResponse.get("refresh_token"));

        session.setAttribute(
                "instance_url", tokenResponse.get("instance_url"));

        session.removeAttribute("oauth_state");
        session.removeAttribute("code_verifier");

        return new RedirectView("/");
    }

    private String generateCodeVerifier() {

        SecureRandom secureRandom = new SecureRandom();

        byte[] bytes = new byte[32];

        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private String generateCodeChallenge(String codeVerifier) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            codeVerifier.getBytes(StandardCharsets.US_ASCII));

            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(hash);

        } catch (NoSuchAlgorithmException e) {

            throw new IllegalStateException(
                    "SHA-256 algorithm not available", e);
        }
    }

    private String generateState() {

        SecureRandom secureRandom = new SecureRandom();

        byte[] bytes = new byte[16];

        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
    

@GetMapping("/oauth/logout")
public RedirectView logout(HttpSession session) {
    session.invalidate();
    return new RedirectView("/");
}

}