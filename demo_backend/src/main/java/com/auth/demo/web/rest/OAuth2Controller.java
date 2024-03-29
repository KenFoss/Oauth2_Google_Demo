package com.auth.demo.web.rest;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import com.auth.demo.config.GoogleConfig;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/login")
public class OAuth2Controller {

    private final GoogleConfig googleConfig;
    public OAuth2Controller (GoogleConfig googleConfig) {
        this.googleConfig = googleConfig;
    }

    @PostMapping("/oauth2/google")
    @CrossOrigin(origins="*") // Enable CORS for this specific method with any origin
    public ResponseEntity<Object> oauth2Callback(@RequestHeader(name = "Authorization") String authorizationHeader) {
        System.out.println("header is " + authorizationHeader);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("auth", authorizationHeader);
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/csrf")
    public ResponseEntity<Object> getCsrf(){
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("messsage", "csrf assigned to cookies");
        return ResponseEntity.ok(responseMap);
    }
}