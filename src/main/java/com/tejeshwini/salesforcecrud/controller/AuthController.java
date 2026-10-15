package com.tejeshwini.salesforcecrud.controller;

import jakarta.servlet.http.HttpSession;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/api/auth/status")
    public Map<String, Boolean> getAuthStatus(HttpSession session) {

        boolean loggedIn =
                session.getAttribute("access_token") != null
                && session.getAttribute("instance_url") != null;

        return Map.of("loggedIn", loggedIn);
    }

    @PostMapping("/api/auth/logout")
    public Map<String, Boolean> logout(HttpSession session) {
        session.invalidate();
        return Map.of("loggedIn", false);
    }
}