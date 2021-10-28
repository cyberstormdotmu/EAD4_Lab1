package com.example.statistics.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private RestTemplate restTemplate;
    private String authHost = "http://auth-service/";
    @HystrixCommand(
            fallbackMethod = "create")

    public boolean create(String authToken) {

        String url = authHost + "user/auth/admin";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> map = new HashMap<>();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(url, entity, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            return false;
        }
        return true;
    }

    public boolean authAdminFallback(String authToken) {
        return false;
    }
}


