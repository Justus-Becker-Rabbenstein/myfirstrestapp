package com.example.myfirstrestapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToDoController {

    @GetMapping("/greet")
    public String hello() {
        return "Hello World";
    }
    
}
