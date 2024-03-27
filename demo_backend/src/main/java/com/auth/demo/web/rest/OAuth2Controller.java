package com.auth.demo.web.rest;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

import com.auth.demo.config.GoogleConfig;
import org.springframework.web.client.RestTemplate;

@Controller
public class OAuth2Controller {

    // This endpoint handles the OAuth2 callback from Google
//    @GetMapping("/login/oauth2/code/google")
//    public ResponseEntity<Object> oauth2Callback(@RequestParam("access_token") String accessToken) {
//        // Add any necessary logic here, such as storing user information in your database
//        // Create a HashMap to hold the response data
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("token has been gained " , accessToken);
//        System.out.println("token is " + accessToken);
//
//        // Redirect to localhost:3000 with the token as a query parameter
//        return ResponseEntity.status(302)
//                .header("Location", "http://localhost:3000")
//                .body(responseMap);
//    }

    private final GoogleConfig googleConfig;
    public OAuth2Controller (GoogleConfig googleConfig) {
        this.googleConfig = googleConfig;
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<Object> oauth2Callback(@RequestParam("code") String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String url = "https://oauth2.googleapis.com/token" +
                "?code=" + code +
                "&client_id=" + googleConfig.getClientId() +
                "&client_secret=" + googleConfig.getClientSecret() +
                "&redirect_uri=http://localhost:8090/login/oauth2/code/google" +
                "&grant_type=authorization_code";

        ResponseEntity<Map> responseEntity = new RestTemplate().postForEntity(
                url,
                null,
                Map.class
        );
        String accessToken = (String) responseEntity.getBody().get("access_token");


        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setBearerAuth(accessToken);
        HttpEntity<String> authRequest = new HttpEntity<>(authHeaders);

        ResponseEntity<Map> userInfoResponse = new RestTemplate().exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET,
                authRequest,
                Map.class
        );

        Map<String, Object> userInfo = userInfoResponse.getBody();
// Add logic here to extract and process specific user information (e.g., email, profile)

        return ResponseEntity.ok(userInfo);
    }
}