package com.example.myfirstrestapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

// loads in RestController for creating a Restful API
@RestController
public class ToDoController {

    // Dependency Injection des CRUD Operations Objekts
    @Autowired
    private ToDoRepository toDoRepository;

    // responds to HTTP GET of /todo url
    @GetMapping("/todo")

    // method that is run on requesting /todo url from Tomcat server
    // defaultValue is with no requestparam
    // not defaultValue is with requestparam
    public ResponseEntity<ToDo> readTodo(@RequestParam(value = "id") int id) {
        // get todo from db by id
        Optional<ToDo> toDoInDb =  toDoRepository.findById(id);

        if (toDoInDb.isPresent()){
            return new ResponseEntity<ToDo>(toDoInDb.get(), HttpStatus.OK);
        }
        // kein else notwendig, da return
            return new ResponseEntity ("No todo found with id: " + id, HttpStatus.NOT_FOUND);
    }

    // save new todo in database
    @PostMapping("/todo")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo newToDo) {
        toDoRepository.save(newToDo);
        return new ResponseEntity<ToDo>(newToDo, HttpStatus.OK);
    }

}
