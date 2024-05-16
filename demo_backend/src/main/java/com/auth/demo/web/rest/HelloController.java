package com.auth.demo.web.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @CrossOrigin(origins="http://localhost:3000", allowCredentials ="true")
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        // Create your response message
        String message = "ENTERED";

        // Create a ResponseEntity with your message and HttpStatus.OK
        return ResponseEntity.ok(message);
    }
}