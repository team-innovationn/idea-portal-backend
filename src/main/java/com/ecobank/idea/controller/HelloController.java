package com.ecobank.idea.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;


@RestController
@RequestMapping(API_BASE_URL)
public class HelloController {

    @GetMapping("hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.status(HttpStatus.OK).body("Say hello from spring");
    }
}
