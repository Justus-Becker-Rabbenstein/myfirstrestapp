// Interface dient zum Erstellen der CRUD Operations fuer Datenbank
// bereits alles in CrudRepositry definiert, hier nur Code, wenn erweitert werden muss

package com.example.myfirstrestapp;

import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, Integer> {

}
