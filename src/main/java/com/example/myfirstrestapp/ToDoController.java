package com.example.myfirstrestapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ToDo> getOneToDo(@RequestParam(value = "id") int id) {
        // get a single todo from db by id
        Optional<ToDo> toDoInDb =  toDoRepository.findById(id);

        if (toDoInDb.isPresent()){
            return new ResponseEntity<ToDo>(toDoInDb.get(), HttpStatus.OK);
        }
        // kein else notwendig, da return
            return new ResponseEntity ("No todo found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/todo/all")
    public ResponseEntity<Iterable<ToDo>> getAllToDo() {
        Iterable<ToDo> allToDosInDb = toDoRepository.findAll();
        return new ResponseEntity<Iterable<ToDo>>(allToDosInDb, HttpStatus.OK);
    }

    // save new todo in database
    @PostMapping("/todo")
    public ResponseEntity<ToDo> createOneToDo(@RequestBody ToDo newToDo) {
        toDoRepository.save(newToDo);
        return new ResponseEntity<ToDo>(newToDo, HttpStatus.CREATED);
    }

    @DeleteMapping("/todo")
    public ResponseEntity deleteOneToDo(@RequestParam(value = "id") int id) {
        Optional<ToDo> toDoInDb = toDoRepository.findById(id);
        if (toDoInDb.isPresent()) {
            toDoRepository.deleteById(id);
            return new ResponseEntity("To do with id: " + id + " deleted.",HttpStatus.OK);
        }
        return new ResponseEntity("Deletion in database of the id: " + id + " failed.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/todo")
    public ResponseEntity<ToDo> editOneToDo(@RequestBody ToDo putToDo) {

        Optional<ToDo> toDoInDb = toDoRepository.findById(putToDo.getId());

        if (toDoInDb.isPresent()) {
            // update
            ToDo savedToDo = toDoRepository.save(putToDo);
            return new ResponseEntity<ToDo>(savedToDo, HttpStatus.OK);
        }
        return new ResponseEntity("ToDo update of id: " + putToDo.getId() + " failed.", HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/todo/setDone")
    public ResponseEntity<ToDo> setIsDone(@RequestParam(value = "isDone")boolean isDone,
                                          @RequestParam(value = "id") int id) {

        Optional<ToDo> toDoInDb = toDoRepository.findById(id);

        if (toDoInDb.isPresent()) {
            toDoInDb.get().setIsDone(isDone);
            ToDo patchedToDo = toDoRepository.save(toDoInDb.get());
            return new ResponseEntity<ToDo>(patchedToDo, HttpStatus.OK);
        }
        return new ResponseEntity("Could not set state of isDone in ToDo with id: " + id, HttpStatus.NOT_FOUND);
    }
}
