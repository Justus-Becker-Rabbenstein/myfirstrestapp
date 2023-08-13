package com.example.myfirstrestapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// loads in Spring RestController for creating a Restful API
@RestController
public class ToDoController {

    // Dependecy injection (object) of ToDoRepository / userRepository with Crud methods
    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private UserRepository userRepository;

    // URL /todo of http get will responds with the code in this method
    @GetMapping("/todo")

    // method that is run on requesting /todo url from Tomcat server
    // defaultValue is with no requestparam
    // not defaultValue is with requestparam (/todo?...)
    public ResponseEntity<ToDo> getOneToDo(@RequestParam(value = "id") int id) {
        // get a single todo from db by id
        Optional<ToDo> toDoInDb =  toDoRepository.findById(id);

        if (toDoInDb.isPresent()){
            return new ResponseEntity<ToDo>(toDoInDb.get(), HttpStatus.OK);
        }
        // no else required, since return statement quits method if true
            return new ResponseEntity ("No todo found with id: " + id, HttpStatus.NOT_FOUND);
    }

    // return all todos of single user when given api secret
    @GetMapping("/todo/all")
    public ResponseEntity<Iterable<ToDo>> getAllToDo(@RequestHeader("api-secret") String secret) {
        var userBySecret = userRepository.findBySecret(secret);
        if (userBySecret.isPresent()) {
            Iterable<ToDo> allToDosInDb = toDoRepository.findAllByUserId(userBySecret.get().getId());
            return new ResponseEntity<Iterable<ToDo>>(allToDosInDb, HttpStatus.OK);
        }
        return  new ResponseEntity("Invalid API secret. ", HttpStatus.BAD_REQUEST);
    }

    // save new todo in database
    @PostMapping("/todo")
    public ResponseEntity<ToDo> createOneToDo(@RequestBody ToDo newToDo) {
        toDoRepository.save(newToDo);
        return new ResponseEntity<ToDo>(newToDo, HttpStatus.CREATED);
    }

    // delete one todo
    @DeleteMapping("/todo")
    public ResponseEntity deleteOneToDo(@RequestParam(value = "id") int id) {
        Optional<ToDo> toDoInDb = toDoRepository.findById(id);
        if (toDoInDb.isPresent()) {
            toDoRepository.deleteById(id);
            return new ResponseEntity("To do with id: " + id + " deleted.",HttpStatus.OK);
        }
        return new ResponseEntity("Deletion in database of the id: " + id + " failed.", HttpStatus.NOT_FOUND);
    }

    // update any task of todos
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

    // update only isDone status
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
