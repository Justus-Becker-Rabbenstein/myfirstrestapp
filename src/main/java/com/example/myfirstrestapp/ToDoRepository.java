// interface for CRUD operations
// mainly inhereted from CrudRepository

package com.example.myfirstrestapp;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ToDoRepository extends CrudRepository<ToDo, Integer> {

Set<ToDo> findAllByUserId(int userId);

}
