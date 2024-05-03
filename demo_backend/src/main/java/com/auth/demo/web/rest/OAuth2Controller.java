package com.auth.demo.web.rest;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.auth.demo.config.GoogleConfig;
import org.springframework.web.client.RestTemplate;

@RestController
public class OAuth2Controller {

    private final GoogleConfig googleConfig;
    public OAuth2Controller (GoogleConfig googleConfig) {
        this.googleConfig = googleConfig;
    }

    @CrossOrigin(origins="http://localhost:3000", allowCredentials ="true")
    @PostMapping("/oauth2/google")
    public ResponseEntity<Object> oauth2Callback(@RequestHeader(name = "Authorization") String authorizationHeader) {
        System.out.println("header is " + authorizationHeader);

        try {
            // Extract the access token from the Authorization header
            String accessToken = authorizationHeader.replace("Bearer ", "");

            // Verify the access token using the Google ID Token Verifier
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(googleConfig.getClientId()))
                    .build();

            System.out.println("verifier");
            System.out.println(verifier.toString());

            System.out.println("client id " + googleConfig.getClientId());
            System.out.println("access token is " + accessToken);

            GoogleIdToken idToken = verifier.verify(accessToken);
            System.out.println("id token is " + idToken.toString());
            if (verifier.verify(accessToken) != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Get the user's Google ID and email
                String userId = payload.getSubject();
                String email = payload.getEmail();
                System.out.println("user id is " + userId);
                System.out.println("user email is " + email);

                // Create a response map with the user's information
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("userId", userId);
                responseMap.put("email", email);
                return ResponseEntity.ok(responseMap);
            } else {
                return ResponseEntity.badRequest().body("Invalid access token");
            }
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.badRequest().body("Error verifying access token: " + e.getMessage());
        }
    }


    @GetMapping("/csrf")
    public ResponseEntity<Object> getCsrf(){
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("messsage", "csrf assigned to cookies");
        return ResponseEntity.ok(responseMap);
    }
}