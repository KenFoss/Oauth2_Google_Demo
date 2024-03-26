package com.auth.demo.web.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2Controller {

    // This endpoint handles the OAuth2 callback from Google
    @GetMapping("/login/oauth2/code/google")
    public String oauth2Callback() {
        // Add any necessary logic here, such as storing user information in your database
        return "made it"; // Redirect to /hello after successful authentication
    }
}