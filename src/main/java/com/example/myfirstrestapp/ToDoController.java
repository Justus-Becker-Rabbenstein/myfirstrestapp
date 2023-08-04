package com.example.myfirstrestapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// loads in RestController for creating a Restful API
@RestController
public class ToDoController {

    // responds to HTTP GET of /greet url
    @GetMapping("/greet")

    // method that is run on requesting /greet url from Tomcat server
    // if no request param (just opening localhost:8080/greet) responds with Hello
    // and defaultValue String
    // if with request param localhost:8080/greet?name=Peter responds with Hello
    // Peter
    // defaultValue is method parameter of Srping framework
    public ResponseEntity<String> hello(
        @RequestParam(value = "name", defaultValue = "World, since no @RequestParam") String name) {
            if (name.equals("admin")) {
             return new ResponseEntity<String>("Hello " + name, HttpStatus.OK);
             }
        return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
    }
}